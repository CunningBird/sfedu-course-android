package com.cunningbird.cats.view.cats.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.cunningbird.cats.R

class CatsImageAdapter(val callback: RecyclerViewClickListener) : PagingDataAdapter<String, RecyclerView.ViewHolder>(REPO_COMPARATOR) {

    interface RecyclerViewClickListener {
        fun onItemClicked(view: View, data: String?)
    }

    companion object {
        private val REPO_COMPARATOR = object : DiffUtil.ItemCallback<String>() {
            override fun areItemsTheSame(oldItem: String, newItem: String): Boolean =
                oldItem == newItem

            override fun areContentsTheSame(oldItem: String, newItem: String): Boolean =
                oldItem == newItem
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as? CatImageViewHolder)?.bind(item = getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return CatImageViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_cat_image_view, parent, false))
    }

    inner class CatImageViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private var ivCatMain: ImageView = view.findViewById(R.id.ivCatMain)

        fun bind(item: String?) {
            ivCatMain.load(item) {
                placeholder(R.drawable.cat_placeholder)
            }
            ivCatMain.setOnClickListener { callback.onItemClicked(itemView, item) }
        }
    }
}