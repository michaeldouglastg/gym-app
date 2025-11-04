package com.example.academiaapp.data.database

import android.content.ContentValues
import android.content.Context
import com.example.academiaapp.data.model.Servico

class ServicoDao(context: Context) {
    private val dbHelper = DatabaseHelper(context)

    // CREATE - Inserir novo serviço
    fun inserir(servico: Servico): Long {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(DatabaseHelper.COL_SERV_NOME, servico.nome)
            put(DatabaseHelper.COL_SERV_DESCRICAO, servico.descricao)
            put(DatabaseHelper.COL_SERV_VALOR, servico.valor)
            put(DatabaseHelper.COL_SERV_DURACAO, servico.duracao)
            put(DatabaseHelper.COL_SERV_INSTRUTOR, servico.instrutor)
        }
        val id = db.insert(DatabaseHelper.TABLE_SERVICOS, null, values)
        db.close()
        return id
    }

    // READ - Buscar todos os serviços
    fun listarTodos(): List<Servico> {
        val servicos = mutableListOf<Servico>()
        val db = dbHelper.readableDatabase
        val cursor = db.query(
            DatabaseHelper.TABLE_SERVICOS,
            null, null, null, null, null,
            "${DatabaseHelper.COL_SERV_NOME} ASC"
        )

        with(cursor) {
            while (moveToNext()) {
                val servico = Servico(
                    id = getInt(getColumnIndexOrThrow(DatabaseHelper.COL_SERV_ID)),
                    nome = getString(getColumnIndexOrThrow(DatabaseHelper.COL_SERV_NOME)),
                    descricao = getString(getColumnIndexOrThrow(DatabaseHelper.COL_SERV_DESCRICAO)),
                    valor = getDouble(getColumnIndexOrThrow(DatabaseHelper.COL_SERV_VALOR)),
                    duracao = getInt(getColumnIndexOrThrow(DatabaseHelper.COL_SERV_DURACAO)),
                    instrutor = getString(getColumnIndexOrThrow(DatabaseHelper.COL_SERV_INSTRUTOR))
                )
                servicos.add(servico)
            }
        }
        cursor.close()
        db.close()
        return servicos
    }

    // READ - Buscar serviço por ID
    fun buscarPorId(id: Int): Servico? {
        val db = dbHelper.readableDatabase
        val selection = "${DatabaseHelper.COL_SERV_ID} = ?"
        val selectionArgs = arrayOf(id.toString())

        val cursor = db.query(
            DatabaseHelper.TABLE_SERVICOS,
            null, selection, selectionArgs, null, null, null
        )

        var servico: Servico? = null
        if (cursor.moveToFirst()) {
            servico = Servico(
                id = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_SERV_ID)),
                nome = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_SERV_NOME)),
                descricao = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_SERV_DESCRICAO)),
                valor = cursor.getDouble(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_SERV_VALOR)),
                duracao = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_SERV_DURACAO)),
                instrutor = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_SERV_INSTRUTOR))
            )
        }
        cursor.close()
        db.close()
        return servico
    }

    // READ - Buscar serviços por instrutor
    fun buscarPorInstrutor(instrutor: String): List<Servico> {
        val servicos = mutableListOf<Servico>()
        val db = dbHelper.readableDatabase
        val selection = "${DatabaseHelper.COL_SERV_INSTRUTOR} = ?"
        val selectionArgs = arrayOf(instrutor)

        val cursor = db.query(
            DatabaseHelper.TABLE_SERVICOS,
            null, selection, selectionArgs, null, null,
            "${DatabaseHelper.COL_SERV_NOME} ASC"
        )

        with(cursor) {
            while (moveToNext()) {
                val servico = Servico(
                    id = getInt(getColumnIndexOrThrow(DatabaseHelper.COL_SERV_ID)),
                    nome = getString(getColumnIndexOrThrow(DatabaseHelper.COL_SERV_NOME)),
                    descricao = getString(getColumnIndexOrThrow(DatabaseHelper.COL_SERV_DESCRICAO)),
                    valor = getDouble(getColumnIndexOrThrow(DatabaseHelper.COL_SERV_VALOR)),
                    duracao = getInt(getColumnIndexOrThrow(DatabaseHelper.COL_SERV_DURACAO)),
                    instrutor = getString(getColumnIndexOrThrow(DatabaseHelper.COL_SERV_INSTRUTOR))
                )
                servicos.add(servico)
            }
        }
        cursor.close()
        db.close()
        return servicos
    }

    // UPDATE - Atualizar serviço
    fun atualizar(servico: Servico): Int {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(DatabaseHelper.COL_SERV_NOME, servico.nome)
            put(DatabaseHelper.COL_SERV_DESCRICAO, servico.descricao)
            put(DatabaseHelper.COL_SERV_VALOR, servico.valor)
            put(DatabaseHelper.COL_SERV_DURACAO, servico.duracao)
            put(DatabaseHelper.COL_SERV_INSTRUTOR, servico.instrutor)
        }
        val selection = "${DatabaseHelper.COL_SERV_ID} = ?"
        val selectionArgs = arrayOf(servico.id.toString())
        val count = db.update(DatabaseHelper.TABLE_SERVICOS, values, selection, selectionArgs)
        db.close()
        return count
    }

    // DELETE - Deletar serviço
    fun deletar(id: Int): Int {
        val db = dbHelper.writableDatabase
        val selection = "${DatabaseHelper.COL_SERV_ID} = ?"
        val selectionArgs = arrayOf(id.toString())
        val count = db.delete(DatabaseHelper.TABLE_SERVICOS, selection, selectionArgs)
        db.close()
        return count
    }
}