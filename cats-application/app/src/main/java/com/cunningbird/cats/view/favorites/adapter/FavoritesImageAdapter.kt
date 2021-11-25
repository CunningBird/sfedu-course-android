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
import com.cunningbird.cats.model.CatFavorite

class FavoritesImageAdapter(val callback: RecyclerViewClickListener) : PagingDataAdapter<CatFavorite, RecyclerView.ViewHolder>(REPO_COMPARATOR) {

    interface RecyclerViewClickListener {
        fun onItemClicked(view: View, data: CatFavorite)
        fun onItemLongClicked(view: View, data: CatFavorite): Boolean
    }

    companion object {
        private val REPO_COMPARATOR = object : DiffUtil.ItemCallback<CatFavorite>() {
            override fun areItemsTheSame(oldItem: CatFavorite, newItem: CatFavorite): Boolean = oldItem == newItem
            override fun areContentsTheSame(oldItem: CatFavorite, newItem: CatFavorite): Boolean = oldItem == newItem
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        getItem(position)?.let { (holder as? CatFavoriteViewHolder)?.bind(item = it) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return CatFavoriteViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_cat_image_view, parent, false))
    }

    inner class CatFavoriteViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private var ivCatMain: ImageView = view.findViewById(R.id.ivCatMain)

        fun bind(item: CatFavorite) {
            ivCatMain.load(item.image.url) { placeholder(R.drawable.cat_placeholder) }
            ivCatMain.setOnClickListener { callback.onItemClicked(itemView, item) }
            ivCatMain.setOnLongClickListener { callback.onItemLongClicked(itemView, item) }
        }
    }
}