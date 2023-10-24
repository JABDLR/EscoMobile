package mx.ipn.escom.escomobile.app.vistas.evento


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager


import mx.ipn.escom.escomobile.app.R
import mx.ipn.escom.escomobile.app.auxiliares.Inyector
import mx.ipn.escom.escomobile.app.modelo.MyLogger
import mx.ipn.escom.escomobile.app.modelo.evento.EventoViewModel
import mx.ipn.escom.escomobile.app.repositorio.evento.Evento
import mx.ipn.escom.escomobile.app.databinding.FragmentListaEventosBinding
import mx.ipn.escom.escomobile.app.modelo.evento.EventoAdapter

/**
 * A simple [Fragment] subclass.
 */
class FragmentListadoEventos : Fragment() {

    private lateinit var viewModel: EventoViewModel
    private lateinit var adapter: EventoAdapter
    private lateinit var binding: FragmentListaEventosBinding
    private lateinit var eventos: MutableList<Evento>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Crea el RecyclerView
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_lista_eventos, container, false)

        adapter = EventoAdapter()

        binding.recyclerViewEventos.layoutManager = LinearLayoutManager(context)
        binding.recyclerViewEventos.adapter = adapter
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this, Inyector.proveeEventosViewmModelFactory(requireContext()))
            .get(EventoViewModel::class.java)
        // TODO Mostrar imagen de carga
        viewModel.obtenerEventos()
        actualizarEventos()
    }

    fun actualizarEventos() {
        viewModel.eventosLiveData.observe(this, Observer { eventos ->
            var msg = "Se actualiza lista de eventos en UI."
            Toast.makeText(context, "Se actualizo del servidor", Toast.LENGTH_LONG).show()
            MyLogger.debug(msg)
            if (eventos.isNullOrEmpty()) {
                msg = "No hay eventos"
                // TODO Mostrar vista vacia de eventos.
                MyLogger.debug(msg)
            } else {
                this.eventos = eventos
                adapter.submitList(eventos)
                // TODO Guardar en cache y en disco.
                // TODO Eliminar imagen de carga
            }
        })
    }


}
