package mx.ipn.escom.escomobile.app.repositorio.usuario

import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * Una Intefaz de Red de Retrofit 2 para acceso a una API
 */
interface UsuarioAPI {
    // TODO Actualizar el endpoint para la nueva versión
    @GET("v2/api/eventos")  //Cambiar por la api de usuarios en el servidor
    fun obtenerUsuarios() : Deferred<Response<UsuarioRespuesta<ListaUsuariosRespuesta>>>
    @GET("v2/api/eventos/{id}")   //Cambiar por la api de usuarios en el servidor
    fun obtenerUsuarioPorID(@Path("id") id: Int):
            Deferred<Response<UsuarioRespuesta<UsuarioIndividualRespuesta>>>
}

/**
 * Representación de una lista de usuarios en json.
 */
@JsonClass(generateAdapter = true)
data class ListaUsuariosRespuesta(
    @field:Json(name = "usuarios") val usuarios: List<Usuario>
)

/**
 * Representación de un usuario en json.
 */
@JsonClass(generateAdapter = true)
data class UsuarioIndividualRespuesta(
    @field:Json(name = "usuario") val usuario: Usuario
)

/**
 * Representación de la respuesta que da nuestra API de Usuarios.
 */
@JsonClass(generateAdapter = true)
data class UsuarioRespuesta<T>(
    @field:Json(name = "status") val estado: Boolean,
    @field:Json(name = "mensaje") val mensaje: String?,
    @field:Json(name = "codigo")  val codigo: Int,
    @field:Json(name = "data") val data: T
)
