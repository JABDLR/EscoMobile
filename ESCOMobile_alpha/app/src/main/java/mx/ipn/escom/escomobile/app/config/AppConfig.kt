package mx.ipn.escom.escomobile.app.config

import com.google.firebase.analytics.FirebaseAnalytics

/**
 * Objeto utilizado para almacenar las constantes de la aplicación.
 *
 * Este objeto contiene definiciones de las constantes que se usarán para configurar la app.
 * @property LogFacility [String] Define el nombre del Logger a usarse, por motivos de filtrado.
 * @property reportaErrores [Boolean] Define si el usuario decide proporcionar información sobre
 * errores.
 * @property canalPorDefecto [String] Define el canal (tópico/tema) por defecto para
 * [MyFirebaseMessagingService].
 * @property canalBolsaTrabajo [String] Define el canal (tópico/tema) para la Bolsa de Trabajo
 * en [MyFirebaseMessagingService].
 * @author Michel J. Valencia
 */

object AppConfig {
    const val apiKey= "QSEFTHUKO13579AXDVGNJ"
    // Production URL
//    const val urlServidor= "https://www.comunidad.escom.ipn.mx/escomobileCA/"
    // For local developing, adjust for local ip
//    const val urlServidor= "http://192.168.100.26/escomobile/"
    // For remote testing, adjust for test server
    const val urlServidor= "http://escomobile.xyz/escomobile/"
    const val LogFacility= "ESCOMobile"
    const val reportaErrrores = false
    const val canalPorDefecto = "all_all"
    const val canalBolsaTrabajo = "bolsa_de_trabajo"
    lateinit var firebaseAnalytics: FirebaseAnalytics

    const val EVENTO_EDITAR_CUENTA = "editar_cuenta"
    const val EVENTO_VER_AREA_EN_MAPA = "ver_area_en_mapa"
    const val formatoFecha = "yyyy-MM-dd HH:mm:ss"
}