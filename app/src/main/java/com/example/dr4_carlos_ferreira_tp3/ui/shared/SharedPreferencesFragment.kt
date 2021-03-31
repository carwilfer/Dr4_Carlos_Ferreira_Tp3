package com.example.dr4_carlos_ferreira_tp3.ui.shared

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.example.dr4_carlos_ferreira_tp3.R
import kotlinx.android.synthetic.main.perfil_usuario_fragment.view.*
import kotlinx.android.synthetic.main.shared_preferences_fragment.*

class SharedPreferencesFragment : Fragment() {

    private lateinit var viewModelSharedPreferences: SharedPreferencesViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        viewModelSharedPreferences = ViewModelProvider(this).get(SharedPreferencesViewModel::class.java)

        viewModelSharedPreferences.status.observe(viewLifecycleOwner, {
            if (it)
                findNavController().popBackStack()
        })
        viewModelSharedPreferences.msg.observe(viewLifecycleOwner, {
            if (!it.isNullOrBlank())
                showToast(it)
        })

        return inflater.inflate(R.layout.shared_preferences_fragment, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //val btnGravar = this.findViewById<Button>(R.id.btnGravar)
        btnGravar.setOnClickListener {

            val txtArquivo = txtArquivo.text.toString() //this.findViewById<EditText>(R.id.txtArquivo)

            val masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)
            val lblArquivo = lblArquivoGerado.text.toString()  //this.findViewById<TextView>(R.id.lblArquivoGerado)
            lblArquivo.toString()

            val sharedPrefs= EncryptedSharedPreferences.create(
                "nome_arquivo",
                txtArquivo,
                requireContext(),
                EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            )
            val editor = sharedPrefs.edit()
            val txtTexto = txtTexto.text.toString()  //this.findViewById<EditText>(R.id.txtTexto)
            editor.putString("texto", txtTexto)
            editor.commit()
        }
    }

    private fun showToast(msg: String) {
        Toast.makeText(
            requireContext(),
            msg,
            Toast.LENGTH_LONG
        ).show()
    }

}