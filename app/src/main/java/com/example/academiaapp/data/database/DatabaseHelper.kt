package com.example.academiaapp.data.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "academia.db"
        private const val DATABASE_VERSION = 1

        // Tabela Usuarios
        const val TABLE_USUARIOS = "usuarios"
        const val COL_USER_ID = "id"
        const val COL_USER_NOME = "nome"
        const val COL_USER_EMAIL = "email"
        const val COL_USER_SENHA = "senha"
        const val COL_USER_TELEFONE = "telefone"
        const val COL_USER_DATA_CADASTRO = "data_cadastro"

        // Tabela Produtos
        const val TABLE_PRODUTOS = "produtos"
        const val COL_PROD_ID = "id"
        const val COL_PROD_NOME = "nome"
        const val COL_PROD_DESCRICAO = "descricao"
        const val COL_PROD_PRECO = "preco"
        const val COL_PROD_ESTOQUE = "estoque"
        const val COL_PROD_CATEGORIA = "categoria"

        // Tabela Servicos
        const val TABLE_SERVICOS = "servicos"
        const val COL_SERV_ID = "id"
        const val COL_SERV_NOME = "nome"
        const val COL_SERV_DESCRICAO = "descricao"
        const val COL_SERV_VALOR = "valor"
        const val COL_SERV_DURACAO = "duracao"
        const val COL_SERV_INSTRUTOR = "instrutor"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        // Criar tabela Usuarios
        val createUsuarios = """
            CREATE TABLE $TABLE_USUARIOS (
                $COL_USER_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COL_USER_NOME TEXT NOT NULL,
                $COL_USER_EMAIL TEXT NOT NULL UNIQUE,
                $COL_USER_SENHA TEXT NOT NULL,
                $COL_USER_TELEFONE TEXT,
                $COL_USER_DATA_CADASTRO TEXT
            )
        """.trimIndent()

        // Criar tabela Produtos
        val createProdutos = """
            CREATE TABLE $TABLE_PRODUTOS (
                $COL_PROD_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COL_PROD_NOME TEXT NOT NULL,
                $COL_PROD_DESCRICAO TEXT,
                $COL_PROD_PRECO REAL NOT NULL,
                $COL_PROD_ESTOQUE INTEGER NOT NULL,
                $COL_PROD_CATEGORIA TEXT
            )
        """.trimIndent()

        // Criar tabela Servicos
        val createServicos = """
            CREATE TABLE $TABLE_SERVICOS (
                $COL_SERV_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COL_SERV_NOME TEXT NOT NULL,
                $COL_SERV_DESCRICAO TEXT,
                $COL_SERV_VALOR REAL NOT NULL,
                $COL_SERV_DURACAO INTEGER NOT NULL,
                $COL_SERV_INSTRUTOR TEXT
            )
        """.trimIndent()

        db?.execSQL(createUsuarios)
        db?.execSQL(createProdutos)
        db?.execSQL(createServicos)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_USUARIOS")
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_PRODUTOS")
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_SERVICOS")
        onCreate(db)
    }
}