package com.cunningbird.cats.view.details.research

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ListAdapter
import android.widget.ListView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.paging.ExperimentalPagingApi
import androidx.recyclerview.widget.RecyclerView
import com.cunningbird.cats.R
import com.cunningbird.cats.view.details.research.adapter.LabelsAdapter
import kotlinx.coroutines.launch

@ExperimentalPagingApi
class ResearchFragment : Fragment(R.layout.fragment_research) {

    private lateinit var catTitle: TextView
    private lateinit var vendor: TextView
    private lateinit var createdAt: TextView
    private lateinit var researchViewModel: ResearchViewModel
    private lateinit var labels: RecyclerView

    private val args: ResearchFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initMembers()
        setUpViews(view)
        fetchCatImage(view)
    }

    private fun fetchCatImage(view: View) {
        lifecycleScope.launch {
            val catImageModel = researchViewModel.getUploadedCatInfo(args.imageId);
            catTitle.text = catImageModel.image_id
            vendor.text = catImageModel.vendor
            createdAt.text = catImageModel.created_at

            labels.adapter = LabelsAdapter(catImageModel.labels, LayoutInflater.from(view.context))
        }
    }

    private fun initMembers() {
        researchViewModel = defaultViewModelProviderFactory.create(ResearchViewModel::class.java)
    }

    private fun setUpViews(view: View) {
        catTitle = view.findViewById(R.id.cat_id)
        vendor = view.findViewById(R.id.vendor)
        createdAt = view.findViewById(R.id.created_at)
        labels = view.findViewById(R.id.research_labels)
    }
}