package com.cunningbird.cats.view.lists.cats

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
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
        setHasOptionsMenu(true)

        initMembers()
        setUpViews(view)
        fetchCatImages("ASC")

        registerForContextMenu(view)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.sort_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.sort_asc -> {
                adapter.refresh()
                fetchCatImages("ASC")
                true
            }
            R.id.sort_desc -> {
                adapter.refresh()
                fetchCatImages("DESC")
                true
            }
            R.id.sort_random -> {
                adapter.refresh()
                fetchCatImages("RANDOM")
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun fetchCatImages(sort: String) {
        lifecycleScope.launch {
            catsViewModel.fetchCatImages(sort).distinctUntilChanged().collectLatest {
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
            val status = catsViewModel.addFeatured(data.id, "1")
            printResult(view, status)
        }
        return true
    }

    private fun printResult(view: View, status: String) {
        Toast.makeText(view.context, "Add Favorite: $status", Toast.LENGTH_SHORT).show();
    }
}