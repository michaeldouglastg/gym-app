package com.example.academiaapp.ui.servicos

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.academiaapp.R
import com.example.academiaapp.data.model.Servico
import java.text.NumberFormat
import java.util.Locale

class ServicoAdapter(
    private var servicos: List<Servico>,
    private val onEditClick: (Servico) -> Unit,
    private val onDeleteClick: (Servico) -> Unit
) : RecyclerView.Adapter<ServicoAdapter.ServicoViewHolder>() {

    inner class ServicoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvNome: TextView = itemView.findViewById(R.id.tvNomeServico)
        val tvValor: TextView = itemView.findViewById(R.id.tvValorServico)
        val tvInstrutor: TextView = itemView.findViewById(R.id.tvInstrutorServico)
        val tvDescricao: TextView = itemView.findViewById(R.id.tvDescricaoServico)
        val tvDuracao: TextView = itemView.findViewById(R.id.tvDuracaoServico)
        val btnEditar: ImageButton = itemView.findViewById(R.id.btnEditar)
        val btnDeletar: ImageButton = itemView.findViewById(R.id.btnDeletar)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ServicoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_servico, parent, false)
        return ServicoViewHolder(view)
    }

    override fun onBindViewHolder(holder: ServicoViewHolder, position: Int) {
        val servico = servicos[position]

        holder.tvNome.text = servico.nome
        holder.tvValor.text = formatarValor(servico.valor)
        holder.tvInstrutor.text = "Instrutor: ${servico.instrutor}"
        holder.tvDescricao.text = servico.descricao
        holder.tvDuracao.text = "Duração: ${servico.duracao} min"

        // Configurar botões
        holder.btnEditar.setOnClickListener {
            onEditClick(servico)
        }

        holder.btnDeletar.setOnClickListener {
            onDeleteClick(servico)
        }
    }

    override fun getItemCount(): Int = servicos.size

    fun atualizarLista(novaLista: List<Servico>) {
        servicos = novaLista
        notifyDataSetChanged()
    }

    private fun formatarValor(valor: Double): String {
        val formatador = NumberFormat.getCurrencyInstance(Locale("pt", "BR"))
        return formatador.format(valor)
    }
}