package com.cunningbird.cats.view.details.research

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.paging.ExperimentalPagingApi
import com.cunningbird.cats.R
import kotlinx.coroutines.launch

@ExperimentalPagingApi
class ResearchFragment : Fragment(R.layout.fragment_research) {

    private lateinit var catTitle: TextView
    private lateinit var researchViewModel: ResearchViewModel

    private val args: ResearchFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initMembers()
        setUpViews(view)
        fetchCatImage()
    }

    private fun fetchCatImage() {
        lifecycleScope.launch {
            val catImageModel = researchViewModel.getUploadedCatInfo(args.imageId);
            catTitle.text = catImageModel.image_id
        }
    }

    private fun initMembers() {
        researchViewModel = defaultViewModelProviderFactory.create(ResearchViewModel::class.java)
    }

    private fun setUpViews(view: View) {
        catTitle = view.findViewById(R.id.uploaded_cat_title)
    }
}