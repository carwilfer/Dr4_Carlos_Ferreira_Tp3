package com.example.dr4_carlos_ferreira_tp3.ui.fiscal.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dr4_carlos_ferreira_tp3.database.FiscalFirestoreDao
import com.example.dr4_carlos_ferreira_tp3.model.Fiscal

class ListFiscalViewModel : ViewModel() {
    private val _fiscal = MutableLiveData<List<Fiscal>>()
    val fiscal: LiveData<List<Fiscal>> = _fiscal


    init {
        FiscalFirestoreDao()
            .all()
            .addSnapshotListener { snapshot, error ->
                if (error == null && snapshot != null && !snapshot.isEmpty)
                    _fiscal.value = snapshot.toObjects(Fiscal::class.java)
            }
    }
}