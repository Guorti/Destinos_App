package com.example.taller_1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Button
import android.widget.Spinner
import com.example.taller_1.model.DestinoTuristico


class MainActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val spinnerSpTodos = findViewById<Spinner>(R.id.spinnerTodos)
        val btnExpDest = findViewById<Button>(R.id.buttonExpDest)
        val btnFav = findViewById<Button>(R.id.buttonFav)
        val btnRecom = findViewById<Button>(R.id.buttonRecom)
        val intentExpDest = Intent(this, ExplorarDestinosActivity::class.java)
        val intentFavoritos = Intent(this, FavoritosActivity::class.java)
        val bundle = Bundle()

        btnExpDest.setOnClickListener{
            spinnerSpTodos.onItemSelectedListener = this
            bundle.putString("Categoria", spinnerSpTodos.selectedItem.toString())
            intentExpDest.putExtras(bundle)
            startActivity(intentExpDest)
        }
        btnFav.setOnClickListener(){ startActivity(intentFavoritos) }

        btnRecom.setOnClickListener(){ seleccionRecomendacion() }
    }


    companion object {
        val favoritosList = arrayListOf<DestinoTuristico>()
    }

    override fun onItemSelected(parent: AdapterView<*>, view: View?, pos: Int, id: Long) {}

    override fun onNothingSelected(parent: AdapterView<*>) {}

    fun seleccionRecomendacion(){
        val bundleIndice = Bundle()
        val indicesRecomendados = arrayListOf<Int>()
        val mapaCategorias = mutableMapOf<String, Int>()
        var mayorKey : String? = null
        var mayorValue : Int = 0
        val intentRecomendacion = Intent(this, RecomendacionDestinoActivity::class.java)


        if(favoritosList.isEmpty()){
            bundleIndice.putInt("Indice", -1)
            intentRecomendacion.putExtras(bundleIndice)
            startActivity(intentRecomendacion)
        } else {

            for (i in 0 until favoritosList.size) {

                if (mapaCategorias.containsKey(favoritosList[i].getCategoriaDestino())) {
                    // If the key exists, increment its value by 1
                    mapaCategorias[favoritosList[i].getCategoriaDestino().toString()] =
                        mapaCategorias.getValue(
                            favoritosList[i].getCategoriaDestino().toString()
                        ) + 1
                } else {
                    // If the key doesn't exist, set its value to 0 and then increment by 1
                    mapaCategorias[favoritosList[i].getCategoriaDestino().toString()] = 1
                }

            }
            for ((key, value) in mapaCategorias) {
                println("Key: $key, Value: $value")
                if (mayorKey == null || value > mayorValue) {
                    mayorValue = value
                    mayorKey = key
                }
            }
            println(mayorKey)

            for (i in 0 until favoritosList.size) {
                if (mayorKey == favoritosList[i].getCategoriaDestino()) {
                    indicesRecomendados.add(i)
                }
            }

            bundleIndice.putInt("Indice", indicesRecomendados.random())
            intentRecomendacion.putExtras(bundleIndice)
            startActivity(intentRecomendacion)

        }
    }

}