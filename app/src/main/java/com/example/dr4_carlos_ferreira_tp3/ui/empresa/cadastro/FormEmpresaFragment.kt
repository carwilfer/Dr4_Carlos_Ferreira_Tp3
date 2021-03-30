package com.example.dr4_carlos_ferreira_tp3.ui.empresa.cadastro

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.dr4_carlos_ferreira_tp3.LogRegister
import com.example.dr4_carlos_ferreira_tp3.R
import com.example.dr4_carlos_ferreira_tp3.database.AppUtil
import com.example.dr4_carlos_ferreira_tp3.database.EmpresaFirestoreDao
import com.example.dr4_carlos_ferreira_tp3.model.Empresa
import kotlinx.android.synthetic.main.form_empresa_fragment.*

class FormEmpresaFragment : Fragment() {

    private lateinit var viewModelFormEmpresa: FormEmpresaViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.form_empresa_fragment, container, false)

        LogRegister.getInstance(
            requireContext()
        ).escreverLog("Acessou: FormEmpresaFragment")

        val application = requireActivity().application
        val formEmpresaViewModelFactory =
            FormEmpresaViewModelFactory(
                EmpresaFirestoreDao(),
                application
            )

        viewModelFormEmpresa = ViewModelProvider(
            this,
            formEmpresaViewModelFactory
        ).get(FormEmpresaViewModel::class.java)
            .apply {
                setUpMsgObserver(this)
                setUpStatusObserver(this)
                setUpImagemCarroObserver(this)
            }
        viewModelFormEmpresa.empresa.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                preencherFormulario(it)
            }
        })
        return view
    }

    private fun setUpStatusObserver(formEmpresaViewModel: FormEmpresaViewModel) {
        formEmpresaViewModel.status.observe(viewLifecycleOwner, Observer { status ->
            if (status)
                findNavController().popBackStack()
        })
    }

    private fun setUpMsgObserver(formEmpresaViewModel: FormEmpresaViewModel) {
        formEmpresaViewModel.msg.observe(viewLifecycleOwner, Observer { msg ->
            if (!msg.isNullOrBlank()) {
                Toast.makeText(requireContext(), msg, Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun setUpImagemCarroObserver(formEmpresaViewModel: FormEmpresaViewModel) {
        formEmpresaViewModel.imagemEmpresa.observe(viewLifecycleOwner, Observer { imagemEmpresa ->
            if (imagemEmpresa != null) {
                imageViewEmpresaFoto.setImageURI(imagemEmpresa)
            }
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (AppUtil.empresaSelecionado != null) {
            viewModelFormEmpresa.selectEmpresa(AppUtil.empresaSelecionado!!.cnpj!!)
        }

        fabFormEmpresaCadastrar.setOnClickListener {

            LogRegister.getInstance(
                requireContext()
            ).escreverLog("Cadastrar Carro")

            val cnpj = editTextFormEmpresaCnpj.text.toString()
            val razao = editTextFormEmpresaRazao.text.toString()
            val bairro = editTextFormEmpresaBairro.text.toString()

            viewModelFormEmpresa.salvarEmpresa(cnpj, razao, bairro)
        }

        imageViewEmpresaFoto.setOnClickListener {
            selecionarImagem()
            //tirarFoto()
        }
    }

    private fun preencherFormulario(empresa: Empresa) {
        editTextFormEmpresaCnpj.setText(empresa.cnpj)
        editTextFormEmpresaRazao.setText(empresa.razao)
        editTextFormEmpresaBairro.setText(empresa.bairro)
        viewModelFormEmpresa.setUpImagemEmpresa(empresa.cnpj!!)
    }

    private fun limparFormulario() {
        editTextFormEmpresaCnpj.setText("")
        editTextFormEmpresaRazao.setText("")
        editTextFormEmpresaBairro.setText("")
    }

    private fun selecionarImagem() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        intent.addCategory(Intent.CATEGORY_OPENABLE)
        startActivityForResult(intent, 2)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 2) {
                val photo: Uri = data!!.data!!
                imageViewEmpresaFoto.setImageURI(photo)
                viewModelFormEmpresa.setImagemEmpresa(photo)
            }
        }
    }
}


