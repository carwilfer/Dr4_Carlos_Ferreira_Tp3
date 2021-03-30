package com.example.dr4_carlos_ferreira_tp3.ui.fiscal.show

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.dr4_carlos_ferreira_tp3.R
import com.example.dr4_carlos_ferreira_tp3.database.AppUtil
import com.example.dr4_carlos_ferreira_tp3.model.Empresa
import kotlinx.android.synthetic.main.show_fiscal_fragment.*

class ShowFiscalFragment : Fragment() {


    private lateinit var viewModelShowFiscal: ShowFiscalViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewModelShowFiscal = ViewModelProvider(this).get(ShowFiscalViewModel::class.java)

        return inflater.inflate(R.layout.show_fiscal_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val fiscal = AppUtil.fiscalSelecionado!!

        textViewShowFiscalNome.text = fiscal.nome
        fiscal.empresa!!.get()
            .addOnSuccessListener {
                val empresa = it.toObject(Empresa::class.java)
                textViewShowMotoristaEmpresaCnpj.text = empresa!!.cnpj
            }
            .addOnFailureListener {
                Log.i("ShowFiscalFragment", "${it.message}")
            }
    }

}