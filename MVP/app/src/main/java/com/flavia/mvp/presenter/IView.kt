package com.flavia.mvp.presenter

/*
    Created by: Flavia Figueroa
    Email: flavia.figueroa@globant.com
*/

interface IView {
    fun initView()
    fun initAdapters()
    fun isRefreshing(isRefreshing: Boolean)
    fun clearSearchView()
    fun reloadData(list: List<Any>)
}