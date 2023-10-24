package mx.ipn.escom.escomobile.app.modelo.actividad

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*
import mx.ipn.escom.escomobile.app.repositorio.actividad.Actividad
import mx.ipn.escom.escomobile.app.repositorio.actividad.ActividadRepositorio
import mx.ipn.escom.escomobile.app.repositorio.evento.Evento
import kotlin.coroutines.CoroutineContext

/**
 * [ActividadViewModel] Nos permite abstraer la capa de lógica de la IU.
 * Hace uso de corutinas para interacturar con RetroFit y con Room.
 *
 * @param parentJob [Job] El que llama la función.
 * @param coroutineContext [CoroutineContext] El contexto en que se ejecuta la corutina.
 * @param scope [CoroutineScope] El alcance de la corutina.
 *
 */
class ActividadViewModel(
    private var repositorio: ActividadRepositorio
): ViewModel() {

    private val parentJob = Job()

    private val coroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.Default

    private val scope = CoroutineScope(coroutineContext)



    val actividadesLiveData = MutableLiveData<MutableList<Actividad>>()


    fun obtenerActividaesAisladas() {
        scope.launch {
            val actividades = repositorio.obtenerActividadesAisladas()
            actividadesLiveData.postValue(actividades)
        }
    }

    fun obtenerActividaesDeEvento(idEvento: Int) {
        scope.launch {
            val actividades = repositorio.obtenerActividadesPorEvento(idEvento)
            actividadesLiveData.postValue(actividades)
        }
    }

    fun cancelarPeticiones() = coroutineContext.cancel()

}