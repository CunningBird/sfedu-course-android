package com.cunningbird.cats.view.lists.cats.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.cunningbird.cats.R
import com.cunningbird.cats.model.lists.CatListItem

class CatsImageAdapter(val callback: RecyclerViewClickListener) : PagingDataAdapter<CatListItem, RecyclerView.ViewHolder>(REPO_COMPARATOR) {

    interface RecyclerViewClickListener {
        fun onItemClicked(view: View, data: CatListItem)
        fun onItemLongClicked(view: View, data: CatListItem): Boolean
    }

    companion object {
        private val REPO_COMPARATOR = object : DiffUtil.ItemCallback<CatListItem>() {
            override fun areItemsTheSame(oldItem: CatListItem, newItem: CatListItem): Boolean = oldItem == newItem
            override fun areContentsTheSame(oldItem: CatListItem, newItem: CatListItem): Boolean = oldItem == newItem
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        getItem(position)?.let { (holder as? CatImageListItemViewHolder)?.bind(item = it) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return CatImageListItemViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_cat_image_view, parent, false))
    }

    inner class CatImageListItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private var ivCatMain: ImageView = view.findViewById(R.id.ivCatMain)

        fun bind(item: CatListItem) {
            ivCatMain.load(item.url) { placeholder(R.drawable.cat_placeholder) }
            ivCatMain.setOnClickListener { callback.onItemClicked(itemView, item) }
            ivCatMain.setOnLongClickListener { callback.onItemLongClicked(itemView, item) }
        }
    }
}