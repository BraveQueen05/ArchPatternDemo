package com.flavia.mvp.presenter

import com.flavia.mvp.model.IModel
import com.flavia.mvp.model.Model

/*
    Created by: Flavia Figueroa
    Email: flavia.figueroa@globant.com
*/

interface ICategoriesPresenter{
    var iView: IView?

    fun onCreate()
    fun onDestroy()
    fun pullToRefreshCalled()
}

class CategoriesPresenter(override var iView: IView?): ICategoriesPresenter {

    private val model: IModel = Model()

    override fun onCreate() {
        this.iView?.initView()
        this.iView?.initAdapters()
        this.getList()
    }

    override fun onDestroy() {
        this.iView = null
    }

    override fun pullToRefreshCalled(){
        this.getList()
        this.iView?.clearSearchView()
    }

    private fun getList() {
        this.iView?.isRefreshing(true)
        this.model.getList {
            this.iView?.reloadData(it)
        }
        this.iView?.isRefreshing(false)
    }
}