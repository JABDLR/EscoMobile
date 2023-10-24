package mx.ipn.escom.escomobile.app.repositorio.convertidorDatos

import com.squareup.moshi.FromJson
import com.squareup.moshi.JsonReader
import com.squareup.moshi.JsonWriter
import com.squareup.moshi.ToJson
import mx.ipn.escom.escomobile.app.config.AppConfig
import java.text.SimpleDateFormat
import java.util.*

class MyDateAdapter {
    private val formatoFecha = SimpleDateFormat(AppConfig.formatoFecha, Locale.getDefault())

    @FromJson
    fun fromJson(reader: JsonReader): Date? {
        return try {
            val dateAsString = reader.nextString()
            return formatoFecha.parse(dateAsString)
        } catch (e: Exception) {
            null
        }
    }

    @ToJson
    fun toJson(writer: JsonWriter, value: Date?) {
        if (value != null) {
            writer.value(value.toString())
        }
    }

}