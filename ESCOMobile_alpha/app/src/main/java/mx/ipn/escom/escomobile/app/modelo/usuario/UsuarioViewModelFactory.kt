package mx.ipn.escom.escomobile.app.modelo.usuario

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import mx.ipn.escom.escomobile.app.repositorio.usuario.UsuarioRepositorio

/**
 * Factory for creating a [PlantListViewModel] with a constructor that takes a [PlantRepository].
 * Factory para crear un [UsuarioViewModel] con un constructor que recibe un [UsuarioRepositorio]
 */
class UsuarioViewModelFactory(
    private val repositorio: UsuarioRepositorio
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>) = UsuarioViewModel(repositorio) as T
}