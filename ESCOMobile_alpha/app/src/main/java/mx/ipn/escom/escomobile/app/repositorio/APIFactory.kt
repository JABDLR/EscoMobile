package mx.ipn.escom.escomobile.app.repositorio

import android.content.Context
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import mx.ipn.escom.escomobile.app.auxiliares.ClienteCertificadoAutofirmado
import mx.ipn.escom.escomobile.app.config.AppConfig
import mx.ipn.escom.escomobile.app.repositorio.actividad.ActividadAPI
import mx.ipn.escom.escomobile.app.repositorio.convertidorDatos.MyDateAdapter
import mx.ipn.escom.escomobile.app.repositorio.evento.EventoAPI
import mx.ipn.escom.escomobile.app.repositorio.usuario.UsuarioAPI
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.io.InputStream
import javax.net.ssl.SSLSocketFactory
import kotlin.coroutines.coroutineContext

/**
 * APIFactory
 *
 * Clase que nos permite crear una API para consultar Escomobile
 *
 * @param interceptador [Interceptor] Crear el interceptor de autenticación para añadir la llave de la api a cada petición.
 * @param cliente [OkHttpClient] El cliente de http que va a realizar las peticiones, usando nuestro interceptor
 * @param retrofit [Retrofit] Nos regresa la instancia de Retrofit para poder hacer peticiones.
 *
 * @param eventoAPI [EventoAPI] API para acceder a los Eventos [Evento]
 * @param actividadAPI [ActividadAPI] API para acceder a las Actividades [Actividad]
 */
class APIFactory private constructor(
    private var certificado: InputStream
) {
    private val interceptador = Interceptor { chain->
        val nuevaURL = chain.request().url()
            .newBuilder()
            .addQueryParameter("api_key", AppConfig.apiKey)
            .build()

        val nuevaPeticion = chain.request()
            .newBuilder()
            .url(nuevaURL)
            .build()

        chain.proceed(nuevaPeticion)
    }

    // OkhttpClient for building http request url
    private val cliente = ClienteCertificadoAutofirmado.obtenerClienteNoSeguro()
        .build()

    private val moshiBuilder = Moshi.Builder()
        .add(MyDateAdapter())



    fun retrofit() : Retrofit = Retrofit.Builder()
        .client(cliente)
        .baseUrl(AppConfig.urlServidor)
        .addConverterFactory(MoshiConverterFactory.create(moshiBuilder.build()))
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .build()


    val eventoAPI: EventoAPI = retrofit().create(EventoAPI::class.java)
    val usuarioAPI: UsuarioAPI = retrofit().create(UsuarioAPI::class.java)
    val actividadAPI: ActividadAPI = retrofit().create(ActividadAPI::class.java)


    companion object {
        // Para hacerla un Singleton
        @Volatile private var instancia: APIFactory? = null

        fun obtenerInstancia(certificado: InputStream) =
            instancia ?: synchronized(this) {
                instancia ?: APIFactory(certificado).also { instancia = it }
            }

    }

}
