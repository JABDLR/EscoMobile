package mx.ipn.escom.escomobile.app.modelo.actividad


import android.content.Intent
import android.content.res.Resources
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
//import kotlinx.android.synthetic.main.fragment_actividad_item_detalle.view.*
// Importaciones de clases generadas por View Binding
import mx.ipn.escom.escomobile.app.databinding.FragmentActividadFechaSeparadorBinding
import mx.ipn.escom.escomobile.app.databinding.FragmentActividadItemDetalleBinding
import mx.ipn.escom.escomobile.app.databinding.FragmentEventoItemDetalleBinding
import mx.ipn.escom.escomobile.app.modelo.MyLogger
import mx.ipn.escom.escomobile.app.modelo.evento.EventoAdapter
import mx.ipn.escom.escomobile.app.repositorio.actividad.Actividad
import mx.ipn.escom.escomobile.app.repositorio.evento.Evento
import java.text.DateFormat
import java.util.*
import java.util.concurrent.TimeUnit

//iMPORTACIONES DEL JORGE


class ActividadAdapter: ListAdapter<ActividadItem, RecyclerView.ViewHolder>(ActividadItemDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val holder: RecyclerView.ViewHolder
        when(viewType) {
            TipoItem.ACTIVIDAD.ordinal -> {
                holder = ActividadViewHolder(
                    FragmentActividadItemDetalleBinding.inflate(
                        LayoutInflater.from(parent.context), parent, false
                    )
                )
            }
            TipoItem.FECHA.ordinal -> {
                holder = SeparadorFechaViewHolder(
                    FragmentActividadFechaSeparadorBinding.inflate(
                        LayoutInflater.from(parent.context), parent, false
                    )
                )
            }
            TipoItem.EVENTO.ordinal -> {
                holder = EventoAdapter.EventoViewHolder(
                    FragmentEventoItemDetalleBinding.inflate(
                        LayoutInflater.from(parent.context), parent, false
                    )
                )
            }
            else -> {
                MyLogger.error("TIPO DE ITEM sin definir, el viweType de onCreateViewHolder tiene un valor inesperado.")
                return super.createViewHolder(parent, viewType)
            }
        }
        return holder
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, posicion: Int) {
        val item= getItem(posicion)
        if (item.tipo == TipoItem.ACTIVIDAD) {
            ( holder as ActividadViewHolder).bind(item.actividad!!)
            val binding = FragmentActividadItemDetalleBinding.bind(actividad_exportar_actividad_boton)
            val actividadAreaView = binding.actividadArea
            holder.itemView.actividad_exportar_actividad_boton.setOnClickListener {
                holder.exportarACalendario(item.actividad!!, it)

            }
            holder.itemView.actividad_area.setOnClickListener {
                holder.verEnMapa(item.actividad!!, it)
            }
            holder.itemView.actividad_ver_mas.setOnClickListener {
                holder.verMasNavegador(item.actividad!!, it)
            }
        }

        if (item.tipo == TipoItem.EVENTO) {
            ( holder as EventoAdapter.EventoViewHolder).bind(item.evento!!)
            holder.itemView.setOnClickListener { holder.verActividades(item.evento!!, it) }
        }

        if (item.tipo == TipoItem.FECHA) {
            ( holder as SeparadorFechaViewHolder ).bind(item.fecha!!)
            holder.agregarSeparacion(posicion, holder.itemView)
        }
    }

    override fun getItemViewType(posicion: Int): Int {
        val item= getItem(posicion)
        return item.tipo.ordinal
    }

    /**
     * Agrega los items de fecha, que sirven como separadores en el listado de actividades
     * Recibe una lista de items de actividades ordenada por fecha ascendente
     * @return [MutableList<ActividaItem>] Devuelve una lista ya con items de fechas, que permiten
     * separar lal lista
     */
    fun agregarSeparadores(items: MutableList<ActividadItem>): MutableList<ActividadItem> {
        val nuevosItems = mutableListOf<ActividadItem>()
        var previa: ActividadItem? = null
        var diasDiferencia: Long

        items.forEachIndexed { pos, actividadItem ->
            if (pos == 0) {
                actividadItem.apply {
                    nuevosItems.add(ActividadItem(TipoItem.FECHA, null, null, fecha))
                    nuevosItems.add(ActividadItem(tipo, actividad, evento, fecha))
                }
            }
            else if (previa != null) {
                actividadItem.apply {
                    val diasFechaPrevia = TimeUnit.MILLISECONDS.toDays(previa!!.fecha!!.time)
                    val diasFechaActual = TimeUnit.MILLISECONDS.toDays(fecha!!.time)
                    diasDiferencia = diasFechaActual - diasFechaPrevia
                    if (diasDiferencia != 0L) {
                        nuevosItems.add(ActividadItem(TipoItem.FECHA, null, null, fecha))
                    }
                    nuevosItems.add(ActividadItem(tipo, actividad, evento, fecha))
                }
            }
            previa = actividadItem
        }
        return nuevosItems
    }

    fun crearListadoActividades(
        actividades: MutableList<Actividad>,
        eventos: MutableList<Evento>
    ): MutableList<ActividadItem>  {
        val items = mutableListOf<ActividadItem>()
        var posEvento: Int = 0
        var posActividad: Int = 0

        while (posEvento < eventos.size || posActividad < actividades.size) {
            val actividad = actividades.getOrNull(posActividad)
            val evento = eventos.getOrNull(posEvento)
            if (evento != null && actividad != null) {
                if (evento.fechaInicio!! <= actividad.fechaInicio!!) {
                    items.add(ActividadItem(TipoItem.EVENTO, null, evento, evento.fechaInicio!!))
                    posEvento += 1
                }
                else {
                    items.add(ActividadItem(TipoItem.ACTIVIDAD, actividad, null, actividad.fechaInicio!!))
                    posActividad += 1
                }
            }
            else {
                if (evento != null) {
                    items.add(
                        ActividadItem(
                            TipoItem.EVENTO,
                            null,
                            evento,
                            evento.fechaInicio.takeIf { it != null })
                    )
                    posEvento += 1
                }
                else if (actividad != null) {
                    items.add(
                        ActividadItem(
                            TipoItem.ACTIVIDAD,
                            actividad,
                            null,
                            actividad.fechaInicio.takeIf { it != null })
                    )
                    posActividad += 1
                }
            }
        }

        return items
    }

    class ActividadViewHolder(
        private var binding: FragmentActividadItemDetalleBinding
    ): RecyclerView.ViewHolder(binding.root) {
        private val formatoFecha =  DateFormat.getDateInstance(DateFormat.LONG, Locale.getDefault())
        private val  formatoHora: DateFormat =  DateFormat.getTimeInstance(DateFormat.SHORT, Locale.getDefault())

        fun verEnMapa(
            actividad: Actividad,
            view: View
        ) {
            val msg = "Seleccionada area id:${actividad.area!!.id} y nombre ${actividad.area!!.nombreLargo} desde una actividad."
            MyLogger.debug(msg)

//            actividad.let { a ->
//                val marcador = Marcador(nombreArea, a.nombre, a.area!!.latitud,
    //                    a.area!!.longitud, a.area!!.piso)
                    // TODO Navegar a MAPA con el marcador
//                evento.id?.let { id ->
//                    val direction =
//                        FragmentListadoEventosDirections.actionEventosFragmentToActividadesFragment()
//                    direction.idEvento = id
//                    view.findNavController().navigate(direction)
//                }
//            }
        }

        fun verMasNavegador(
            actividad: Actividad,
            view: View
        ) {
            val msg = "Se abre un enlace."
            MyLogger.debug(msg)
            val verMasWeb = Intent(Intent.ACTION_VIEW, Uri.parse(actividad.urlVerMas))
            ContextCompat.startActivity(view.context, verMasWeb, null)
        }

        fun exportarACalendario(
            actividad: Actividad,
            view: View
        ) {
            val intent = Intent(Intent.ACTION_EDIT)
            intent.type = "vnd.android.cursor.item/event"
            intent.putExtra("beginTime", actividad.fechaInicio!!.time)
            intent.putExtra("allDay", false)
            intent.putExtra("endTime", actividad.fechaFin!!.time)
            intent.putExtra("title", actividad.nombre)
            intent.putExtra("description", actividad.descripcion)
            intent.putExtra("eventLocation", actividad.area!!.nombreLargo.toString())
            ContextCompat.startActivity(view.context, intent, null)
        }

        fun bind(item: Actividad) {
            binding.apply {
                actividadNombre.text = item.nombre
                actividadDescripcion.text = item.descripcion
                actividadFechaIni.text = formatoHora.format(item.fechaInicio)
                actividadFechaFin.text = formatoHora.format(item.fechaFin)
                actividadArea.text = item.area?.nombreCorto

                if (item.urlVerMas.isNullOrBlank()) {
                    actividadVerMas.visibility = View.GONE
                }
                else {
                    actividadVerMas.visibility = View.VISIBLE
                }

                executePendingBindings()
            }
        }

    }

    class SeparadorFechaViewHolder(
        private var binding: FragmentActividadFechaSeparadorBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        private val formatoFecha =  DateFormat.getDateInstance(DateFormat.LONG, Locale.getDefault())
        fun agregarSeparacion(indice: Int, view: View) {
            // Agregar una ligera separación a los bloques por día de las actividades.
            val params: ViewGroup.MarginLayoutParams
            if (indice == 0) {
                params =  view.layoutParams as ViewGroup.MarginLayoutParams
                params.topMargin = 0
            }
            else {
                params =  view.layoutParams as ViewGroup.MarginLayoutParams
                params.topMargin = (8 * Resources.getSystem().displayMetrics.density + 0.5F).toInt()
            }
        }

        fun bind(fecha: Date) {
            binding.apply {
                actividadItemFecha.text = formatoFecha.format(fecha)
            }
        }
    }
}



    /** [TipoItem]
 * Tipo del item que se va a mostrar en la lista
 *
 * El ViewHolder puede ser de tipo Actividad, Evento o Fecha
 */
