package mx.ipn.escom.escomobile.app.repositorio.evento

import mx.ipn.escom.escomobile.app.modelo.MyLogger
import mx.ipn.escom.escomobile.app.repositorio.Repositorio

class EventoRepositorio private constructor(
    private var api: EventoAPI
): Repositorio() {
    suspend fun obtenerEventos() : MutableList<Evento>?{

        // TODO Añadir el acceso al DAO y como fallback el uso de la red
        MyLogger.debug("Obteniendo la lista de eventos del servidor")
        val eventosRespuesta = llamadaAPISegura(
            call = {api.obtenerEventosAsync().await()},
            mensajeError= "Error al obtener los eventos del servidor."
        )

        return eventosRespuesta?.data?.eventos?.toMutableList()
    }

    companion object {
        // Para hacerla un Singleton
        @Volatile private var instancia: EventoRepositorio? = null

        fun obtenerInstancia(eventoAPI: EventoAPI) =
            instancia ?: synchronized(this) {
                instancia ?: EventoRepositorio(eventoAPI).also { instancia = it }
            }

    }

}