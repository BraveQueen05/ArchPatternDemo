package com.flavia.mvp.view

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.flavia.mvp.*
import com.flavia.mvp.databinding.ActivityMainBinding
import com.flavia.mvp.presenter.CategoriesPresenter
import com.flavia.mvp.presenter.ICategoriesPresenter
import com.flavia.mvp.presenter.IView

class MainActivity : AppCompatActivity(), IView {

    private lateinit var binding: ActivityMainBinding

    private val presenter: ICategoriesPresenter by lazy {
        CategoriesPresenter(this)
    }

    private val adapterCategories: ICategoriesListAdapter by lazy {
        CategoriesListAdapter().apply {
            this.categoryClick = { category ->
                Toast.makeText(this@MainActivity, "Su id es ${category.id} y su nombre es ${category.name}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private val adapterCategoriesSearch: ICategoriesSearchAdapter by lazy {
        CategoriesSearchAdapter(this.binding.searchView)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        this.presenter.onCreate()
    }

    override fun initView(){
        this.binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(this.binding.root)

        this.binding.refresh.setOnRefreshListener {
            this.presenter.pullToRefreshCalled()
        }
    }

    override fun initAdapters(){
        this.binding.rvCategories.adapter = this.adapterCategories.getAdapter

        this.adapterCategoriesSearch.apply {
            this.searchAction()
            this.dataSourceFiltered = {
                this@MainActivity.adapterCategories.dataSource = it
                this@MainActivity.adapterCategories.dataHasChanged()
            }
        }
    }

    override fun isRefreshing(isRefreshing: Boolean){
        this.binding.refresh.isRefreshing = isRefreshing
    }

    override fun clearSearchView(){
        this.binding.searchView.apply {
            this.setQuery("", false)
            this.clearFocus()
        }
    }

    override fun reloadData(list: List<Any>){
        this.adapterCategories.dataSource = list
        this.adapterCategoriesSearch.dataSource = list as? List<Categories> ?: emptyList()
        this.adapterCategories.dataHasChanged()
    }

    override fun onDestroy() {
        super.onDestroy()

        this.presenter.onDestroy()
    }
}