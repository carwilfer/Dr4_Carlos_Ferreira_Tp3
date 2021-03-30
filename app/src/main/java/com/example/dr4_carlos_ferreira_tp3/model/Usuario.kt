package com.example.dr4_carlos_ferreira_tp3.model

import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.DocumentId

class Usuario (
    var nome: String? = null,
    var firebaseUser: FirebaseUser? = null,
    @DocumentId
    var uid: String? = null
)