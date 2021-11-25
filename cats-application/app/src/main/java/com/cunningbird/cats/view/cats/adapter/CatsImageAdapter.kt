package com.cunningbird.cats.view.cats.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.cunningbird.cats.R
import com.cunningbird.cats.model.CatImage

class CatsImageAdapter(val callback: RecyclerViewClickListener) : PagingDataAdapter<CatImage, RecyclerView.ViewHolder>(REPO_COMPARATOR) {

    interface RecyclerViewClickListener {
        fun onItemClicked(view: View, data: CatImage)
        fun onItemLongClicked(view: View, data: CatImage): Boolean
    }

    companion object {
        private val REPO_COMPARATOR = object : DiffUtil.ItemCallback<CatImage>() {
            override fun areItemsTheSame(oldItem: CatImage, newItem: CatImage): Boolean = oldItem == newItem
            override fun areContentsTheSame(oldItem: CatImage, newItem: CatImage): Boolean = oldItem == newItem
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

        fun bind(item: CatImage) {
            ivCatMain.load(item.url) { placeholder(R.drawable.cat_placeholder) }
            ivCatMain.setOnClickListener { callback.onItemClicked(itemView, item) }
            ivCatMain.setOnLongClickListener { callback.onItemLongClicked(itemView, item) }
        }
    }
}