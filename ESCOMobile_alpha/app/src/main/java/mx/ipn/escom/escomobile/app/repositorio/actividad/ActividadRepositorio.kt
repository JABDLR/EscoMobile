package mx.ipn.escom.escomobile.app.repositorio.actividad

import mx.ipn.escom.escomobile.app.modelo.MyLogger
import mx.ipn.escom.escomobile.app.repositorio.Repositorio

class ActividadRepositorio private constructor(
    private var api: ActividadAPI
): Repositorio() {
    suspend fun obtenerActividadesAisladas(): MutableList<Actividad>? {
        // TODO Añadir accceso al DAO y como fallback usar la red
        MyLogger.debug("Obteniendo lista de actividades aisladas del servidor.")
        val actividadesRespuesta = llamadaAPISegura(
            call =  {api.obtenerActividadesAisladasAsync().await()},
            mensajeError = "Error al obtener las actividades del servidor."
        )

        return actividadesRespuesta?.data?.actividades?.toMutableList()

    }
    suspend fun obtenerActividadesPorEvento(idEvento: Int): MutableList<Actividad>? {
        // TODO Añadir accceso al DAO y como fallback usar la red
        MyLogger.debug("Obteniendo lista de actividades de un evento del servidor.")
        val actividadesRespuesta = llamadaAPISegura(
            call =  {api.obtenerActividadesDeEventoAsync(idEvento).await()},
            mensajeError = "Error al obtener las actividades del servidor."
        )
        return actividadesRespuesta?.data?.actividades?.toMutableList()

    }

    companion object {
        // Para hacerla un Singleton
        @Volatile private var instancia: ActividadRepositorio? = null

        fun obtenerInstancia(actividadAPI: ActividadAPI) =
            instancia ?: synchronized(this) {
                instancia ?: ActividadRepositorio(actividadAPI).also { instancia = it }
            }
    }
}