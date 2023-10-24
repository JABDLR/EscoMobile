package mx.ipn.escom.escomobile.app.repositorio.area

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.io.Serializable

/**
 *   [Area] Clase que define la entidad Area
 *
 *   La clase nos permite represnetar un área de la escula.
 *
 *   @property id [Int] LLave primaria, nos permite identificar cada area de manera única.
 *   @property clave [String] Es la clave que la escuela le asigna en el plano a esa area.
 *   @property nombreCorto [String] Manera abreviada o corta de referirse al area.
 *   @property nombreLargo [String] Nombre del area.
 *   @property idTipo [Int] LLave foranea, permite referenciar al catalogo de tipos.
 *   @property tipo [String] La clase de area que es.
 *   @property piso [Int] La planta en el que esta ubicada la area, representacion numerica.
 *   @property planta [String] La planta en la que esta la area, nombre completo de la planta.
 *   @property plantaCorto [String] Nombre corto de la planta en la que se encuentra la planta.
 *   @property latitud [Double] La latitud geográfica del area.
 *   @property longitud [Double] La longitud geográfica del area.
 */
@JsonClass(generateAdapter = true)
data class Area(
    @field:Json(name = "id")  var id : Int? = null,
    @field:Json(name = "clave") var clave: String? = null,
    @field:Json(name = "nombreCorto") var nombreCorto: String? = null,
    @field:Json(name = "nombreLargo") var nombreLargo: String? = null,
    @field:Json(name = "idTipo") var idTipo: Int? = null,
    @field:Json(name = "tipo") var tipo: String? = null,
    @field:Json(name = "piso") var piso: Int? = null,
    @field:Json(name = "planta") var planta: String? = null,
    @field:Json(name = "plantaCorto") var plantaCorto: String? = null,
    @field:Json(name = "latitud") var latitud: Double? = null,
    @field:Json(name = "longitud") var longitud: Double? = null
): Serializable
