package com.example.dr4_carlos_ferreira_tp3.ui.fiscal.cadastro

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.dr4_carlos_ferreira_tp3.R
import kotlinx.android.synthetic.main.form_fiscal_fragment.*

class FormFiscalFragment : Fragment() {

    private lateinit var viewModelFormFiscal: FormFiscalViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModelFormFiscal = ViewModelProvider(this).get(FormFiscalViewModel::class.java)

        viewModelFormFiscal.let {
            it.status.observe(viewLifecycleOwner, Observer { status ->
                if (status)
                    findNavController().popBackStack()
            })
            it.msg.observe(viewLifecycleOwner, Observer { msg ->
                if (!msg.isNullOrBlank())
                    Toast.makeText(
                        requireContext(), msg, Toast.LENGTH_LONG
                    ).show()
            })
            it.empresa.observe(viewLifecycleOwner, Observer { list ->
                val adapter = ArrayAdapter(
                    requireContext(),
                    android.R.layout.simple_spinner_item,
                    list
                )
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinnerFormFiscalEmpresa.adapter = adapter

                spinnerFormFiscalEmpresa.onItemSelectedListener =
                    object : AdapterView.OnItemSelectedListener {
                        override fun onNothingSelected(parent: AdapterView<*>?) {}

                        override fun onItemSelected(
                            parent: AdapterView<*>?,
                            view: View?,
                            position: Int,
                            id: Long
                        ) {
                            it.definirCnpjSelecionado(list[position])
                        }

                    }
            })
        }

        return inflater.inflate(R.layout.form_fiscal_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fabFormFiscalSalvar.setOnClickListener {
            val nome = editTextFormFiscalNome.text.toString()
            val cpf = editTextFormFiscalCpf.text.toString()
            val email = editTextFormFiscalEmail.text.toString()
            viewModelFormFiscal.salvarFiscal(nome, cpf, email)
        }
    }
}