package com.example.academiaapp.ui.produtos

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.academiaapp.R
import com.example.academiaapp.data.model.Produto
import java.text.NumberFormat
import java.util.Locale

class ProdutoAdapter(
    private var produtos: List<Produto>,
    private val onEditClick: (Produto) -> Unit,
    private val onDeleteClick: (Produto) -> Unit
) : RecyclerView.Adapter<ProdutoAdapter.ProdutoViewHolder>() {

    inner class ProdutoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvNome: TextView = itemView.findViewById(R.id.tvNomeProduto)
        val tvPreco: TextView = itemView.findViewById(R.id.tvPrecoProduto)
        val tvCategoria: TextView = itemView.findViewById(R.id.tvCategoriaProduto)
        val tvDescricao: TextView = itemView.findViewById(R.id.tvDescricaoProduto)
        val tvEstoque: TextView = itemView.findViewById(R.id.tvEstoqueProduto)
        val btnEditar: ImageButton = itemView.findViewById(R.id.btnEditar)
        val btnDeletar: ImageButton = itemView.findViewById(R.id.btnDeletar)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProdutoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_produto, parent, false)
        return ProdutoViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProdutoViewHolder, position: Int) {
        val produto = produtos[position]

        holder.tvNome.text = produto.nome
        holder.tvPreco.text = formatarPreco(produto.preco)
        holder.tvCategoria.text = produto.categoria
        holder.tvDescricao.text = produto.descricao
        holder.tvEstoque.text = "Estoque: ${produto.estoque}"

        // Configurar botões
        holder.btnEditar.setOnClickListener {
            onEditClick(produto)
        }

        holder.btnDeletar.setOnClickListener {
            onDeleteClick(produto)
        }
    }

    override fun getItemCount(): Int = produtos.size

    fun atualizarLista(novaLista: List<Produto>) {
        produtos = novaLista
        notifyDataSetChanged()
    }

    private fun formatarPreco(preco: Double): String {
        val formatador = NumberFormat.getCurrencyInstance(Locale("pt", "BR"))
        return formatador.format(preco)
    }
}