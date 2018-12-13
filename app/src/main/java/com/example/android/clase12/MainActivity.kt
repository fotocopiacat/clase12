package com.example.android.clase12

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.android.clase12.R.layout.activity_item
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.*
import java.io.IOException

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    btnUpdate.setOnClickListener {
        var url = "https://mindicador.cl/api/uf"
        val request = Request.Builder().url(url).build()
        val cliente = OkHttpClient()

        cliente.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call?, e: IOException) {

            }

            override fun onResponse(call: Call?, response: Response?) {
                val respuesta = response?.body()?.string()
                val gson = GsonBuilder().create()
                val indicador = gson.fromJson(respuesta,Indicador::class.java)

                val adaptador = CustomAdapter(this@MainActivity,R.layout.activity_item,indicador.serie)
                lvListar.adapter = adaptador
            }
        })

    }

    //como el customadapter se usara solo una vez, lo creamos en el main activity

    }

    class CustomAdapter(var miContexto:Context,
                        var miRecurso:Int,
                        var miLista:ArrayList<Serie>):
        ArrayAdapter<Serie>(miContexto,miRecurso,miLista){
        override fun getView(position:Int,convertView: View?, parent: ViewGroup): View {
            var v = LayoutInflater.from(miContexto).inflate(miRecurso, null)
            var valor = v.findViewById<TextView>(R.id.lblValor)
            var fecha = v.findViewById<TextView>(R.id.lblFecha)
            valor.text = miLista[position].valor.toString()
            fecha.text = miLista[position].fecha
            return v
        }
    }
}
