package com.example.dr4_carlos_ferreira_tp3.model

import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.DocumentReference

class Fiscal (
    val nome: String? = null,
    val email: String? = null,
    @DocumentId
    var cpf: String? = null,
    var empresa: DocumentReference? = null
){
    override fun toString(): String = "$nome (Email.: $email)"
}