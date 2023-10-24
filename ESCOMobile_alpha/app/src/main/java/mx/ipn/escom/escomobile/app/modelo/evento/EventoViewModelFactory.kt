package mx.ipn.escom.escomobile.app.modelo.evento

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import mx.ipn.escom.escomobile.app.repositorio.evento.EventoRepositorio

/**
 * Factory for creating a [PlantListViewModel] with a constructor that takes a [PlantRepository].
 * Factory para crear un [EventoViewModel] con un constructor que recibe un [EventoRepositorio]
 */
class EventoViewModelFactory(
    private val repositorio: EventoRepositorio
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>) = EventoViewModel(repositorio) as T
}