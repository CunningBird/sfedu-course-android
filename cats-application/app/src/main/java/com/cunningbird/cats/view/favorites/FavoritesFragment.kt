package com.cunningbird.cats.view.favorites

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.paging.ExperimentalPagingApi
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cunningbird.cats.R
import com.cunningbird.cats.model.CatImage
import com.cunningbird.cats.view.favorites.adapter.FavoritesImageAdapter
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

    override fun onItemClicked(view: View, data: CatImage) {
        //val navigation = CatsFragmentDirections.actionCatsFragmentToCardFragment(data.id)
        //findNavController().navigate(navigation)
        println("Favorites Flex")
    }

    override fun onItemLongClicked(view: View, data: CatImage): Boolean {
        TODO("Not yet implemented")
    }
}