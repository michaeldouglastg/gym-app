package com.example.academiaapp.ui.servicos

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
import com.example.academiaapp.data.database.ServicoDao
import com.example.academiaapp.data.model.Servico

class ServicosActivity : AppCompatActivity() {

    private lateinit var toolbar: MaterialToolbar
    private lateinit var rvServicos: RecyclerView
    private lateinit var fabAdicionar: FloatingActionButton
    private lateinit var layoutVazio: LinearLayout
    private lateinit var servicoDao: ServicoDao
    private lateinit var adapter: ServicoAdapter
    private var servicos = listOf<Servico>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_servicos)

        // Inicializar componentes
        toolbar = findViewById(R.id.toolbar)
        rvServicos = findViewById(R.id.rvServicos)
        fabAdicionar = findViewById(R.id.fabAdicionar)
        layoutVazio = findViewById(R.id.layoutVazio)

        // Configurar toolbar
        toolbar.setNavigationOnClickListener {
            finish()
        }

        // Inicializar DAO
        servicoDao = ServicoDao(this)

        // Configurar RecyclerView
        configurarRecyclerView()

        // Carregar serviços
        carregarServicos()

        // Botão adicionar
        fabAdicionar.setOnClickListener {
            mostrarDialogServico(null)
        }

        // Inserir dados de exemplo na primeira vez
        inserirDadosExemplo()
    }

    private fun configurarRecyclerView() {
        adapter = ServicoAdapter(
            servicos = servicos,
            onEditClick = { servico ->
                mostrarDialogServico(servico)
            },
            onDeleteClick = { servico ->
                confirmarExclusao(servico)
            }
        )

        rvServicos.layoutManager = LinearLayoutManager(this)
        rvServicos.adapter = adapter
    }

    private fun carregarServicos() {
        servicos = servicoDao.listarTodos()
        adapter.atualizarLista(servicos)

        // Mostrar/ocultar mensagem de lista vazia
        if (servicos.isEmpty()) {
            layoutVazio.visibility = View.VISIBLE
            rvServicos.visibility = View.GONE
        } else {
            layoutVazio.visibility = View.GONE
            rvServicos.visibility = View.VISIBLE
        }
    }

    private fun mostrarDialogServico(servico: Servico?) {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_servico, null)
        val dialog = AlertDialog.Builder(this)
            .setView(dialogView)
            .create()

        // Componentes do dialog
        val etNome = dialogView.findViewById<TextInputEditText>(R.id.etNomeServico)
        val etDescricao = dialogView.findViewById<TextInputEditText>(R.id.etDescricaoServico)
        val etInstrutor = dialogView.findViewById<TextInputEditText>(R.id.etInstrutorServico)
        val etValor = dialogView.findViewById<TextInputEditText>(R.id.etValorServico)
        val etDuracao = dialogView.findViewById<TextInputEditText>(R.id.etDuracaoServico)
        val btnSalvar = dialogView.findViewById<MaterialButton>(R.id.btnSalvar)
        val btnCancelar = dialogView.findViewById<MaterialButton>(R.id.btnCancelar)

        // Se for edição, preencher campos
        if (servico != null) {
            etNome.setText(servico.nome)
            etDescricao.setText(servico.descricao)
            etInstrutor.setText(servico.instrutor)
            etValor.setText(servico.valor.toString())
            etDuracao.setText(servico.duracao.toString())
        }

        btnSalvar.setOnClickListener {
            val nome = etNome.text.toString().trim()
            val descricao = etDescricao.text.toString().trim()
            val instrutor = etInstrutor.text.toString().trim()
            val valorStr = etValor.text.toString().trim()
            val duracaoStr = etDuracao.text.toString().trim()

            // Validações
            if (nome.isEmpty()) {
                etNome.error = "Digite o nome"
                return@setOnClickListener
            }

            if (instrutor.isEmpty()) {
                etInstrutor.error = "Digite o instrutor"
                return@setOnClickListener
            }

            if (valorStr.isEmpty()) {
                etValor.error = "Digite o valor"
                return@setOnClickListener
            }

            if (duracaoStr.isEmpty()) {
                etDuracao.error = "Digite a duração"
                return@setOnClickListener
            }

            val valor = valorStr.toDoubleOrNull() ?: 0.0
            val duracao = duracaoStr.toIntOrNull() ?: 0

            if (servico == null) {
                // Inserir novo serviço
                val novoServico = Servico(
                    nome = nome,
                    descricao = descricao,
                    valor = valor,
                    duracao = duracao,
                    instrutor = instrutor
                )
                servicoDao.inserir(novoServico)
                Toast.makeText(this, "Serviço adicionado!", Toast.LENGTH_SHORT).show()
            } else {
                // Atualizar serviço existente
                val servicoAtualizado = servico.copy(
                    nome = nome,
                    descricao = descricao,
                    valor = valor,
                    duracao = duracao,
                    instrutor = instrutor
                )
                servicoDao.atualizar(servicoAtualizado)
                Toast.makeText(this, "Serviço atualizado!", Toast.LENGTH_SHORT).show()
            }

            carregarServicos()
            dialog.dismiss()
        }

        btnCancelar.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }

    private fun confirmarExclusao(servico: Servico) {
        AlertDialog.Builder(this)
            .setTitle("Confirmar Exclusão")
            .setMessage("Deseja realmente excluir ${servico.nome}?")
            .setPositiveButton("Sim") { _, _ ->
                servicoDao.deletar(servico.id)
                Toast.makeText(this, "Serviço excluído!", Toast.LENGTH_SHORT).show()
                carregarServicos()
            }
            .setNegativeButton("Não", null)
            .show()
    }

    private fun inserirDadosExemplo() {
        val servicos = servicoDao.listarTodos()
        if (servicos.isEmpty()) {
            val exemplos = listOf(
                Servico(nome = "Musculação", descricao = "Treino personalizado com instrutor", valor = 150.00, duracao = 60, instrutor = "Carlos Silva"),
                Servico(nome = "Spinning", descricao = "Aula de bike indoor em grupo", valor = 80.00, duracao = 45, instrutor = "Marina Costa"),
                Servico(nome = "Yoga", descricao = "Aula de yoga para relaxamento e flexibilidade", valor = 70.00, duracao = 60, instrutor = "Juliana Alves"),
                Servico(nome = "CrossFit", descricao = "Treino funcional de alta intensidade", valor = 180.00, duracao = 50, instrutor = "Roberto Santos"),
                Servico(nome = "Pilates", descricao = "Fortalecimento e alongamento", valor = 90.00, duracao = 50, instrutor = "Ana Paula")
            )
            exemplos.forEach { servicoDao.inserir(it) }
        }
    }
}