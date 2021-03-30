package com.example.dr4_carlos_ferreira_tp3.model

import com.google.firebase.firestore.DocumentId

open class EmpresaLite (
    val razao: String? = null,
    val bairro: String? = null,
    @DocumentId
    val cnpj: String? = null
){
    override fun toString(): String = "$razao: $bairro"
}