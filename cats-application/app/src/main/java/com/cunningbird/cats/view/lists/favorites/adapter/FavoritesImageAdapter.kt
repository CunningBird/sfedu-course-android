package com.cunningbird.cats.view.lists.favorites.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.cunningbird.cats.R
import com.cunningbird.cats.model.lists.FavoriteCatListItem

class FavoritesImageAdapter(val callback: RecyclerViewClickListener) : PagingDataAdapter<FavoriteCatListItem, RecyclerView.ViewHolder>(REPO_COMPARATOR) {

    interface RecyclerViewClickListener {
        fun onItemClicked(view: View, data: FavoriteCatListItem)
        fun onItemLongClicked(view: View, data: FavoriteCatListItem): Boolean
    }

    companion object {
        private val REPO_COMPARATOR = object : DiffUtil.ItemCallback<FavoriteCatListItem>() {
            override fun areItemsTheSame(oldItem: FavoriteCatListItem, newItem: FavoriteCatListItem): Boolean = oldItem == newItem
            override fun areContentsTheSame(oldItem: FavoriteCatListItem, newItem: FavoriteCatListItem): Boolean = oldItem == newItem
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        getItem(position)?.let { (holder as? CatFavoriteListItemViewHolder)?.bind(item = it) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return CatFavoriteListItemViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_cat_image_view, parent, false))
    }

    inner class CatFavoriteListItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private var ivCatMain: ImageView = view.findViewById(R.id.ivCatMain)

        fun bind(item: FavoriteCatListItem) {
            ivCatMain.load(item.image.url) { placeholder(R.drawable.cat_placeholder) }
            ivCatMain.setOnClickListener { callback.onItemClicked(itemView, item) }
            ivCatMain.setOnLongClickListener { callback.onItemLongClicked(itemView, item) }
        }
    }
}