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
import mx.ipn.escom.escomobile.app.databinding.FragmentBienvenidaBinding
import mx.ipn.escom.escomobile.app.modelo.MyLogger
import mx.ipn.escom.escomobile.app.vistas.MainActivity


/** BienvenidaFragment
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * Use the [BienvenidaFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class BienvenidaFragment : Fragment() {

    private lateinit var binding: FragmentBienvenidaBinding

    companion object {
        @JvmStatic
        fun newInstance() = BienvenidaFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflar la vista para este fragmento
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_bienvenida, container, false)

        // Inicializar los listeners de los bótones
        with(binding) {
            btnConinuarSinRegistro.setOnClickListener {
                MyLogger.debug("Navegando a Inicio (Continuar sin registro).")
                findNavController().navigate(R.id.inicioFragment,
                    null,
                    NavOptions.Builder()
                            // Elimina BienvenidaFragment del backStack
                        .setPopUpTo(R.id.bienvenidaFragment, true).build()
                )
            }
            btnIniciarSesion.setOnClickListener {
                MyLogger.debug("Navegando a Iniciar Sesion.")
                findNavController().navigate(R.id.iniciarSesionFragment)
            }
            btnRegistarse.setOnClickListener {
                MyLogger.debug("Navegando a Registrar Usuario.")
                findNavController().navigate(R.id.registrarUsuarioFragment)
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

    /**
     * Aparte de la funcionalidad de onResume(), se revisa si el usuario ya está guardado en preferencias y si es así
     *  se llama con un intent a la clase ManagerActivity
     */
    /*
    override fun onResume() {
        super.onResume()

        // Checar si hay un usuario que haya iniciado sesión
	    val user = SPLogin.loadUserFromSharedPreferences( this )
	    MyLogger.debug( "USER ${ user.boleta }")
	    if( user.boleta != null && user.pass != null ) {
		    Usuario loggeado ir a pantalla de inicio
		    val intent = Intent( this, mx.ipn.escom.escomobileCA.app.vista.ManagerActivity::class.java )
		    MyLogger.debug( "ON RESUME GO TO LOGIN")
		    startActivity( intent )
		    finish()

	    } else {
//		    Mostrar login
	    }
    }
    */

}
