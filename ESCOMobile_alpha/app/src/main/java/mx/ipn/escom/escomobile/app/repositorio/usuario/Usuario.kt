package mx.ipn.escom.escomobile.app.repositorio.usuario

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import mx.ipn.escom.escomobile.app.config.AppConfig
import org.json.JSONObject
import java.io.Serializable
import java.text.SimpleDateFormat

import java.util.*

/**
 *   Clase de la Entidad Usuario
 *
 *   La clase nos permite tener la información de un Usuario (Ahorita tiene los datos de evento porque todavía no conozco los del usuario).
 */
@JsonClass(generateAdapter = true)
data class Usuario(
    @field:Json(name = "idUsuario") var id: Int? = null,
    @field:Json(name = "nombre") var nombre: String? = null,
    @field:Json(name = "descripcion") var descripcion: String? = null,
    @field:Json(name = "fechaIni") var fechaInicio: Date? = null,
    @field:Json(name = "fechaFin") var fechaFin: Date? = null,
    @field:Json(name = "urlImagen") var urlImagen: String? = null,
    @field:Json(name = "urlVerMas") var urlVerMas: String? = null
): Serializable {
    var fechaModificacion: Date? = null


    /**
     * Instanciar la clase a partir de un JSON con estructura como la devuelve el servidor.
     * @param JSONObject Un objeto json, con el formato de un evento.
     * ejemplo
     * {
    "id": "1",
    "nombre": "InducciÃ³n",
    "descripcion": "Jornada de InducciÃ³n, Semestre 2020/1\nBienvenida a nuevos alumnos.\nConoce tu nueva esccuela",
    "fechaIni": "2019-07-30 10:00:00",
    "fechaFin": "2019-08-03 13:00:00",
    "urlVerMas": "http://www.isc.escom.ipn.mx/",
    "urlImagen": "http://www.isc.escom.ipn.mx/images/parallaxESCOM.jpg"
    }
     * parsea las fechas al tipo Date
     * @return Actividad Instancia de la clase actividad
     */
}