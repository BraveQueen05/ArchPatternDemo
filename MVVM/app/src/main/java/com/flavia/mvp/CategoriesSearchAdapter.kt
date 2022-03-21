package com.flavia.mvp

import androidx.appcompat.widget.SearchView
import java.util.*

/*
    Created by: Flavia Figueroa
*/

typealias Action = (list: List<Any>) -> Unit

interface ICategoriesSearchAdapter {
    var dataSource: List<Categories>
    var dataSourceFiltered: Action?
    fun searchAction()
}

class CategoriesSearchAdapter(private val searchView: SearchView): ICategoriesSearchAdapter {

    override var dataSource: List<Categories> = emptyList()

    override var dataSourceFiltered: Action ?= null

    override fun searchAction(){
        this.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean = false

            override fun onQueryTextChange(newText: String?): Boolean {
                filter(newText ?: "")
                return false
            }
        })
    }

    private fun filter(txtToSearch: String) {
        if (txtToSearch.isEmpty()) {
            this.dataSource.apply {
                this@CategoriesSearchAdapter.dataSourceFiltered?.let {
                    it(this)
                }
            }
        }
        else {
            this.dataSource.apply {
                val collecion = this.filter { i ->
                    i.name.lowercase(Locale.getDefault()).contains(txtToSearch.lowercase(Locale.getDefault()))
                }

                this@CategoriesSearchAdapter.dataSourceFiltered?.let {
                    it(if(collecion.isEmpty()) listOf("No se encontraron resultados para: \n $txtToSearch") else collecion)
                }
            }
        }
    }
}