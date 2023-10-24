package mx.ipn.escom.escomobile.app.vistas.acceso
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController

import mx.ipn.escom.escomobile.app.R
import mx.ipn.escom.escomobile.app.databinding.FragmentRegistroUsuarioBinding
import mx.ipn.escom.escomobile.app.modelo.MyLogger
import mx.ipn.escom.escomobile.app.vistas.MainActivity


/** RegistrarFragment
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * Use the [RegistrarFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class RegistrarFragment : Fragment() {

    private lateinit var binding: FragmentRegistroUsuarioBinding

    companion object {
        @JvmStatic
        fun newInstance() = RegistrarFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflar la vista para este fragmento
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_registro_usuario, container, false)

        // Inicializar los listeners de los bótones
        with(binding) {

            btnRegistrarse.setOnClickListener {
                MyLogger.debug("Navegando a Inicio (Registrandose).")
                findNavController().navigate(R.id.inicioFragment,
                    null,
                    NavOptions.Builder()
                        // Elimina BienvenidaFragment del backStack
                        .setPopUpTo(R.id.registrarUsuarioFragment, true).build()
                )
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
