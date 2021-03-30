package com.example.dr4_carlos_ferreira_tp3.ui.fiscal.cadastro

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dr4_carlos_ferreira_tp3.database.EmpresaFirestoreDao
import com.example.dr4_carlos_ferreira_tp3.database.FiscalFirestoreDao
import com.example.dr4_carlos_ferreira_tp3.model.Fiscal
import com.google.firebase.firestore.FirebaseFirestore

class FormFiscalViewModel : ViewModel() {
    private val _empresa = MutableLiveData<List<String>>()
    val empresa: LiveData<List<String>> = _empresa

    private var cnpjDaEmpresa: String? = null

    private val _status = MutableLiveData<Boolean>()
    val status: LiveData<Boolean> = _status

    private val _msg = MutableLiveData<String>()
    val msg: LiveData<String> = _msg

    init {
        EmpresaFirestoreDao().allDocuments()
            .addOnSuccessListener {
                val listaEmpresas = mutableListOf<String>("Selecionar uma empresa!")
                it.documents.forEach {
                    listaEmpresas.add(it.id)
                }
                _empresa.value = listaEmpresas
            }
    }
    fun salvarFiscal(nome: String, email: String?, cpf: String){
        val fiscal =
            Fiscal(nome, email, cpf)

        fiscal.empresa = FirebaseFirestore
            .getInstance()
            .collection("empresas")
            .document(cnpjDaEmpresa!!)

        FiscalFirestoreDao()
            .createOrUpdate(fiscal)
            .addOnSuccessListener {
                _status.value = true
                _msg.value = "Fiscal salvo com sucesso."
            }
            .addOnFailureListener {
                _msg.value = "${it.message}"
            }
    }

    fun definirCnpjSelecionado(cnpj: String) {
        cnpjDaEmpresa = cnpj
    }
}