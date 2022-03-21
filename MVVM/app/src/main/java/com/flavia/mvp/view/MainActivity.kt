package com.flavia.mvp.view

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.flavia.mvp.*
import com.flavia.mvp.databinding.ActivityMainBinding
import com.flavia.mvp.viewmodel.CategoriesViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val categoriesViewModel: CategoriesViewModel by viewModels()

    private val adapterCategories: ICategoriesListAdapter by lazy {
        CategoriesListAdapter().apply {
            this.categoryClick = {
                onItemClicked(it)
            }
        }
    }

    private val adapterCategoriesSearch: ICategoriesSearchAdapter by lazy {
        CategoriesSearchAdapter(this.binding.searchView)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initView()
        initAdapters()
        callViewModelMethods()
        observeLiveDatas()
    }

    private fun initView() {
        this.binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(this.binding.root)

        this.binding.refresh.setOnRefreshListener {
            pullToRefreshCalled()
        }
    }

    private fun callViewModelMethods(){
        this.categoriesViewModel.getList()
    }

    private fun initAdapters(){
        this.adapterCategoriesSearch.apply {
            this.searchAction()
            this.dataSourceFiltered = {
                this@MainActivity.adapterCategories.dataSource = it
                this@MainActivity.adapterCategories.dataHasChanged()
            }
        }
        this.binding.rvCategories.adapter = this.adapterCategories.getAdapter
    }

    private fun observeLiveDatas(){
        this.categoriesViewModel.list.observe(this, {
            reloadData(it)
        })

        this.categoriesViewModel.loading.observe(this, {
            isRefreshing(it)
        })

        this.categoriesViewModel.listCallback = {

        }
    }

    private fun isRefreshing(isRefreshing: Boolean){
        this.binding.refresh.isRefreshing = isRefreshing
    }

    private fun clearSearchView(){
        this.binding.searchView.apply {
            this.setQuery("", false)
            this.clearFocus()
        }
    }

    private fun reloadData(list: List<Any>){
        this.adapterCategories.dataSource = list
        this.adapterCategoriesSearch.dataSource = list as? List<Categories> ?: emptyList()
        this.adapterCategories.dataHasChanged()
    }

    private fun pullToRefreshCalled() {
        this.categoriesViewModel.getList()
        clearSearchView()
    }

    private fun onItemClicked(category: Categories) {
        Toast.makeText(this, "Su id es ${category.id} y su nombre es ${category.name}", Toast.LENGTH_SHORT).show()
    }
}