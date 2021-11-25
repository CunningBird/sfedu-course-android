package com.cunningbird.cats.view.card

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
class CardFragment : Fragment(R.layout.fragment_card) {

    private lateinit var catTitle: TextView
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
            val catImageModel = catsViewModel.getCatInfo(args.imageId);
            catTitle.text = catImageModel.id
        }
    }

    private fun initMembers() {
        catsViewModel = defaultViewModelProviderFactory.create(CardViewModel::class.java)
    }

    private fun setUpViews(view: View) {
        catTitle = view.findViewById(R.id.cat_title)
    }
}