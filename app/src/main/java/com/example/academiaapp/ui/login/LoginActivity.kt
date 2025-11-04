package com.example.academiaapp.ui.login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.example.academiaapp.R
import com.example.academiaapp.data.database.UsuarioDao
import com.example.academiaapp.data.model.Usuario
import com.example.academiaapp.ui.home.HomeActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var etEmail: TextInputEditText
    private lateinit var etSenha: TextInputEditText
    private lateinit var btnEntrar: MaterialButton
    private lateinit var btnCadastrar: MaterialButton
    private lateinit var usuarioDao: UsuarioDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Inicializar componentes
        etEmail = findViewById(R.id.etEmail)
        etSenha = findViewById(R.id.etSenha)
        btnEntrar = findViewById(R.id.btnEntrar)
        btnCadastrar = findViewById(R.id.btnCadastrar)

        // Inicializar DAO
        usuarioDao = UsuarioDao(this)

        // Criar usuário padrão se não existir
        criarUsuarioPadrao()

        // Configurar botões
        btnEntrar.setOnClickListener {
            realizarLogin()
        }

        btnCadastrar.setOnClickListener {
            realizarCadastro()
        }
    }

    private fun realizarLogin() {
        val email = etEmail.text.toString().trim()
        val senha = etSenha.text.toString().trim()

        // Validações
        if (email.isEmpty()) {
            etEmail.error = "Digite o email"
            etEmail.requestFocus()
            return
        }

        if (senha.isEmpty()) {
            etSenha.error = "Digite a senha"
            etSenha.requestFocus()
            return
        }

        // Autenticar
        val usuario = usuarioDao.autenticar(email, senha)

        if (usuario != null) {
            Toast.makeText(this, "Bem-vindo, ${usuario.nome}!", Toast.LENGTH_SHORT).show()

            // Ir para tela Home
            val intent = Intent(this, HomeActivity::class.java)
            intent.putExtra("USUARIO_NOME", usuario.nome)
            intent.putExtra("USUARIO_ID", usuario.id)
            startActivity(intent)
            finish()
        } else {
            Toast.makeText(this, "Email ou senha incorretos", Toast.LENGTH_SHORT).show()
        }
    }

    private fun realizarCadastro() {
        val email = etEmail.text.toString().trim()
        val senha = etSenha.text.toString().trim()

        // Validações
        if (email.isEmpty() || senha.isEmpty()) {
            Toast.makeText(this, "Preencha email e senha para cadastrar", Toast.LENGTH_SHORT).show()
            return
        }

        if (senha.length < 4) {
            Toast.makeText(this, "Senha deve ter no mínimo 4 caracteres", Toast.LENGTH_SHORT).show()
            return
        }

        // Criar novo usuário
        val novoUsuario = Usuario(
            nome = email.substringBefore("@"),
            email = email,
            senha = senha,
            telefone = ""
        )

        val id = usuarioDao.inserir(novoUsuario)

        if (id > 0) {
            Toast.makeText(this, "Cadastro realizado! Faça login", Toast.LENGTH_SHORT).show()
            etSenha.setText("")
        } else {
            Toast.makeText(this, "Erro ao cadastrar. Email já existe?", Toast.LENGTH_SHORT).show()
        }
    }

    private fun criarUsuarioPadrao() {
        // Criar usuário padrão para testes: admin@academia.com / admin
        val usuarioTeste = usuarioDao.autenticar("admin@academia.com", "admin")

        if (usuarioTeste == null) {
            val admin = Usuario(
                nome = "Administrador",
                email = "admin@academia.com",
                senha = "admin",
                telefone = "(11) 99999-9999"
            )
            usuarioDao.inserir(admin)
        }
    }
}