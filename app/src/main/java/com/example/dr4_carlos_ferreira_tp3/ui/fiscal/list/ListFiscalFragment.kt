package com.example.dr4_carlos_ferreira_tp3.ui.fiscal.list

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.dr4_carlos_ferreira_tp3.R
import com.example.dr4_carlos_ferreira_tp3.database.AppUtil
import kotlinx.android.synthetic.main.list_fiscal_fragment.*

class ListFiscalFragment : Fragment() {

    private lateinit var viewModelListFiscal: ListFiscalViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModelListFiscal = ViewModelProvider(this).get(ListFiscalViewModel::class.java)

        viewModelListFiscal.fiscal.observe(viewLifecycleOwner, Observer {
            if (!it.isNullOrEmpty())
                listViewFiscal.adapter =
                    ArrayAdapter(
                        requireContext(),
                        android.R.layout.simple_list_item_1,
                        it
                    )
            listViewFiscal.setOnItemClickListener { parent, view, position, id ->
                val fiscal = it[position]
                AppUtil.fiscalSelecionado = fiscal
                findNavController().navigate(R.id.showFiscalFragment)
            }
        })

        return inflater.inflate(R.layout.list_fiscal_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fabListFiscalForm.setOnClickListener {
            findNavController().navigate(R.id.formFiscalFragment)
        }
    }
}