package com.flavia.mvp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.flavia.mvp.Categories
import com.flavia.mvp.model.IModel
import com.flavia.mvp.model.Model

/*
    Created by: Flavia Figueroa
    Email: flavia.figueroa@globant.com
*/

typealias callback = (List<Categories>) -> Unit
class CategoriesViewModel: ViewModel() {
    private val model: IModel = Model()

    var listCallback: callback ?= null

    private val mList = MutableLiveData<List<Categories>>()
    val list: LiveData<List<Categories>> = this.mList

    private val mLoading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = this.mLoading

    fun getList() {
        this.mLoading.value = true
        this.model.getList {
            this.mList.value = it
            this.listCallback?.let { it1 -> it1(it) }
        }
        this.mLoading.value = false
    }
}