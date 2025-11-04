package com.example.academiaapp.ui.produtos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.button.MaterialButton
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputEditText
import com.example.academiaapp.R
import com.example.academiaapp.data.database.ProdutoDao
import com.example.academiaapp.data.model.Produto

class ProdutosActivity : AppCompatActivity() {

    private lateinit var toolbar: MaterialToolbar
    private lateinit var rvProdutos: RecyclerView
    private lateinit var fabAdicionar: FloatingActionButton
    private lateinit var layoutVazio: LinearLayout
    private lateinit var produtoDao: ProdutoDao
    private lateinit var adapter: ProdutoAdapter
    private var produtos = listOf<Produto>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_produtos)

        // Inicializar componentes
        toolbar = findViewById(R.id.toolbar)
        rvProdutos = findViewById(R.id.rvProdutos)
        fabAdicionar = findViewById(R.id.fabAdicionar)
        layoutVazio = findViewById(R.id.layoutVazio)

        // Configurar toolbar
        toolbar.setNavigationOnClickListener {
            finish()
        }

        // Inicializar DAO
        produtoDao = ProdutoDao(this)

        // Configurar RecyclerView
        configurarRecyclerView()

        // Carregar produtos
        carregarProdutos()

        // Botão adicionar
        fabAdicionar.setOnClickListener {
            mostrarDialogProduto(null)
        }

        // Inserir dados de exemplo na primeira vez
        inserirDadosExemplo()
    }

    private fun configurarRecyclerView() {
        adapter = ProdutoAdapter(
            produtos = produtos,
            onEditClick = { produto ->
                mostrarDialogProduto(produto)
            },
            onDeleteClick = { produto ->
                confirmarExclusao(produto)
            }
        )

        rvProdutos.layoutManager = LinearLayoutManager(this)
        rvProdutos.adapter = adapter
    }

    private fun carregarProdutos() {
        produtos = produtoDao.listarTodos()
        adapter.atualizarLista(produtos)

        // Mostrar/ocultar mensagem de lista vazia
        if (produtos.isEmpty()) {
            layoutVazio.visibility = View.VISIBLE
            rvProdutos.visibility = View.GONE
        } else {
            layoutVazio.visibility = View.GONE
            rvProdutos.visibility = View.VISIBLE
        }
    }

    private fun mostrarDialogProduto(produto: Produto?) {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_produto, null)
        val dialog = AlertDialog.Builder(this)
            .setView(dialogView)
            .create()

        // Componentes do dialog
        val etNome = dialogView.findViewById<TextInputEditText>(R.id.etNomeProduto)
        val etDescricao = dialogView.findViewById<TextInputEditText>(R.id.etDescricaoProduto)
        val etCategoria = dialogView.findViewById<TextInputEditText>(R.id.etCategoriaProduto)
        val etPreco = dialogView.findViewById<TextInputEditText>(R.id.etPrecoProduto)
        val etEstoque = dialogView.findViewById<TextInputEditText>(R.id.etEstoqueProduto)
        val btnSalvar = dialogView.findViewById<MaterialButton>(R.id.btnSalvar)
        val btnCancelar = dialogView.findViewById<MaterialButton>(R.id.btnCancelar)

        // Se for edição, preencher campos
        if (produto != null) {
            etNome.setText(produto.nome)
            etDescricao.setText(produto.descricao)
            etCategoria.setText(produto.categoria)
            etPreco.setText(produto.preco.toString())
            etEstoque.setText(produto.estoque.toString())
        }

        btnSalvar.setOnClickListener {
            val nome = etNome.text.toString().trim()
            val descricao = etDescricao.text.toString().trim()
            val categoria = etCategoria.text.toString().trim()
            val precoStr = etPreco.text.toString().trim()
            val estoqueStr = etEstoque.text.toString().trim()

            // Validações
            if (nome.isEmpty()) {
                etNome.error = "Digite o nome"
                return@setOnClickListener
            }

            if (precoStr.isEmpty()) {
                etPreco.error = "Digite o preço"
                return@setOnClickListener
            }

            if (estoqueStr.isEmpty()) {
                etEstoque.error = "Digite o estoque"
                return@setOnClickListener
            }

            val preco = precoStr.toDoubleOrNull() ?: 0.0
            val estoque = estoqueStr.toIntOrNull() ?: 0

            if (produto == null) {
                // Inserir novo produto
                val novoProduto = Produto(
                    nome = nome,
                    descricao = descricao,
                    preco = preco,
                    estoque = estoque,
                    categoria = categoria
                )
                produtoDao.inserir(novoProduto)
                Toast.makeText(this, "Produto adicionado!", Toast.LENGTH_SHORT).show()
            } else {
                // Atualizar produto existente
                val produtoAtualizado = produto.copy(
                    nome = nome,
                    descricao = descricao,
                    preco = preco,
                    estoque = estoque,
                    categoria = categoria
                )
                produtoDao.atualizar(produtoAtualizado)
                Toast.makeText(this, "Produto atualizado!", Toast.LENGTH_SHORT).show()
            }

            carregarProdutos()
            dialog.dismiss()
        }

        btnCancelar.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }

    private fun confirmarExclusao(produto: Produto) {
        AlertDialog.Builder(this)
            .setTitle("Confirmar Exclusão")
            .setMessage("Deseja realmente excluir ${produto.nome}?")
            .setPositiveButton("Sim") { _, _ ->
                produtoDao.deletar(produto.id)
                Toast.makeText(this, "Produto excluído!", Toast.LENGTH_SHORT).show()
                carregarProdutos()
            }
            .setNegativeButton("Não", null)
            .show()
    }

    private fun inserirDadosExemplo() {
        val produtos = produtoDao.listarTodos()
        if (produtos.isEmpty()) {
            val exemplos = listOf(
                Produto(nome = "Whey Protein", descricao = "Suplemento proteico 1kg", preco = 89.90, estoque = 50, categoria = "Suplementos"),
                Produto(nome = "Creatina", descricao = "Creatina monohidratada 300g", preco = 59.90, estoque = 30, categoria = "Suplementos"),
                Produto(nome = "Luva de Treino", descricao = "Luva para musculação tamanho M", preco = 35.00, estoque = 20, categoria = "Acessórios"),
                Produto(nome = "Garrafa Squeeze", descricao = "Garrafa 1 litro", preco = 25.00, estoque = 40, categoria = "Acessórios")
            )
            exemplos.forEach { produtoDao.inserir(it) }
        }
    }
}