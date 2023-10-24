package mx.ipn.escom.escomobile.app.modelo.actividad

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import mx.ipn.escom.escomobile.app.repositorio.actividad.ActividadRepositorio

/**
 * Factory para crear un [ActivdadViewModel] con un constructor que recibe un [ActividadRepositorio]
 */
class ActividadViewModelFactory(
    private val repositorio: ActividadRepositorio
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>) = ActividadViewModel(repositorio) as T
}