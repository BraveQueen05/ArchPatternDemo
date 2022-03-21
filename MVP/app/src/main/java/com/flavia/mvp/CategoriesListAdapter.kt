package com.flavia.mvp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.flavia.mvp.databinding.EmptyItemRecyclerBinding
import com.flavia.mvp.databinding.ItemRecyclerBinding
import java.util.*

/*
    Created by: Flavia Figueroa
*/

typealias CategoryClick = (category: Categories) -> Unit

interface ICategoriesListAdapter {
    var dataSource: List<Any>
    var categoryClick: CategoryClick?
    var getAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>

    fun dataHasChanged()
}

enum class RowTypes{
    ERROR, CATEGORY
}

class CategoriesListAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>(), ICategoriesListAdapter {

    override var dataSource: List<Any> = emptyList()

    override var categoryClick: CategoryClick ?= null

    override var getAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder> = this

    override fun dataHasChanged() = this.notifyDataSetChanged()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType){
            RowTypes.ERROR.ordinal      -> ErrorViewHolder(EmptyItemRecyclerBinding.inflate(LayoutInflater.from(parent.context), parent, false))
            RowTypes.CATEGORY.ordinal   -> CategoriesViewHolder(ItemRecyclerBinding.inflate(LayoutInflater.from(parent.context), parent, false))
            else                        -> CategoriesViewHolder(ItemRecyclerBinding.inflate(LayoutInflater.from(parent.context), parent, false))
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(getItemViewType(position)){
            RowTypes.ERROR.ordinal      -> (holder as ErrorViewHolder).bind(this.dataSource[position] as String)
            RowTypes.CATEGORY.ordinal   -> (holder as CategoriesViewHolder).bind(this.dataSource[position] as Categories)
            else                        -> (holder as CategoriesViewHolder).bind(this.dataSource[position] as Categories)
        }
    }

    override fun getItemCount(): Int = this.dataSource.size

    override fun getItemViewType(position: Int): Int =
        if (this.dataSource[position] is String) RowTypes.ERROR.ordinal else RowTypes.CATEGORY.ordinal

    inner class CategoriesViewHolder(private val binding: ItemRecyclerBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(category: Categories){
            binding.apply {
                this.tvName.text = category.name
                this.btnCategory.setOnClickListener {
                    this@CategoriesListAdapter.categoryClick?.let {
                        it(category)
                    }
                }
            }
        }
    }

    inner class ErrorViewHolder(private val binding: EmptyItemRecyclerBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(message: String){
            binding.apply {
                this.tvMessage.text = message
            }
        }
    }
}