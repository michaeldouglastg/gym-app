package com.example.academiaapp.ui.home

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView
import com.example.academiaapp.R
import com.example.academiaapp.ui.login.LoginActivity
import com.example.academiaapp.ui.produtos.ProdutosActivity
import com.example.academiaapp.ui.servicos.ServicosActivity

class HomeActivity : AppCompatActivity() {

    private lateinit var tvNomeUsuario: TextView
    private lateinit var cardProdutos: MaterialCardView
    private lateinit var cardServicos: MaterialCardView
    private lateinit var btnSair: MaterialButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        // Inicializar componentes
        tvNomeUsuario = findViewById(R.id.tvNomeUsuario)
        cardProdutos = findViewById(R.id.cardProdutos)
        cardServicos = findViewById(R.id.cardServicos)
        btnSair = findViewById(R.id.btnSair)

        // Receber dados do usuário
        val nomeUsuario = intent.getStringExtra("USUARIO_NOME") ?: "Usuário"
        tvNomeUsuario.text = nomeUsuario

        // Configurar navegação
        cardProdutos.setOnClickListener {
            val intent = Intent(this, ProdutosActivity::class.java)
            startActivity(intent)
        }

        cardServicos.setOnClickListener {
            val intent = Intent(this, ServicosActivity::class.java)
            startActivity(intent)
        }

        btnSair.setOnClickListener {
            // Voltar para tela de login
            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }
    }
}