enum class TipoItem {
    ACTIVIDAD,
    EVENTO,
    FECHA
}

/**
 * [ActividadItem]
 *
 * Nos permite hacer una separación de los items de la lista como una fecha, evento o actividad.
 * Lo que va permitir agrupar las actividades por fecha, además de mostrar eventos y actividades
 * en la misma vista
 */
class ActividadItem (
    var tipo: TipoItem,
    var actividad: Actividad?,
    var evento: Evento?,
    var fecha: Date?
)

private class ActividadItemDiffCallback: DiffUtil.ItemCallback<ActividadItem>() {
    override fun areContentsTheSame(oldItem: ActividadItem, newItem: ActividadItem): Boolean {
        if (oldItem.tipo == newItem.tipo) {
            if (oldItem.tipo == TipoItem.ACTIVIDAD && oldItem.actividad?.id == newItem.actividad?.id) {
                return true
            }
            else if (oldItem.tipo == TipoItem.EVENTO && oldItem.evento?.id == newItem.evento?.id) {
                return true
            }
            else if (oldItem.tipo == TipoItem.FECHA && oldItem.fecha == newItem.fecha) {
                return true
            }
            return false
        }
        else {
            return false
        }
    }

    override fun areItemsTheSame(oldItem: ActividadItem, newItem: ActividadItem): Boolean {
        if (oldItem.tipo == newItem.tipo) {
            if (oldItem.tipo == TipoItem.ACTIVIDAD && oldItem.actividad?.id == newItem.actividad?.id) {
                return true
            }
            else if (oldItem.tipo == TipoItem.EVENTO && oldItem.evento?.id == newItem.evento?.id) {
                return true
            }
            else if (oldItem.tipo == TipoItem.FECHA && oldItem.fecha == newItem.fecha) {
                return true
            }
            return false
        }
        else {
            return false
        }
    }
}
