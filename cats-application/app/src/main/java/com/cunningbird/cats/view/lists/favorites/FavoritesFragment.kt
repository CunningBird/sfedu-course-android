package com.cunningbird.cats.view.lists.favorites

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.ExperimentalPagingApi
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cunningbird.cats.R
import com.cunningbird.cats.model.lists.CatListItem
import com.cunningbird.cats.model.lists.FavoriteCatListItem
import com.cunningbird.cats.view.lists.cats.CatsFragmentDirections
import com.cunningbird.cats.view.lists.favorites.adapter.FavoritesImageAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

@ExperimentalPagingApi
class FavoritesFragment : Fragment(R.layout.fragment_favorites), FavoritesImageAdapter.RecyclerViewClickListener {

    private lateinit var rvCatRemote: RecyclerView
    private lateinit var favoritesViewModel: FavoritesViewModel
    private lateinit var adapter: FavoritesImageAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initMembers()
        setUpViews(view)
        fetchCatImages()
    }

    private fun fetchCatImages() {
        lifecycleScope.launch {
            favoritesViewModel.fetchCatImages().distinctUntilChanged().collectLatest {
                adapter.submitData(it)
            }
        }
    }

    private fun initMembers() {
        favoritesViewModel = defaultViewModelProviderFactory.create(FavoritesViewModel::class.java)
        adapter = FavoritesImageAdapter(this)
    }

    private fun setUpViews(view: View) {
        rvCatRemote = view.findViewById(R.id.rvCatFavorites)
        rvCatRemote.layoutManager = GridLayoutManager(context, 2)
        rvCatRemote.adapter = adapter
    }

    override fun onItemClicked(view: View, data: FavoriteCatListItem) {
        val navigation = FavoritesFragmentDirections.actionFavoritesFragmentToFeaturedFragment(data.id)
        findNavController().navigate(navigation)
    }

    override fun onItemLongClicked(view: View, data: FavoriteCatListItem): Boolean {
        lifecycleScope.launch {
            val status = "Failed"//favoritesViewModel.addCatImageAsFavorites(data.id, "1")
            printResult(view, status)
        }
        return true
    }

    private fun printResult(view: View, status: String) {
        Toast.makeText(view.context, "Remove Favorite: $status", Toast.LENGTH_SHORT).show();
    }
}