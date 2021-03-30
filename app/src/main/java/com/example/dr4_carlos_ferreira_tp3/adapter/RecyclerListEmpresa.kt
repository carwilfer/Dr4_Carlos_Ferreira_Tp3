package com.example.dr4_carlos_ferreira_tp3.adapter

import android.graphics.BitmapFactory
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.dr4_carlos_ferreira_tp3.R
import com.example.dr4_carlos_ferreira_tp3.model.EmpresaLite
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.recycler_empresa_list.view.*

class RecyclerListEmpresa (
    private val empresa: List<EmpresaLite>,
    val actionClick: (EmpresaLite) -> Unit
) : RecyclerView.Adapter<RecyclerListEmpresa.EmpresaViewHolder>() {

    private val storageReference = FirebaseStorage.getInstance().reference

    class EmpresaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textRazao: TextView = itemView.textViewEmpresaRazao
        val textBairro: TextView = itemView.textViewEmpresaBairro
        val textCnpj: TextView = itemView.textViewEmpresaCnpj
        val imageEmpresa: ImageView = itemView.imageViewEmpresaFoto
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmpresaViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(
                R.layout.recycler_empresa_list,
                parent,
                false
            )
        return EmpresaViewHolder(
            view
        )
    }

    override fun onBindViewHolder(holder: EmpresaViewHolder, position: Int) {
        val empresa = empresa[position]
        holder.textRazao.text = empresa.razao
        holder.textBairro.text = empresa.bairro
        holder.textCnpj.text = empresa.cnpj

        val fileReference = storageReference.child("Empresa/imagens/${empresa.cnpj}.png")
        fileReference
            .getBytes(1024*1024)
            .addOnSuccessListener {         // it: ByteArray
                val bitmap = BitmapFactory.decodeByteArray(it, 0, it.size)
                holder.imageEmpresa.setImageBitmap(bitmap)
            }
            .addOnFailureListener {
                Log.i("RecyclerImageEmpresa", "${it.message}")
            }

        holder.itemView.setOnClickListener {
            actionClick(empresa)
        }

    }

    override fun getItemCount(): Int = empresa.size
}