package com.example.dr4_carlos_ferreira_tp3.ui.empresa.cadastro

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.dr4_carlos_ferreira_tp3.database.EmpresaDao

class FormEmpresaViewModelFactory (
    val empresaDao: EmpresaDao,
    val application: Application
) : ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FormEmpresaViewModel::class.java)){
            return FormEmpresaViewModel(
                empresaDao,
                application
            ) as T
        }
        throw IllegalArgumentException("Classe ViewModel desconhecida.")
    }
}