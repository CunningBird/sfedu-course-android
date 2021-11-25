package com.cunningbird.cats.view.lists.uploads.adapter

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
import com.cunningbird.cats.model.lists.UploadedCatListItem

class UploadsImageAdapter(val callback: RecyclerViewClickListener) : PagingDataAdapter<UploadedCatListItem, RecyclerView.ViewHolder>(REPO_COMPARATOR) {

    interface RecyclerViewClickListener {
        fun onItemClicked(view: View, data: UploadedCatListItem)
    }

    companion object {
        private val REPO_COMPARATOR = object : DiffUtil.ItemCallback<UploadedCatListItem>() {
            override fun areItemsTheSame(oldItem: UploadedCatListItem, newItem: UploadedCatListItem): Boolean = oldItem == newItem
            override fun areContentsTheSame(oldItem: UploadedCatListItem, newItem: UploadedCatListItem): Boolean = oldItem == newItem
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

        fun bind(item: UploadedCatListItem) {
            ivCatMain.load(item.url) { placeholder(R.drawable.cat_placeholder) }
            ivCatMain.setOnClickListener { callback.onItemClicked(itemView, item) }
        }
    }
}