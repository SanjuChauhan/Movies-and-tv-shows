package com.my.favourite.movies.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.my.favourite.movies.R
import com.my.favourite.movies.databinding.ItemDataBinding
import com.my.favourite.movies.model.MovieTitleData
import java.util.*

class MovieDataRvAdapter(private val context: Context) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var dataList: MutableList<MovieTitleData> = ArrayList()
    private var mOnItemClickListener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemBinding = DataBindingUtil.inflate<ItemDataBinding>(
            LayoutInflater.from(context),
            R.layout.item_data, parent, false
        )
        return PeopleViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val viewHolder = holder as PeopleViewHolder
        viewHolder.setData(dataList[position])
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    private fun add(data: MovieTitleData) {
        dataList.add(data)
        notifyItemInserted(dataList.size - 1)
    }

    fun addAtPosition(position: Int, data: MovieTitleData) {
        dataList.add(position, data)
        notifyItemInserted(position)
    }

    fun addAll(dataList: List<MovieTitleData>) {
        dataList.forEach { data ->
            add(data)
        }
    }

    private fun remove(data: MovieTitleData) {
        val position = dataList.indexOf(data)
        if (position > -1) {
            dataList.removeAt(position)
            notifyItemRemoved(position)
        }
    }

    fun clear() {
        dataList.clear()
        notifyDataSetChanged()
    }

    fun getItem(position: Int): MovieTitleData {
        return dataList[position]
    }

    fun setItemAtPosition(position: Int, data: MovieTitleData) {
        dataList[position] = data
        notifyItemChanged(position)
    }

    fun removeItem(position: Int) {
        remove(dataList[position])
    }

    fun getFactsRowsList(): List<MovieTitleData>? {
        return dataList
    }

    fun setPeopleList(dataList: MutableList<MovieTitleData>) {
        this.dataList = dataList
    }

    fun size(): Int {
        return dataList.size
    }

    internal inner class PeopleViewHolder(var itemBinding: ItemDataBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {

        fun setData(data: MovieTitleData) {

            itemBinding.data = data

            Glide.with(context)
                .load(data.poster)
                .placeholder(R.drawable.img_placeholder)
                .error(R.drawable.img_placeholder)
                .into(itemBinding.ivImage)

            itemBinding.rlRoot.setOnClickListener {
                mOnItemClickListener?.onItemClick(data, adapterPosition)
            }
        }
    }

    fun setItemClickListener(onItemClickListener: OnItemClickListener) {
        mOnItemClickListener = onItemClickListener
    }

    interface OnItemClickListener {
        fun onItemClick(data: MovieTitleData, position: Int)
    }
}
