package mx.ipn.escom.escomobile.app.repositorio

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * Representación de la respuesta que da nuestra API
 *
 * @property status [Boolean] True si la peticion fue exitosa, False de lo contrario
 * @property mensaje [String] Mensaje que aparece en caso de error
 * @property codigo [Int] Código del error
 * @property data [T] Payload de la respuesta, dependiendo de que API consulte es la respuesta
 *
 * TODO Definir los codigos de error
 * TODO Definir los mensajes de error
 *
 */
@JsonClass(generateAdapter = true)
data class APIRespuesta<T>(
    @field:Json(name = "status") val estado: Boolean,
    @field:Json(name = "mensaje") val mensaje: String?,
    @field:Json(name = "codigo")  val codigo: Int,
    @field:Json(name = "data") val data: T
)