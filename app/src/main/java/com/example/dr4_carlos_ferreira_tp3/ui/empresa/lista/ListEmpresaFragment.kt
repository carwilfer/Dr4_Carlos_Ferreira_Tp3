package com.example.dr4_carlos_ferreira_tp3.ui.empresa.lista

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dr4_carlos_ferreira_tp3.*
import com.example.dr4_carlos_ferreira_tp3.adapter.RecyclerListEmpresa
import com.example.dr4_carlos_ferreira_tp3.database.AppUtil
import com.example.dr4_carlos_ferreira_tp3.database.EmpresaFirestoreDao
import kotlinx.android.synthetic.main.list_empresa_fragment.*

class ListEmpresaFragment : Fragment() {

    private lateinit var viewModelListEmpresa: ListEmpresaViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //verificarUsuario()

        val view = inflater.inflate(R.layout.list_empresa_fragment, container, false)
        LogRegister.getInstance(
            requireContext()
        ).escreverLog("Acessou: ListEmpresaFragment")

        val listEmpresaViewModelFactory =
            ListEmpresaViewModelFactory(
                EmpresaFirestoreDao()
            )
        viewModelListEmpresa = ViewModelProvider(this, listEmpresaViewModelFactory)
            .get(ListEmpresaViewModel::class.java)

        viewModelListEmpresa.empresa.observe(viewLifecycleOwner, Observer {
            recyclerViewListEmpresa.adapter =
                RecyclerListEmpresa(
                    it
                ) {
                    AppUtil.empresaSelecionado = null
                    findNavController().navigate(R.id.formEmpresaFragment)
                }
            recyclerViewListEmpresa.layoutManager = LinearLayoutManager(requireContext())
        })
        viewModelListEmpresa.atualizarListaEmpresa()

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        fabFormEmpresa.setOnClickListener {
            AppUtil.empresaSelecionado = null
            findNavController().navigate(R.id.formEmpresaFragment)
        }
    }

    /*fun verificarUsuario(){
        if (EmpresaFirestoreDao.firebaseAuth.currentUser == null)
            findNavController().popBackStack()
    }*/

}