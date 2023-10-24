package mx.ipn.escom.escomobile.app.modelo.evento

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*
import mx.ipn.escom.escomobile.app.repositorio.APIFactory
import mx.ipn.escom.escomobile.app.repositorio.evento.Evento
import mx.ipn.escom.escomobile.app.repositorio.evento.EventoRepositorio
import kotlin.coroutines.CoroutineContext

/**
 * [EventoViewModel] Nos permite abstraer la capa de lógica de la IU.
 * Hace uso de corutinas para interacturar con RetroFit y con Room.
 *
 * @param parentJob [Job] El que llama la función.
 * @param coroutineContext [CoroutineContext] El contexto en que se ejecuta la corutina.
 * @param scope [CoroutineScope] El alcance de la corutina.
 *
 */
class EventoViewModel(
    private var repositorio: EventoRepositorio
): ViewModel() {

    private val parentJob = Job()

    private val coroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.Default

    private val scope = CoroutineScope(coroutineContext)



    val eventosLiveData = MutableLiveData<MutableList<Evento>>()


    // TODO Agregar Room
    fun obtenerEventos() {
        scope.launch {
            val eventos = repositorio.obtenerEventos()
            eventosLiveData.postValue(eventos)
        }
    }

    fun cancelarPeticiones() = coroutineContext.cancel()

}