package com.cunningbird.cats.view.lists.cats

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
import com.cunningbird.cats.view.lists.cats.adapter.CatsImageAdapter
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

    override fun onItemClicked(view: View, data: CatListItem) {
        val navigation = CatsFragmentDirections.actionCatsFragmentToCardFragment(data.id)
        findNavController().navigate(navigation)
    }

    override fun onItemLongClicked(view: View, data: CatListItem): Boolean {
        lifecycleScope.launch {
            val status = catsViewModel.addCatImageAsFavorites(data.id, "1")
            printResult(view, status)
        }
        return true
    }

    private fun printResult(view: View, status: String) {
        Toast.makeText(view.context, "Featuring status: $status", Toast.LENGTH_SHORT).show();
    }
}