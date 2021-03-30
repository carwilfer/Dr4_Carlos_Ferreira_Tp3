package com.example.dr4_carlos_ferreira_tp3.ui.usuario.login

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.dr4_carlos_ferreira_tp3.R
import kotlinx.android.synthetic.main.login_usuario_fragment.*

class LoginUsuarioFragment : Fragment() {

    companion object {
        fun newInstance() =
            LoginUsuarioFragment()
    }

    private lateinit var viewModelLoginUsuario: LoginUsuarioViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.login_usuario_fragment, container, false)
        viewModelLoginUsuario = ViewModelProvider(this).get(LoginUsuarioViewModel::class.java)

        viewModelLoginUsuario.status.observe(viewLifecycleOwner, Observer {
            if (it) {
                findNavController().navigate(R.id.listEmpresaFragment)
            }
        })
        viewModelLoginUsuario.msg.observe(viewLifecycleOwner, Observer {
            if (!it.isNullOrBlank())
                Toast
                    .makeText(
                        requireContext(),
                        it,
                        Toast.LENGTH_LONG
                    ).show()
        })

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnFormLoginAcessar.setOnClickListener {
            val email = editTextFormLoginEmail.text.toString()
            val senha = editTextFormLoginSenha.text.toString()
            viewModelLoginUsuario.verificarCredenciais(email, senha)
        }

        btnFormLoginCadastro.setOnClickListener {
            findNavController().navigate(R.id.cadastroUsuarioFragment)
        }
    }

}