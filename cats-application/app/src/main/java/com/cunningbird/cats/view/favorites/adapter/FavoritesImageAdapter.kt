package com.cunningbird.cats.view.favorites.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.cunningbird.cats.R
import com.cunningbird.cats.model.CatImageModel

class FavoritesImageAdapter(val callback: RecyclerViewClickListener) : PagingDataAdapter<CatImageModel, RecyclerView.ViewHolder>(REPO_COMPARATOR) {

    interface RecyclerViewClickListener {
        fun onItemClicked(view: View, data: CatImageModel)
    }

    companion object {
        private val REPO_COMPARATOR = object : DiffUtil.ItemCallback<CatImageModel>() {
            override fun areItemsTheSame(oldItem: CatImageModel, newItem: CatImageModel): Boolean = oldItem == newItem
            override fun areContentsTheSame(oldItem: CatImageModel, newItem: CatImageModel): Boolean = oldItem == newItem
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        getItem(position)?.let { (holder as? CatImageViewHolder)?.bind(item = it) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return CatImageViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_cat_image_view, parent, false))
    }

    inner class CatImageViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private var ivCatMain: ImageView = view.findViewById(R.id.ivCatMain)

        fun bind(item: CatImageModel) {
            ivCatMain.load(item.url) { placeholder(R.drawable.cat_placeholder) }
            ivCatMain.setOnClickListener { callback.onItemClicked(itemView, item) }
        }
    }
}