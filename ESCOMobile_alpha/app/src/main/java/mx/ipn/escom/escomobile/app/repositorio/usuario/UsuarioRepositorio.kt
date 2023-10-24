package mx.ipn.escom.escomobile.app.repositorio.usuario

import mx.ipn.escom.escomobile.app.modelo.MyLogger
import mx.ipn.escom.escomobile.app.repositorio.Repositorio

class UsuarioRepositorio private constructor(
    private var api: UsuarioAPI
): Repositorio() {
    suspend fun obtenerUsuarios() : MutableList<Usuario>?{

        // TODO Añadir el acceso al DAO y como fallback el uso de la red
        MyLogger.debug("Obteniendo la lista de eventos del servidor")
        val usuariosRespuesta = llamadaAPISegura(
            call = {api.obtenerUsuarios().await()},
            mensajeError= "Error al obtener los eventos del servidor."
        )

        return usuariosRespuesta?.data?.usuarios?.toMutableList()
    }

    companion object {
        // Para hacerla un Singleton
        @Volatile private var instancia: UsuarioRepositorio? = null

        fun obtenerInstancia(eventoAPI: UsuarioAPI) =
            instancia ?: synchronized(this) {
                instancia ?: UsuarioRepositorio(eventoAPI).also { instancia = it }
            }

    }

}