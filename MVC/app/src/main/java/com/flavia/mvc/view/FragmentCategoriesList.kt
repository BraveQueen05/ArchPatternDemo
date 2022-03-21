package com.flavia.mvc.view

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.flavia.mvc.*
import com.flavia.mvc.databinding.FragmentListBinding

interface IFragmentCategoriesListListener {
    fun pullToRefreshCalled()
    fun onItemClicked(category: Categories)
}

interface IFragmentCategoriesList {
    fun isRefreshing(isRefreshing: Boolean)
    fun clearSearchView()
    fun reloadData(list: List<Any>)
}

class FragmentCategoriesList : Fragment(), IFragmentCategoriesList {

    private lateinit var binding: FragmentListBinding

    private var listenerCategories: IFragmentCategoriesListListener? = null

    private val adapterCategories: ICategoriesListAdapter by lazy {
        CategoriesListAdapter().apply {
            this.categoryClick = {
                this@FragmentCategoriesList.listenerCategories?.onItemClicked(it)
            }
        }
    }

    private val adapterCategoriesSearch: ICategoriesSearchAdapter by lazy {
        CategoriesSearchAdapter(this.binding.searchView)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if(context is IFragmentCategoriesListListener) this.listenerCategories = context else throw Exception("$context debe implementar IFragmentCategoriesListListener")
    }

    override fun onDetach() {
        super.onDetach()
        this.listenerCategories = null
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        this.binding = FragmentListBinding.inflate(inflater)
        return this.binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        refreshLayout()
        this.adapterCategoriesSearch.apply {
            this.searchAction()
            this.dataSourceFiltered = {
                this@FragmentCategoriesList.adapterCategories.dataSource = it
                this@FragmentCategoriesList.adapterCategories.dataHasChanged()
            }
        }
    }

    private fun initRecyclerView(){
        this.binding.rvCategories.adapter = this.adapterCategories.getAdapter
    }

    private fun refreshLayout(){
        this.binding.refresh.setOnRefreshListener {
            this.listenerCategories?.pullToRefreshCalled()
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
}