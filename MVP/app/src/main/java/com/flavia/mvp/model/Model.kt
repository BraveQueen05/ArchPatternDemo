package com.flavia.mvp.model

import android.os.Handler
import android.os.Looper
import com.flavia.mvp.Categories

/*
    Created by: Flavia Figueroa
*/

interface IModel{
    fun getList(success: (List<Categories>) -> Unit)
}

class Model: IModel {

    override fun getList(success: (List<Categories>) -> Unit) {
        Handler(Looper.getMainLooper()).postDelayed( {
            val list = mutableListOf(
                Categories(1, "Entretenimiento"),
                Categories(2, "Recreación"),
                Categories(3, "Educacion"),
                Categories(4, "Social"),
                Categories(5, "Diy"),
                Categories(6, "Caridad")
            )
            success(list)
        }, 3000)
    }
}