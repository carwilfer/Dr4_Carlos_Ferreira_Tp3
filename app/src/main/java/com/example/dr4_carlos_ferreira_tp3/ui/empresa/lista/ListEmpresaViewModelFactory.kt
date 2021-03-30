package com.example.dr4_carlos_ferreira_tp3.ui.empresa.lista

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.dr4_carlos_ferreira_tp3.database.EmpresaDao

class ListEmpresaViewModelFactory (
    private val empresaDao: EmpresaDao
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ListEmpresaViewModel::class.java))
            return ListEmpresaViewModel(
                empresaDao
            ) as T
        throw IllegalArgumentException("Classe ViewModel desconhecida.")
    }
}