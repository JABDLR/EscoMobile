package mx.ipn.escom.escomobile.app.vistas.actividad


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager


import mx.ipn.escom.escomobile.app.R
import mx.ipn.escom.escomobile.app.auxiliares.Inyector
import mx.ipn.escom.escomobile.app.modelo.MyLogger
import mx.ipn.escom.escomobile.app.databinding.FragmentListaActividadesBinding
import mx.ipn.escom.escomobile.app.modelo.actividad.ActividadAdapter
import mx.ipn.escom.escomobile.app.modelo.actividad.ActividadItem
import mx.ipn.escom.escomobile.app.modelo.actividad.ActividadViewModel
import mx.ipn.escom.escomobile.app.modelo.evento.EventoViewModel
import mx.ipn.escom.escomobile.app.repositorio.actividad.Actividad
import mx.ipn.escom.escomobile.app.repositorio.evento.Evento
import mx.ipn.escom.escomobile.app.vistas.actividad.FragmentListadoActividadesArgs

/**
 * A simple [Fragment] subclass.
 */
class FragmentListadoActividades : Fragment() {

    private lateinit var viewModel: ActividadViewModel
    private lateinit var viewModelEventos: EventoViewModel
    private lateinit var adapter: ActividadAdapter
    private lateinit var binding: FragmentListaActividadesBinding
    private lateinit var actividades: MutableList<Actividad>
    private lateinit var eventos: MutableList<Evento>
    private lateinit var items: MutableList<ActividadItem>
    val args: FragmentListadoActividadesArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Crea el RecyclerView
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_lista_actividades, container, false)

        adapter = ActividadAdapter()

        binding.recyclerViewActividades.layoutManager = LinearLayoutManager(context)
        binding.recyclerViewActividades.adapter = adapter

        binding.progressBar.visibility = View.VISIBLE
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        configurarAdapter()
    }


    fun configurarAdapter() {
        viewModel =
            ViewModelProvider(this, Inyector.proveeActividadViewmModelFactory(requireContext()))
                .get(ActividadViewModel::class.java)

        viewModelEventos =
            ViewModelProvider(this, Inyector.proveeEventosViewmModelFactory(requireContext()))
                .get(EventoViewModel::class.java)
        eventos = mutableListOf<Evento>()
        actividades = mutableListOf<Actividad>()
        // TODO Checar si hay idEvento, para mostrar solo las actividades de ese evento o las aisladas
        if (args.idEvento == 0) { // Se muestran actividades aisladas
            viewModel.obtenerActividaesAisladas()
            viewModelEventos.obtenerEventos()
            MyLogger.debug("Mostrando Actividades Aisladas")
        } else { // Se muestran las actividades del evento con id = idEvento
            viewModel.obtenerActividaesDeEvento(args.idEvento)
            MyLogger.debug("Mostrando de Evento")
        }

        // TODO Mostrar imagen de carga
        actualizarAcitividades()
        actualizarEventos()
    }

    override fun onResume() {
        super.onResume()

        actualizarEventos()
        actualizarAcitividades()
    }

    fun actualizarAcitividades() {
        viewModel.actividadesLiveData.observe(this, Observer { actividades ->
            var msg = "Se actualiza lista de actividades en UI."
            MyLogger.debug(msg)
            if (actividades.isNullOrEmpty()) {
                msg = "No hay eventos"
                // TODO Mostrar vista vacia de actividades.
                MyLogger.debug(msg)
            } else {
                this.actividades = actividades
                items = adapter.crearListadoActividades(this.actividades, this.eventos)
                items = adapter.agregarSeparadores(items)
                adapter.submitList(items)
                // TODO Eliminar imagen de carga
                binding.progressBar.visibility = View.GONE
            }
        })
    }

    fun actualizarEventos() {
        viewModelEventos.eventosLiveData.observe(this, Observer { eventos ->
            var msg = "Se actualiza lista de eventos en UI."
            MyLogger.debug(msg)
            if (eventos.isNullOrEmpty()) {
                msg = "No hay eventos"
                // TODO Mostrar vista vacia de eventos.
                MyLogger.debug(msg)
            } else {
                this.eventos = eventos
                items = adapter.crearListadoActividades(this.actividades, this.eventos)
                items = adapter.agregarSeparadores(items)
                adapter.submitList(items)
                // TODO Guardar en cache y en disco.
                // TODO Eliminar imagen de carga
                binding.progressBar.visibility = View.GONE
            }
        })
    }

}
