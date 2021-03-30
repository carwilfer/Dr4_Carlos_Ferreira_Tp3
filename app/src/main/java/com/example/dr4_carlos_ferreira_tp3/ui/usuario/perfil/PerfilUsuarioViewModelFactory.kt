package com.example.dr4_carlos_ferreira_tp3.ui.usuario.perfil

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class PerfilUsuarioViewModelFactory (
    val application: Application
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PerfilUsuarioViewModel::class.java)){
            return PerfilUsuarioViewModel(
                application
            ) as T
        }
        throw IllegalArgumentException("Classe ViewModel desconhecida.")
    }
}