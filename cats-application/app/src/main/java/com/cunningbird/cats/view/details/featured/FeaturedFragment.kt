package com.cunningbird.cats.view.details.featured

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.paging.ExperimentalPagingApi
import coil.load
import com.cunningbird.cats.R
import com.cunningbird.cats.view.details.card.CardFragmentArgs
import kotlinx.coroutines.launch

@ExperimentalPagingApi
class FeaturedFragment : Fragment(R.layout.fragment_card) {

    private lateinit var catImage: ImageView
    private lateinit var catId: TextView
    private lateinit var catsViewModel: FeaturedViewModel

    private val args: CardFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initMembers()
        setUpViews(view)
        fetchCatImage()
    }

    private fun fetchCatImage() {
        lifecycleScope.launch {
            val catImageModel = catsViewModel.getFavoriteInfo(args.imageId)
            catImage.load(catImageModel.image.url) { placeholder(R.drawable.cat_placeholder) }
            catId.text = "Cat ID: " + catImageModel.image.id
        }
    }

    private fun initMembers() {
        catsViewModel = defaultViewModelProviderFactory.create(FeaturedViewModel::class.java)
    }

    private fun setUpViews(view: View) {
        catImage = view.findViewById(R.id.cat_image)
        catId = view.findViewById(R.id.cat_id)
    }
}