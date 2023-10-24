package mx.ipn.escom.escomobile.app.modelo

import android.util.Log
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.google.firebase.crashlytics.ktx.crashlytics
import com.google.firebase.ktx.Firebase
import mx.ipn.escom.escomobile.app.config.AppConfig
//import com.crashlytics.android.Crashlytics
private lateinit var firebaseAnalytics: FirebaseAnalytics

/**
 * Objeto encargado de la bitácora de la aplicación, hace uso del Logger de Android y de Crashlytics
 * Hacer un Crashlytics.log permite agregar información a los reportes de crasheo de la app.
 * además de que si se la da la prioridad y la etiqueta, hace uso de  android.util.Log
 * @property LogFacility Define el nombre del Logger a usarse, por motivos de filtrado.
 * @property reportaErrores Define si el usuario decide proporcionar información adicional sobre errores.
 */
object MyLogger {

    fun verbose(mensaje: String, etiqueta: String = AppConfig.LogFacility) {
        if (AppConfig.reportaErrrores) {
            //Crashlytics.log(Log.VERBOSE, etiqueta, mensaje)
            Firebase.crashlytics.log(mensaje)
        }
        else {
            Log.v(etiqueta, mensaje)
        }
    }

    fun debug(mensaje: String, etiqueta: String = AppConfig.LogFacility) {
        if (AppConfig.reportaErrrores) {
            //Crashlytics.log(Log.DEBUG, etiqueta, mensaje)
            Firebase.crashlytics.log(mensaje)
        }
        else {
            Log.d(etiqueta, mensaje)
        }
    }
    fun info(mensaje: String, etiqueta: String = AppConfig.LogFacility) {
        if (AppConfig.reportaErrrores) {
            //Crashlytics.log(Log.INFO, etiqueta, mensaje)
            Firebase.crashlytics.log(mensaje)
        }
        else {
            Log.i(etiqueta, mensaje)
        }
    }

    fun warn(mensaje: String, etiqueta: String = AppConfig.LogFacility) {
        if (AppConfig.reportaErrrores) {
            //Crashlytics.log(Log.WARN, etiqueta, mensaje)
            Firebase.crashlytics.log(mensaje)
        }
        else {
            Log.w(etiqueta, mensaje)
        }
    }


    fun error(mensaje: String, etiqueta: String = AppConfig.LogFacility) {
        if (AppConfig.reportaErrrores) {
            //Crashlytics.log(Log.ERROR, etiqueta, mensaje)
            Firebase.crashlytics.log(mensaje)
        }
        else {
            Log.e(etiqueta, mensaje)
        }
    }

    fun wtf(mensaje: String, etiqueta: String = AppConfig.LogFacility) {
        if (AppConfig.reportaErrrores) {
            //Crashlytics.log(Log.ERROR, etiqueta, mensaje)
            Firebase.crashlytics.log(mensaje)
        }
        else {
            Log.wtf(etiqueta, mensaje)
        }
    }
    fun assert(mensaje: String, etiqueta: String = AppConfig.LogFacility) {
        if (AppConfig.reportaErrrores) {
            //Firebase.crashlytics.log(Log.ASSERT, etiqueta, mensaje)
            Firebase.crashlytics.log(mensaje)
        }
        else {
            Log.println(Log.ASSERT, etiqueta, mensaje)
        }
    }
}