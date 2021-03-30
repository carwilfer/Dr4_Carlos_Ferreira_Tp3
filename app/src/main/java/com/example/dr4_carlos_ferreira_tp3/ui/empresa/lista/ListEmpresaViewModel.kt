package com.example.dr4_carlos_ferreira_tp3.ui.empresa.lista

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dr4_carlos_ferreira_tp3.database.EmpresaDao
import com.example.dr4_carlos_ferreira_tp3.model.EmpresaLite

class ListEmpresaViewModel (
    private val empresaDao: EmpresaDao
) : ViewModel() {
    private val _empresa = MutableLiveData<List<EmpresaLite>>()
    val empresa: LiveData<List<EmpresaLite>> = _empresa

    fun atualizarListaEmpresa() {

        empresaDao.all()
            .addSnapshotListener { snapshot, error ->
                if (error != null)
                    Log.i("ListEmpresaViewModel", "${error.message}")
                else
                    if (snapshot != null && !snapshot.isEmpty){
                        val empresa =
                            snapshot.toObjects(EmpresaLite::class.java)
                        _empresa.value = empresa
                    }
            }
     }
}