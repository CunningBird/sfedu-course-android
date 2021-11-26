package com.cunningbird.cats.view.details.card

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
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

    private lateinit var likeButton: Button
    private lateinit var dislikeButton: Button

    private lateinit var catsViewModel: CardViewModel

    private val args: CardFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initMembers()
        setUpViews(view)
        fetchCatImage()
        applyListeners(view)
    }

    private fun fetchCatImage() {
        lifecycleScope.launch {
            val catImageModel = catsViewModel.getCatInfo(args.imageId)
            catImage.load(catImageModel.url) { placeholder(R.drawable.cat_placeholder) }
            catId.text = "ID: " + catImageModel.id
            catWidth.text = "Width: " + catImageModel.width
            catHeight.text = "Height: " + catImageModel.height
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

        likeButton = view.findViewById(R.id.buttonLike)
        dislikeButton = view.findViewById(R.id.buttonDislike)
    }

    private fun applyListeners(view: View) {
        likeButton.setOnClickListener {
            vote(view, "Like", 1)
        }
        dislikeButton.setOnClickListener {
            vote(view, "Dislike", 0)
        }
    }

    private fun vote(view: View, name: String, value: Int) {
        lifecycleScope.launch {
            val status = catsViewModel.vote(args.imageId, value)
            printResult(view, "$name status: $status")
        }
    }

    private fun printResult(view: View, message: String) {
        Toast.makeText(view.context, message, Toast.LENGTH_SHORT).show();
    }
}