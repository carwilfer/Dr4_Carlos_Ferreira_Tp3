package com.example.dr4_carlos_ferreira_tp3.database

import android.graphics.Bitmap
import com.example.dr4_carlos_ferreira_tp3.model.Fiscal
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FileDownloadTask
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import java.io.ByteArrayOutputStream
import java.io.File

object UsuarioFirebaseDao {
    val firebaseAuth = FirebaseAuth.getInstance()
    private val collection = FirebaseFirestore
        .getInstance()
        .collection("usuarios")

    private val storageReference = FirebaseStorage
        .getInstance()
        .reference
        .child("Usuarios")

    fun cadastrarCredenciais(email: String, senha: String): Task<AuthResult> {
        return firebaseAuth
            .createUserWithEmailAndPassword(email, senha)
    }

    fun cadastrarPerfil(uid: String, nome: String, telefone: String): Task<Void> {
        return collection
            .document(uid)
            .set(
                Fiscal(
                    nome,
                    telefone
                )
            )
    }

    fun cadastrarImagem(uid: String, imagem: Bitmap): UploadTask {
        val outputStream = ByteArrayOutputStream()
        imagem.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
        return storageReference.child("${uid}.jpeg")
            .putBytes(outputStream.toByteArray())
    }

    fun verificarCredenciais(email: String, senha: String): Task<AuthResult> {
        return firebaseAuth
            .signInWithEmailAndPassword(email, senha)
    }

    fun encerrarSessao() {
        firebaseAuth.signOut()
    }

    fun consultarUsuario(): Task<DocumentSnapshot> {
        val firebaseUser = firebaseAuth.currentUser
        return collection                   // usuarios
            .document(firebaseUser!!.uid)
            .get()
    }

    fun consultarImagem(uid: String, file: File): FileDownloadTask {
        return storageReference
            .child("${uid}.jpeg")
            .getFile(file)
    }
}