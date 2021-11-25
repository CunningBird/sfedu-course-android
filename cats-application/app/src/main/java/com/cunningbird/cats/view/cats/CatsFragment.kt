package com.cunningbird.cats.view.cats

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
import com.cunningbird.cats.model.CatImage
import com.cunningbird.cats.view.cats.adapter.CatsImageAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

@ExperimentalPagingApi
class CatsFragment : Fragment(R.layout.fragment_cats), CatsImageAdapter.RecyclerViewClickListener {

    private lateinit var rvCatRemote: RecyclerView
    private lateinit var catsViewModel: CatsViewModel
    private lateinit var adapter: CatsImageAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initMembers()
        setUpViews(view)
        fetchCatImages()

        registerForContextMenu(view)
    }

    private fun fetchCatImages() {
        lifecycleScope.launch {
            catsViewModel.fetchCatImages().distinctUntilChanged().collectLatest {
                adapter.submitData(it)
            }
        }
    }

    private fun initMembers() {
        catsViewModel = defaultViewModelProviderFactory.create(CatsViewModel::class.java)
        adapter = CatsImageAdapter(this)
    }

    private fun setUpViews(view: View) {
        rvCatRemote = view.findViewById(R.id.rvCatList)
        rvCatRemote.layoutManager = GridLayoutManager(context, 2)
        rvCatRemote.adapter = adapter
    }

    override fun onItemClicked(view: View, data: CatImage) {
        val navigation = CatsFragmentDirections.actionCatsFragmentToCardFragment(data.id)
        findNavController().navigate(navigation)
    }

    override fun onItemLongClicked(view: View, data: CatImage): Boolean {
        val status = catsViewModel.addCatImageAsFavorites(data.id)
        Toast.makeText(view.context, status, Toast.LENGTH_SHORT).show();
        return true
    }
}