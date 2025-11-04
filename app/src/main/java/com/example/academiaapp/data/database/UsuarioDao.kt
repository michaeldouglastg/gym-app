package com.example.academiaapp.data.database

import android.content.ContentValues
import android.content.Context
import com.example.academiaapp.data.model.Usuario
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class UsuarioDao(context: Context) {
    private val dbHelper = DatabaseHelper(context)

    // CREATE - Inserir novo usuário
    fun inserir(usuario: Usuario): Long {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(DatabaseHelper.COL_USER_NOME, usuario.nome)
            put(DatabaseHelper.COL_USER_EMAIL, usuario.email)
            put(DatabaseHelper.COL_USER_SENHA, usuario.senha)
            put(DatabaseHelper.COL_USER_TELEFONE, usuario.telefone)
            put(DatabaseHelper.COL_USER_DATA_CADASTRO, getCurrentDate())
        }
        val id = db.insert(DatabaseHelper.TABLE_USUARIOS, null, values)
        db.close()
        return id
    }

    // READ - Buscar todos os usuários
    fun listarTodos(): List<Usuario> {
        val usuarios = mutableListOf<Usuario>()
        val db = dbHelper.readableDatabase
        val cursor = db.query(
            DatabaseHelper.TABLE_USUARIOS,
            null, null, null, null, null, null
        )

        with(cursor) {
            while (moveToNext()) {
                val usuario = Usuario(
                    id = getInt(getColumnIndexOrThrow(DatabaseHelper.COL_USER_ID)),
                    nome = getString(getColumnIndexOrThrow(DatabaseHelper.COL_USER_NOME)),
                    email = getString(getColumnIndexOrThrow(DatabaseHelper.COL_USER_EMAIL)),
                    senha = getString(getColumnIndexOrThrow(DatabaseHelper.COL_USER_SENHA)),
                    telefone = getString(getColumnIndexOrThrow(DatabaseHelper.COL_USER_TELEFONE)),
                    dataCadastro = getString(getColumnIndexOrThrow(DatabaseHelper.COL_USER_DATA_CADASTRO))
                )
                usuarios.add(usuario)
            }
        }
        cursor.close()
        db.close()
        return usuarios
    }

    // READ - Buscar usuário por email e senha (Login)
    fun autenticar(email: String, senha: String): Usuario? {
        val db = dbHelper.readableDatabase
        val selection = "${DatabaseHelper.COL_USER_EMAIL} = ? AND ${DatabaseHelper.COL_USER_SENHA} = ?"
        val selectionArgs = arrayOf(email, senha)

        val cursor = db.query(
            DatabaseHelper.TABLE_USUARIOS,
            null, selection, selectionArgs, null, null, null
        )

        var usuario: Usuario? = null
        if (cursor.moveToFirst()) {
            usuario = Usuario(
                id = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_USER_ID)),
                nome = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_USER_NOME)),
                email = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_USER_EMAIL)),
                senha = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_USER_SENHA)),
                telefone = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_USER_TELEFONE)),
                dataCadastro = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_USER_DATA_CADASTRO))
            )
        }
        cursor.close()
        db.close()
        return usuario
    }

    // UPDATE - Atualizar usuário
    fun atualizar(usuario: Usuario): Int {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(DatabaseHelper.COL_USER_NOME, usuario.nome)
            put(DatabaseHelper.COL_USER_EMAIL, usuario.email)
            put(DatabaseHelper.COL_USER_SENHA, usuario.senha)
            put(DatabaseHelper.COL_USER_TELEFONE, usuario.telefone)
        }
        val selection = "${DatabaseHelper.COL_USER_ID} = ?"
        val selectionArgs = arrayOf(usuario.id.toString())
        val count = db.update(DatabaseHelper.TABLE_USUARIOS, values, selection, selectionArgs)
        db.close()
        return count
    }

    // DELETE - Deletar usuário
    fun deletar(id: Int): Int {
        val db = dbHelper.writableDatabase
        val selection = "${DatabaseHelper.COL_USER_ID} = ?"
        val selectionArgs = arrayOf(id.toString())
        val count = db.delete(DatabaseHelper.TABLE_USUARIOS, selection, selectionArgs)
        db.close()
        return count
    }

    private fun getCurrentDate(): String {
        val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault())
        return sdf.format(Date())
    }
}