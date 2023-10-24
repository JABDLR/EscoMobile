package mx.ipn.escom.escomobile.app.auxiliares

import android.content.Context
import mx.ipn.escom.escomobile.app.modelo.MyLogger
import mx.ipn.escom.escomobile.app.modelo.actividad.ActividadViewModelFactory
import mx.ipn.escom.escomobile.app.modelo.evento.EventoViewModel
import mx.ipn.escom.escomobile.app.modelo.evento.EventoViewModelFactory
import mx.ipn.escom.escomobile.app.modelo.usuario.UsuarioViewModelFactory
import mx.ipn.escom.escomobile.app.repositorio.APIFactory
import mx.ipn.escom.escomobile.app.repositorio.actividad.ActividadRepositorio
import mx.ipn.escom.escomobile.app.repositorio.evento.EventoRepositorio
import mx.ipn.escom.escomobile.app.repositorio.usuario.UsuarioRepositorio
import java.io.InputStream

/**
 * Métodos estáticos para facilitar la instanciación de los ViewModels utilizando
 * Inyección de Dependencias.
 */
object Inyector {

    fun proveeEventosViewmModelFactory (context: Context): EventoViewModelFactory {
        var certificado: InputStream = context.resources.openRawResource(
            context.resources.getIdentifier(
                "escomobile_ca",
                "raw",
                context.packageName
            )
        )
        val repositorio: EventoRepositorio = EventoRepositorio.obtenerInstancia(
            APIFactory.obtenerInstancia(certificado).eventoAPI
            // TODO inyectar instancia de eventoDAO
        )
        return EventoViewModelFactory(repositorio)
    }

    fun proveeUsuariosViewmModelFactory (context: Context): UsuarioViewModelFactory {
        var certificado: InputStream = context.resources.openRawResource(
            context.resources.getIdentifier(
                "escomobile_ca",
                "raw",
                context.packageName
            )
        )
        val repositorio: UsuarioRepositorio = UsuarioRepositorio.obtenerInstancia(
            APIFactory.obtenerInstancia(certificado).usuarioAPI
            // TODO inyectar instancia de usuarioDAO
        )
        return UsuarioViewModelFactory(repositorio)
    }


  fun proveeActividadViewmModelFactory (context: Context): ActividadViewModelFactory {

        var certificado: InputStream = context.resources.openRawResource(
            context.resources.getIdentifier(
                "escomobile_ca",
                "raw",
                context.packageName
            )
        )

        val repositorio: ActividadRepositorio = ActividadRepositorio.obtenerInstancia(
            APIFactory.obtenerInstancia(certificado).actividadAPI
            // TODO inyectar instancia de actividadDAO
        )
        return ActividadViewModelFactory(repositorio)
    }


}
