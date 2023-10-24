package mx.ipn.escom.escomobile.app.repositorio.evento

import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import mx.ipn.escom.escomobile.app.repositorio.APIRespuesta

/**
 * Una Intefaz de Red de Retrofit 2 para acceso a una API
 */
interface EventoAPI {
    // TODO Actualizar el endpoint para la nueva versión
    @GET("v2/api/eventos")
    fun obtenerEventosAsync():
            Deferred<Response<APIRespuesta<ListaEventosRespuesta>>>
    @GET("v2/api/eventos/{id}")
    fun obtenerEventoPorIDAsync(@Path("id") id: Int):
            Deferred<Response<APIRespuesta<EventoIndividualRespuesta>>>
}

/**
 * Representación de una lista de eventos en json.
 */
@JsonClass(generateAdapter = true)
data class ListaEventosRespuesta(
    @field:Json(name = "eventos") val eventos: List<Evento>
)

/**
 * Representación de un evento en json.
 */
@JsonClass(generateAdapter = true)
data class EventoIndividualRespuesta(
    @field:Json(name = "evento") val evento: Evento
)
