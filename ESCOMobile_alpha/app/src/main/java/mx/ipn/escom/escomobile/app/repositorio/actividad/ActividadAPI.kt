package mx.ipn.escom.escomobile.app.repositorio.actividad

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.coroutines.Deferred
import mx.ipn.escom.escomobile.app.repositorio.APIRespuesta
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path


/** [ActividadAPI]
* Intefaz de Retrofit 2 para accesar a la  API de Actividad
*/
interface ActividadAPI {
    @GET("v2/api/actividades")
    fun obtenerActividadesAisladasAsync():
        Deferred<Response<APIRespuesta<ListaActividadesRespuesta>>>
    @GET("v2/api/eventos/{id}/actividades")
    fun obtenerActividadesDeEventoAsync(@Path("id") id: Int):
        Deferred<Response<APIRespuesta<ListaActividadesRespuesta>>>
    @GET("v2/api/actividades/{id}")
    fun obtenerActividadAsync(@Path("id") id: Int):
        Deferred<Response<APIRespuesta<ActividadIndividualRespuesta>>>

}
/**
 * Representación de una lista de actividades en json.
 */
@JsonClass(generateAdapter = true)
data class ListaActividadesRespuesta(
    @field:Json(name = "actividades") val actividades: List<Actividad>
)

/**
 * Representación de una actividad en json.
 */
@JsonClass(generateAdapter = true)
data class ActividadIndividualRespuesta(
    @field:Json(name = "actividad") val actividad: Actividad
)

