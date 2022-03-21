package com.flavia.mvc.controller

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.flavia.mvc.*
import com.flavia.mvc.databinding.ActivityMainBinding
import com.flavia.mvc.model.Model
import com.flavia.mvc.view.FragmentCategoriesList
import com.flavia.mvc.view.IFragmentCategoriesList
import com.flavia.mvc.view.IFragmentCategoriesListListener

class MainActivity : AppCompatActivity(), IFragmentCategoriesListListener {

    private lateinit var binding: ActivityMainBinding

    private val model = Model()

    private val fragCategoriesList: IFragmentCategoriesList by lazy {
        this.supportFragmentManager.findFragmentById(R.id.fragList) as FragmentCategoriesList
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind()
        getList()
    }

    private fun bind() {
        this.binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(this.binding.root)
    }

    override fun pullToRefreshCalled() {
        getList()
        this.fragCategoriesList.clearSearchView()
    }

    private fun getList() {
        this.fragCategoriesList.isRefreshing(true)

        this.model.getList {
            this.fragCategoriesList.reloadData(it)
        }
        this.fragCategoriesList.isRefreshing(false)
    }

    override fun onItemClicked(category: Categories) {
        Toast.makeText(this, "Su id es ${category.id} y su nombre es ${category.name}", Toast.LENGTH_SHORT).show()
    }
}