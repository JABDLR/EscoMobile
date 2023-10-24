package mx.ipn.escom.escomobile.app.repositorio.actividad

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import mx.ipn.escom.escomobile.app.repositorio.area.Area
import mx.ipn.escom.escomobile.app.repositorio.evento.Evento
import java.io.Serializable
import java.util.Date

/**
 *   Clase de la Entidad Actividad
 *
 *   La clase nos permite tener la información de una Actividad.
 *
 *   @property idEvento El id del evento al que esta asociado la actividad, 0 en caso de que sea una
 *   actividad aislada, se usa 0 para no tener problemas con los nulos en los SharedPrefs
 */
@JsonClass(generateAdapter = true)
data class Actividad(
    @field:Json(name = "id")  var id : Int? = null,
    @field:Json(name = "nombre") var nombre: String? = null,
    @field:Json(name = "fechaInicio") var fechaInicio: Date? = null,
    @field:Json(name = "fechaFin") var fechaFin: Date? = null,
    @field:Json(name = "descripcion") var descripcion: String? = null,
    @field:Json(name = "urlImagen") var urlImagen: String? = null,
    @field:Json(name = "urlVerMas") var urlVerMas: String? = null,
    @field:Json(name = "idArea") var idArea: Int? = null,
    @field:Json(name = "idEvento") var idEvento: Int? = null,
    // Atributos para navegación de la relaciones
    @field:Json(name = "evento") var evento : Evento? = null,
    @field:Json(name = "area") var area: Area? = null
): Serializable


