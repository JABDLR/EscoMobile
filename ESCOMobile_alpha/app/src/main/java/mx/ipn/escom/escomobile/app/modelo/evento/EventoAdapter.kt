package mx.ipn.escom.escomobile.app.modelo.evento

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import mx.ipn.escom.escomobile.app.databinding.FragmentEventoItemDetalleBinding
import mx.ipn.escom.escomobile.app.repositorio.evento.Evento
import mx.ipn.escom.escomobile.app.vistas.actividad.FragmentListadoActividadesDirections
import java.text.DateFormat
import java.util.*

class EventoAdapter : ListAdapter<Evento, RecyclerView.ViewHolder>(EventoDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return EventoViewHolder(FragmentEventoItemDetalleBinding.inflate(
            LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, posicion: Int) {
        val evento = getItem(posicion)
        ( holder as EventoViewHolder ).bind(evento)
        holder.itemView.setOnClickListener { holder.verActividades(evento, it) }
    }


    class EventoViewHolder (
        private var binding: FragmentEventoItemDetalleBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        private val formatoFecha =  DateFormat.getDateInstance(DateFormat.LONG, Locale.getDefault())

        fun verActividades (
            evento: Evento,
            view: View
        ) {
            evento.id?.let { id ->
                val direction =
                    FragmentListadoActividadesDirections.actionVerActividadesEvento()
                direction.idEvento = id
                view.findNavController().navigate(direction)
            }
        }

        fun verEnMapa(
            evento: Evento,
            view: View
        ) {

            //TODO Crear el marcador de tipo mapa
            //TODO Crear direcciones para navegar con un marcador al mapa
        }

        fun bind(item: Evento) {
            binding.apply {
                eventoNombre.text = item.nombre
                eventoDescripcion.text = item.descripcion
                eventoFechaIni.text = formatoFecha.format(item.fechaInicio)
                eventoFechaFin.text = formatoFecha.format(item.fechaFin)

                executePendingBindings()
            }
        }

    }
}

private class EventoDiffCallback : DiffUtil.ItemCallback<Evento>() {
    override fun areContentsTheSame(oldItem: Evento, newItem: Evento): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areItemsTheSame(oldItem: Evento, newItem: Evento): Boolean {
        return oldItem == newItem
    }
}
