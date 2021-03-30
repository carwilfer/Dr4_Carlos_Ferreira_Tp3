package com.example.dr4_carlos_ferreira_tp3.database

import com.example.dr4_carlos_ferreira_tp3.model.Empresa
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot

interface EmpresaDao {
    fun createOrUpdate(empresa: Empresa): Task<Void>
    fun read(cnpj: String): Task<DocumentSnapshot>
    fun delete(empresa: Empresa): Task<Void>
    fun all(): CollectionReference
    fun allDocuments(): Task<QuerySnapshot>
}