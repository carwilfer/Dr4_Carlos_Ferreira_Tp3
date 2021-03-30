package com.example.dr4_carlos_ferreira_tp3.model

import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.DocumentReference

class Avaliacao (
    var firebaseUser: FirebaseUser? = null,
    val pergunta: String? = null,
    val resposta: Int? = null,
    @DocumentId
    var uid: String? = null,
    var empresa: DocumentReference? = null
)