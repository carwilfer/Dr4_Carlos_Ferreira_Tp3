package com.example.dr4_carlos_ferreira_tp3.database

import com.example.dr4_carlos_ferreira_tp3.model.Fiscal
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore

class FiscalFirestoreDao {
    private val collection = FirebaseFirestore
        .getInstance()
        .collection("fiscal")

    fun createOrUpdate(fiscal: Fiscal): Task<Void> {
        return collection
            .document(fiscal.cpf!!)
            .set(fiscal)
    }

    fun all(): CollectionReference {
        return collection
    }
}