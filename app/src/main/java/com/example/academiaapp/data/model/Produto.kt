package com.example.academiaapp.data.model

data class Produto(
    val id: Int = 0,
    val nome: String,
    val descricao: String,
    val preco: Double,
    val estoque: Int,
    val categoria: String
)