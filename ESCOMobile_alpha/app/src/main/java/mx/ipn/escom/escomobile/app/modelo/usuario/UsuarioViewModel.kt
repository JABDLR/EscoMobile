package mx.ipn.escom.escomobile.app.modelo.usuario

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*
import mx.ipn.escom.escomobile.app.repositorio.APIFactory
import mx.ipn.escom.escomobile.app.repositorio.usuario.Usuario
import mx.ipn.escom.escomobile.app.repositorio.usuario.UsuarioRepositorio
import kotlin.coroutines.CoroutineContext

/**
 * [UsuarioViewModel] Nos permite abstraer la capa de lógica de la IU.
 * Hace uso de corutinas para interacturar con RetroFit y con Room.
 *
 * @param parentJob [Job] El que llama la función.
 * @param coroutineContext [CoroutineContext] El contexto en que se ejecuta la corutina.
 * @param scope [CoroutineScope] El alcance de la corutina.
 *
 */
class UsuarioViewModel(
    private var repositorio: UsuarioRepositorio
): ViewModel() {

    private val parentJob = Job()

    private val coroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.Default

    private val scope = CoroutineScope(coroutineContext)



    val usuariosLiveData = MutableLiveData<MutableList<Usuario>>()


    // TODO Agregar Room
    fun obtenerUsuarios() {
        scope.launch {
            val usuarios = repositorio.obtenerUsuarios()
            usuariosLiveData.postValue(usuarios)
        }
    }

    fun cancelarPeticiones() = coroutineContext.cancel()

}