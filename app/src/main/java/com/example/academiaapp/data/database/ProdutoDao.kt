package com.example.academiaapp.data.database

import android.content.ContentValues
import android.content.Context
import com.example.academiaapp.data.model.Produto

class ProdutoDao(context: Context) {
    private val dbHelper = DatabaseHelper(context)

    // CREATE - Inserir novo produto
    fun inserir(produto: Produto): Long {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(DatabaseHelper.COL_PROD_NOME, produto.nome)
            put(DatabaseHelper.COL_PROD_DESCRICAO, produto.descricao)
            put(DatabaseHelper.COL_PROD_PRECO, produto.preco)
            put(DatabaseHelper.COL_PROD_ESTOQUE, produto.estoque)
            put(DatabaseHelper.COL_PROD_CATEGORIA, produto.categoria)
        }
        val id = db.insert(DatabaseHelper.TABLE_PRODUTOS, null, values)
        db.close()
        return id
    }

    // READ - Buscar todos os produtos
    fun listarTodos(): List<Produto> {
        val produtos = mutableListOf<Produto>()
        val db = dbHelper.readableDatabase
        val cursor = db.query(
            DatabaseHelper.TABLE_PRODUTOS,
            null, null, null, null, null,
            "${DatabaseHelper.COL_PROD_NOME} ASC"
        )

        with(cursor) {
            while (moveToNext()) {
                val produto = Produto(
                    id = getInt(getColumnIndexOrThrow(DatabaseHelper.COL_PROD_ID)),
                    nome = getString(getColumnIndexOrThrow(DatabaseHelper.COL_PROD_NOME)),
                    descricao = getString(getColumnIndexOrThrow(DatabaseHelper.COL_PROD_DESCRICAO)),
                    preco = getDouble(getColumnIndexOrThrow(DatabaseHelper.COL_PROD_PRECO)),
                    estoque = getInt(getColumnIndexOrThrow(DatabaseHelper.COL_PROD_ESTOQUE)),
                    categoria = getString(getColumnIndexOrThrow(DatabaseHelper.COL_PROD_CATEGORIA))
                )
                produtos.add(produto)
            }
        }
        cursor.close()
        db.close()
        return produtos
    }

    // READ - Buscar produto por ID
    fun buscarPorId(id: Int): Produto? {
        val db = dbHelper.readableDatabase
        val selection = "${DatabaseHelper.COL_PROD_ID} = ?"
        val selectionArgs = arrayOf(id.toString())

        val cursor = db.query(
            DatabaseHelper.TABLE_PRODUTOS,
            null, selection, selectionArgs, null, null, null
        )

        var produto: Produto? = null
        if (cursor.moveToFirst()) {
            produto = Produto(
                id = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_PROD_ID)),
                nome = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_PROD_NOME)),
                descricao = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_PROD_DESCRICAO)),
                preco = cursor.getDouble(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_PROD_PRECO)),
                estoque = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_PROD_ESTOQUE)),
                categoria = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_PROD_CATEGORIA))
            )
        }
        cursor.close()
        db.close()
        return produto
    }

    // READ - Buscar produtos por categoria
    fun buscarPorCategoria(categoria: String): List<Produto> {
        val produtos = mutableListOf<Produto>()
        val db = dbHelper.readableDatabase
        val selection = "${DatabaseHelper.COL_PROD_CATEGORIA} = ?"
        val selectionArgs = arrayOf(categoria)

        val cursor = db.query(
            DatabaseHelper.TABLE_PRODUTOS,
            null, selection, selectionArgs, null, null,
            "${DatabaseHelper.COL_PROD_NOME} ASC"
        )

        with(cursor) {
            while (moveToNext()) {
                val produto = Produto(
                    id = getInt(getColumnIndexOrThrow(DatabaseHelper.COL_PROD_ID)),
                    nome = getString(getColumnIndexOrThrow(DatabaseHelper.COL_PROD_NOME)),
                    descricao = getString(getColumnIndexOrThrow(DatabaseHelper.COL_PROD_DESCRICAO)),
                    preco = getDouble(getColumnIndexOrThrow(DatabaseHelper.COL_PROD_PRECO)),
                    estoque = getInt(getColumnIndexOrThrow(DatabaseHelper.COL_PROD_ESTOQUE)),
                    categoria = getString(getColumnIndexOrThrow(DatabaseHelper.COL_PROD_CATEGORIA))
                )
                produtos.add(produto)
            }
        }
        cursor.close()
        db.close()
        return produtos
    }

    // UPDATE - Atualizar produto
    fun atualizar(produto: Produto): Int {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(DatabaseHelper.COL_PROD_NOME, produto.nome)
            put(DatabaseHelper.COL_PROD_DESCRICAO, produto.descricao)
            put(DatabaseHelper.COL_PROD_PRECO, produto.preco)
            put(DatabaseHelper.COL_PROD_ESTOQUE, produto.estoque)
            put(DatabaseHelper.COL_PROD_CATEGORIA, produto.categoria)
        }
        val selection = "${DatabaseHelper.COL_PROD_ID} = ?"
        val selectionArgs = arrayOf(produto.id.toString())
        val count = db.update(DatabaseHelper.TABLE_PRODUTOS, values, selection, selectionArgs)
        db.close()
        return count
    }

    // DELETE - Deletar produto
    fun deletar(id: Int): Int {
        val db = dbHelper.writableDatabase
        val selection = "${DatabaseHelper.COL_PROD_ID} = ?"
        val selectionArgs = arrayOf(id.toString())
        val count = db.delete(DatabaseHelper.TABLE_PRODUTOS, selection, selectionArgs)
        db.close()
        return count
    }
}