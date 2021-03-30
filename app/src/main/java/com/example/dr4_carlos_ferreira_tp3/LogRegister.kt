package com.example.dr4_carlos_ferreira_tp3

import android.content.Context

class LogRegister (
    private val context: Context
) {
    private val nomeArquivoLog = "logRegister.log"

    fun escreverLog(msg: String){
        val fileOutputStream =
            context
                .openFileOutput(
                    nomeArquivoLog,
                    Context.MODE_APPEND)
        fileOutputStream.write(msg.toByteArray())
        fileOutputStream.write("\n".toByteArray())
        fileOutputStream.close()
    }

    fun lerLog() =
        context.openFileInput(nomeArquivoLog)
            .bufferedReader()
            .readText()


    companion object {
        private var instance: LogRegister? = null
        fun getInstance(context: Context): LogRegister {
            if (instance == null)
                instance = LogRegister(context)
            return instance as LogRegister
        }
    }
}