package com.example.academiaapp.data.model

data class Servico(
    val id: Int = 0,
    val nome: String,
    val descricao: String,
    val valor: Double,
    val duracao: Int, // em minutos
    val instrutor: String
)