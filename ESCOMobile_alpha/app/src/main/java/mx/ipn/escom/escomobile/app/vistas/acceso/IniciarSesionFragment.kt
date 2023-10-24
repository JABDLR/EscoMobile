package mx.ipn.escom.escomobile.app.vistas.acceso

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController

import mx.ipn.escom.escomobile.app.R
import mx.ipn.escom.escomobile.app.auxiliares.Inyector
import mx.ipn.escom.escomobile.app.databinding.FragmentIniciarSesionBinding
import mx.ipn.escom.escomobile.app.modelo.MyLogger
import mx.ipn.escom.escomobile.app.modelo.usuario.UsuarioViewModel
import mx.ipn.escom.escomobile.app.vistas.MainActivity


/** InicioFragment
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * Use the [InicioFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class IniciarSesionFragment : Fragment() {

    private lateinit var binding: FragmentIniciarSesionBinding
    private lateinit var viewModel: UsuarioViewModel

    companion object {
        @JvmStatic
        fun newInstance() = IniciarSesionFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders
            .of(this, Inyector.proveeUsuariosViewmModelFactory(requireContext()))
            .get(UsuarioViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflar la vista para este fragmento
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_iniciar_sesion, container, false)

        // Inicializar los listeners de los bótones
        with(binding) {

            btnIngresar.setOnClickListener {
                MyLogger.debug("Navegando a Inicio (Iniciando sesion).")
                findNavController().navigate(R.id.inicioFragment,
                    null,
                    NavOptions.Builder()
                        // Elimina BienvenidaFragment del backStack
                        .setPopUpTo(R.id.iniciarSesionFragment, true).build()
                )
            }

            btnOlvidePass.setOnClickListener {
                MyLogger.debug("Navegando a Olvide pass.")
            }

            btnRegistrate.setOnClickListener {
                MyLogger.debug("Navegando a Registrarse.")
            }

        }
        // Ocultar la navegacion en este fragment
        (activity as MainActivity).mostrarNavegacion(false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        // Bloquear la orientacion de la pantalla en modo Portrait
        (activity as MainActivity).bloquearOrientacion(true)
        (activity as MainActivity).mostrarNavegacion(false)
    }

    override fun onPause() {
        super.onPause()
        // Liberar la orientacion de la pantalla
        (activity as MainActivity).bloquearOrientacion(false)
    }

    override fun onStop() {
        super.onStop()
        // Liberar la navegacion para que se muestre en los demás fragments
        (activity as MainActivity).mostrarNavegacion(true)
    }


}
