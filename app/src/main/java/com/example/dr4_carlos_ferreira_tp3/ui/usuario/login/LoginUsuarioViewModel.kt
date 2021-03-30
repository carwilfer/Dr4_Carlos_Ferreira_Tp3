package com.example.dr4_carlos_ferreira_tp3.ui.usuario.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dr4_carlos_ferreira_tp3.database.UsuarioFirebaseDao

class LoginUsuarioViewModel : ViewModel() {
    private val _status = MutableLiveData<Boolean>()
    val status: LiveData<Boolean> = _status

    private val _msg = MutableLiveData<String>()
    val msg: LiveData<String> = _msg

    fun verificarCredenciais(email: String, senha: String) {
        UsuarioFirebaseDao.verificarCredenciais(
            email,
            senha
        ) // signInWithEmailAndPassword
            .addOnSuccessListener {
                _status.value = true
            }
            .addOnFailureListener {
                _msg.value = it.message
            }
    }
}