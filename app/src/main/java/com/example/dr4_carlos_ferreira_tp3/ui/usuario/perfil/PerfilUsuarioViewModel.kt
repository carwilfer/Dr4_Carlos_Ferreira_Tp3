package com.example.dr4_carlos_ferreira_tp3.ui.usuario.perfil

import android.app.Application
import android.net.Uri
import androidx.core.net.toUri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.dr4_carlos_ferreira_tp3.database.UsuarioFirebaseDao
import com.example.dr4_carlos_ferreira_tp3.model.Usuario
import java.io.File

class PerfilUsuarioViewModel(
    application: Application
) : AndroidViewModel(application) {

    val app = application

    private val _imagemPerfil = MutableLiveData<Uri>()
    val imagemPerfil: LiveData<Uri> = _imagemPerfil

    private val _usuario = MutableLiveData<Usuario?>()
    val usuario: LiveData<Usuario?> = _usuario

    init {
        UsuarioFirebaseDao.consultarUsuario()
            .addOnSuccessListener {
                val usuario = it.toObject(Usuario::class.java)
                usuario!!.firebaseUser = UsuarioFirebaseDao.firebaseAuth.currentUser
                _usuario.value = usuario!!
                val file = File(app.cacheDir, "userTemp.jpeg")
                UsuarioFirebaseDao.consultarImagem(
                    usuario!!.uid!!,
                    file
                )
                    .addOnSuccessListener {
                        _imagemPerfil.value = file.toUri()
                    }
            }
    }

    fun encerrarSessao() {
        UsuarioFirebaseDao.encerrarSessao()
        _usuario.value = null
    }
}