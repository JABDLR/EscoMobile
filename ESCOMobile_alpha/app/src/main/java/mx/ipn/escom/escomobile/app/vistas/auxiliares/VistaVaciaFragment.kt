package mx.ipn.escom.escomobile.app.vistas.auxiliares


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import kotlinx.android.synthetic.main.fragment_vista_vacia.view.*

import mx.ipn.escom.escomobile.app.R
import mx.ipn.escom.escomobile.app.modelo.MyLogger

/**
 * Un [Fragment] que nos permite implementar una vista vacia o por implementar.
 *
 * @param mensaje [String] Mensaje que muestra la vista al crearse
 * @property ARG_PARAM1[String] Key del parámetro que es almacenada en el bundle
 *
 * Para navegar a este fragmento con un mensaje se hace uso de la siguiente acción
 * val action = SOMEFragmentDirections.actionGlobalVistaVaciaFragment()
 * action.mensaje = mensaje
 * findNavController().navigate(action)
 */
class VistaVaciaFragment : Fragment() {

    private var mensaje: String? = null
    val args: VistaVaciaFragmentArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_vista_vacia, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mensaje = args.mensaje
        mensaje.let {
            view.textViewMensaje.text = it
        }
    }

}
