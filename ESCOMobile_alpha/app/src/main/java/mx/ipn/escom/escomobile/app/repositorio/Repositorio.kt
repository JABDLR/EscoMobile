package mx.ipn.escom.escomobile.app.repositorio

import com.crashlytics.android.Crashlytics
import mx.ipn.escom.escomobile.app.modelo.MyLogger
import retrofit2.Response
import java.io.IOException
import java.lang.Exception
import java.net.UnknownHostException

sealed class Respuesta<out T : Any> {
    data class Exitosa<out T : Any>(val data: T) : Respuesta<T>()
    data class Fallida(val exception: Exception) : Respuesta<Nothing>()
}

/**
 * Clase Repositorio es la base para poder implementar un Repositorio que se adapte a cada entidad
 * Permite hacer llamadas a la API sin arrojar Excepciones, se hace en una corutina, donde la
 * función que se ejecuta es un lambda que se llama call.
 *
 * Para usarse call debe ser una lambda que realice la llamada a la API correspondiente.
 *      call = {api.obtenerEventos().await()}
 * De esta manera se puede reutilizar para todas las peticiones, ya que solo sería manejar
 * el contenido de la respuesta de cada petición.
 *
 * @author Michel J. Valencia
 *
 */
open class Repositorio {
    /**
     * @return El contenido de la respuesta de la petición.
     */
    suspend fun <T : Any> llamadaAPISegura(
        call: suspend () -> Response<T>,
        mensajeError: String
    ): T? {

        val respuesta: Respuesta<T> = respuestaAPISegura(call, mensajeError)
        var data: T? = null

        when (respuesta) {
            is Respuesta.Exitosa ->
                data = respuesta.data
            is Respuesta.Fallida -> {
                MyLogger.verbose("${respuesta.exception}")
            }
        }

        return data

    }

    /** Petición a una API, es segura ya que no arroja errores.
     * Aqui es donde se realizan las peticiones al servidor,
     * en caso de requerir un manero adicional de errores se agrega
     * catch al try, en caso de que sea una respuesta exitosa se envia el
     * cuerpo de la respuesta.*/
    private suspend fun <T : Any> respuestaAPISegura(
        call: suspend () -> Response<T>,
        mensajeError: String
    ): Respuesta<T> {
        try {
            // respuesta de la peticion que se hizo
            val respuesta = call.invoke()
            if (respuesta.isSuccessful)
            {
                MyLogger.verbose(respuesta.raw().toString())
                MyLogger.verbose(respuesta.toString())
                MyLogger.verbose(respuesta.body().toString())
                return Respuesta.Exitosa(respuesta.body()!!)
            }
            // TODO Checar mensajes de error del servidor
        }
        catch (e: UnknownHostException) {
            // Server caido o no hay red
            MyLogger.error(e.toString())
            // TODO Leer cache o Room
        }
        catch (e: Exception) {
            // Cualquier otra cosa que no se tiene contemplada
            MyLogger.error(e.toString())
            Crashlytics.logException(e)
        }
        return Respuesta.Fallida(IOException("Respuesta Fallida durante llamada segura - $mensajeError"))
    }
}


