package com.example.dr4_carlos_ferreira_tp3.ui.usuario.cadastro

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.dr4_carlos_ferreira_tp3.R
import kotlinx.android.synthetic.main.cadastro_usuario_fragment.*

class CadastroUsuarioFragment : Fragment() {

    private lateinit var viewModelCadastroUsuario: CadastroUsuarioViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.cadastro_usuario_fragment, container, false)

        viewModelCadastroUsuario.status.observe(viewLifecycleOwner, Observer {
            if (it)
                findNavController().popBackStack()
        })
        viewModelCadastroUsuario.msg.observe(viewLifecycleOwner, Observer {
            if (!it.isNullOrBlank())
                showToast(it)

        })

        viewModelCadastroUsuario = ViewModelProvider(this).get(CadastroUsuarioViewModel::class.java)

        return view
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnFormCadastroSalvar.setOnClickListener {

            val senha = editTextFormCadastroSenha.text.toString()
            val resenha = editTextFormCadastroReSenha.text.toString()

            if (senha == resenha) {
                val nome = editTextFormCadastroNome.text.toString()
                val email = editTextFormCadastroEmail.text.toString()
                viewModelCadastroUsuario.salvarCadastro(email, senha, nome)
            } else
                showToast("Senhas não conferem")
        }

        imageViewFormCadastroFoto.setOnClickListener {
            tirarFoto()
        }
    }
    private fun tirarFoto() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intent, 1)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == Activity.RESULT_OK) {
            var imageBitmap = data?.extras?.get("data") as Bitmap
            imageViewFormCadastroFoto.setImageBitmap(imageBitmap)
            viewModelCadastroUsuario.alterarImagemPerfil(imageBitmap)
        }
    }
    private fun showToast(msg: String) {
        Toast
            .makeText(
                requireContext(),
                msg,
                Toast.LENGTH_LONG
            ).show()
    }

}