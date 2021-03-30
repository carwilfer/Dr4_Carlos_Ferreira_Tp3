package com.example.dr4_carlos_ferreira_tp3.ui.empresa.cadastro

import android.app.Application
import android.net.Uri
import android.util.Log
import androidx.core.net.toUri
import androidx.lifecycle.*
import com.example.dr4_carlos_ferreira_tp3.database.EmpresaDao
import com.example.dr4_carlos_ferreira_tp3.model.Empresa
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.io.File

class FormEmpresaViewModel (
    val empresaDao: EmpresaDao,
    application: Application
) : AndroidViewModel(application) {

    private val app = application

    private val _imagemEmpresa = MutableLiveData<Uri>()
    val imagemEmpresa: LiveData<Uri> = _imagemEmpresa

    private val _empresa = MutableLiveData<Empresa>()
    val empresa: LiveData<Empresa> = _empresa

    private val _status = MutableLiveData<Boolean>()
    val status: LiveData<Boolean> = _status

    private val _msg = MutableLiveData<String?>()
    val msg: LiveData<String?> = _msg

    init {
        _status.value = false
        _msg.value = null
    }

    fun salvarEmpresa(
        razao: String, bairro: String, cnpj: String) {
        _status.value = false
        _msg.value = "Por favor, aguarde a persistência!"

        val empresa = Empresa(
            razao,
            bairro,
            cnpj
        )

        uploadImageEmpresa(cnpj)
        empresaDao.createOrUpdate(empresa)
            .addOnSuccessListener {
                _status.value = true
                _msg.value = "Persistência realizada!"
            }
            .addOnFailureListener {
                _msg.value = "Persistência falhou!"
                Log.e("EmpresaDaoFirebase", "${it.message}")
            }
    }

    fun uploadImageEmpresa(cnpj: String) {
        getFileReference(cnpj)
            .putFile(imagemEmpresa!!.value!!)
            .addOnSuccessListener {
                _msg.value = "Imagem persistida com sucesso."
            }
            .addOnFailureListener {
                _msg.value = "Imagem não pode ser carregada: ${it.message}"
            }
    }

    fun setUpImagemEmpresa(cnpj: String) {
        val file = File(app.cacheDir, "temp.png")
        getFileReference(cnpj)
            .getFile(file)
            .addOnSuccessListener {
                setImagemEmpresa(file.toUri())
            }
            .addOnFailureListener {
                _msg.value = "Imagem não pode ser carregada: ${it.message}"
            }
    }

    private fun getFileReference(cnpj: String): StorageReference {
        val firebaseStorage = FirebaseStorage.getInstance()
        val storageReference = firebaseStorage.reference
        val fileReference = storageReference.child("Empresa/imagens/$cnpj.png")
        return fileReference
    }

    fun setImagemEmpresa(photo: Uri) {
        _imagemEmpresa.value = photo
    }

    fun selectEmpresa(cnpj: String) {
        empresaDao.read(cnpj)
            .addOnSuccessListener {
                val empresa = it.toObject(Empresa::class.java)
                if (empresa != null)
                    _empresa.value = empresa!!
                else
                    _msg.value = "O carro foi encontrado."
            }
            .addOnFailureListener {
                _msg.value = "${it.message}"
            }

    }

}