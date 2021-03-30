package com.example.dr4_carlos_ferreira_tp3.model

class Empresa (
    razao: String? = null,
    bairro: String? = null,
    cnpj: String? = null
): EmpresaLite(razao, cnpj, bairro) {
    override fun toString(): String = "$razao: $bairro"
}