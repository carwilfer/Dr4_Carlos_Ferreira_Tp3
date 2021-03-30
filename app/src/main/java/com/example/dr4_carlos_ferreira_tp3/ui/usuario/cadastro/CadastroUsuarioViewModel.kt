package com.example.dr4_carlos_ferreira_tp3.ui.usuario.cadastro

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dr4_carlos_ferreira_tp3.database.UsuarioFirebaseDao

class CadastroUsuarioViewModel : ViewModel() {
    private var imagemPerfil: Bitmap? = null

    private val _status = MutableLiveData<Boolean>()
    val status: LiveData<Boolean> = _status

    private val _msg = MutableLiveData<String>()
    val msg: LiveData<String> = _msg

    fun salvarCadastro(email: String, senha: String,
                       nome: String) {
        UsuarioFirebaseDao.cadastrarCredenciais(
            email,
            senha
        ) // createUserWithEmailAndPassword
            .addOnSuccessListener {
                val uid = it.user!!.uid         // firebaseUser.uid
                UsuarioFirebaseDao.cadastrarPerfil(
                    uid,
                    nome,
                    email
                )   // collection("usuarios").document(uid)
                    .addOnSuccessListener {
                        UsuarioFirebaseDao.cadastrarImagem(
                            uid,
                            imagemPerfil!!
                        )
                            .addOnSuccessListener {
                                _status.value = true
                                _msg.value = "Usuário cadastrado com sucesso!"
                            }
                            .addOnFailureListener {
                                _msg.value = "${it.message}"
                            }
                    }
                    .addOnFailureListener {
                        _msg.value = "${it.message}"
                    }
            }
            .addOnFailureListener {
                _msg.value = "${it.message}"
            }
    }

    fun alterarImagemPerfil(imagem: Bitmap){
        imagemPerfil = imagem
    }
}