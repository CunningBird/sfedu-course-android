package com.cunningbird.cats.view.details.card

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
import kotlinx.coroutines.launch

@ExperimentalPagingApi
class CardFragment : Fragment(R.layout.fragment_card) {

    private lateinit var catImage: ImageView
    private lateinit var catId: TextView
    private lateinit var catWidth: TextView
    private lateinit var catHeight: TextView

    private lateinit var catsViewModel: CardViewModel

    private val args: CardFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initMembers()
        setUpViews(view)
        fetchCatImage()
    }

    private fun fetchCatImage() {
        lifecycleScope.launch {
            val catImageModel = catsViewModel.getCatInfo(args.imageId)
            catImage.load(catImageModel.url) { placeholder(R.drawable.cat_placeholder) }
            catId.text = "Cat ID: " + catImageModel.id
            catWidth.text = "Image width: " + catImageModel.width
            catHeight.text = "Image height: " + catImageModel.height
        }
    }

    private fun initMembers() {
        catsViewModel = defaultViewModelProviderFactory.create(CardViewModel::class.java)
    }

    private fun setUpViews(view: View) {
        catImage = view.findViewById(R.id.cat_image)
        catId = view.findViewById(R.id.cat_id)
        catWidth = view.findViewById(R.id.cat_width)
        catHeight = view.findViewById(R.id.cat_height)
    }
}