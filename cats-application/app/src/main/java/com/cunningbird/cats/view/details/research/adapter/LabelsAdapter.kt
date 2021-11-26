package com.cunningbird.cats.view.details.research.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.cunningbird.cats.R
import com.cunningbird.cats.model.details.Labels

class LabelsAdapter(private val list: List<Labels>, private val inflater: LayoutInflater) : RecyclerView.Adapter<LabelsAdapter.ViewHolder>() {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.nameView.text = list[position].Name
        holder.confidenceView.text = list[position].Confidence.toString()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = inflater.inflate(R.layout.item_label, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class ViewHolder internal constructor(view: View) : RecyclerView.ViewHolder(view) {
        val nameView: TextView
        val confidenceView: TextView

        init {
            nameView = view.findViewById(R.id.label_name)
            confidenceView = view.findViewById(R.id.label_confidence)
        }
    }
}