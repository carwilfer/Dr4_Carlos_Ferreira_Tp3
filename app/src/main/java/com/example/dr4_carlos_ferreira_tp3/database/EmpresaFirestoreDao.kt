package com.example.dr4_carlos_ferreira_tp3.database

import com.example.dr4_carlos_ferreira_tp3.model.Empresa
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot

class EmpresaFirestoreDao :
    EmpresaDao {
    private val collection = FirebaseFirestore
        .getInstance()
        .collection("empresas")

    override fun createOrUpdate(empresa: Empresa): Task<Void> {
        return collection
            .document(empresa.cnpj!!)
            .set(empresa)
    }

    override fun delete(empresa: Empresa): Task<Void> {
        return collection
            .document(empresa.cnpj!!)
            .delete()
    }

    override fun read(cnpj: String): Task<DocumentSnapshot> {
        return collection
            .document(cnpj)
            .get()
    }

    override fun all(): CollectionReference {
        return collection
    }

    override fun allDocuments(): Task<QuerySnapshot> {
        return collection.get()
    }
}