package com.flavia.mvc.model

import android.os.Handler
import android.os.Looper
import com.flavia.mvc.Categories

/*
    Created by: Flavia Figueroa
*/

class Model {

    fun getList(callback: (list: List<Categories>) -> Unit) {
        Handler(Looper.getMainLooper()).postDelayed( {
            val list = mutableListOf(
                Categories(1, "Entretenimiento"),
                Categories(2, "Recreación"),
                Categories(3, "Educacion"),
                Categories(4, "Social"),
                Categories(5, "Diy"),
                Categories(6, "Caridad")
            )
            callback(list)
        }, 3000)
    }
}