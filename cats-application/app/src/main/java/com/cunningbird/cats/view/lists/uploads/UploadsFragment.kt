package com.cunningbird.cats.view.lists.uploads

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.paging.ExperimentalPagingApi
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cunningbird.cats.R
import com.cunningbird.cats.model.lists.UploadedCatListItem
import com.cunningbird.cats.view.lists.uploads.adapter.UploadsImageAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

@ExperimentalPagingApi
class UploadsFragment : Fragment(R.layout.fragment_uploads), UploadsImageAdapter.RecyclerViewClickListener {

    private lateinit var rvCatRemote: RecyclerView
    private lateinit var uploadsViewModel: UploadsViewModel
    private lateinit var adapter: UploadsImageAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initMembers()
        setUpViews(view)
        fetchCatImages()
    }

    private fun fetchCatImages() {
        lifecycleScope.launch {
            uploadsViewModel.fetchCatImages().distinctUntilChanged().collectLatest {
                adapter.submitData(it)
            }
        }
    }

    private fun initMembers() {
        uploadsViewModel = defaultViewModelProviderFactory.create(UploadsViewModel::class.java)
        adapter = UploadsImageAdapter(this)
    }

    private fun setUpViews(view: View) {
        rvCatRemote = view.findViewById(R.id.rvCatUploads)
        rvCatRemote.layoutManager = GridLayoutManager(context, 2)
        rvCatRemote.adapter = adapter
    }

    override fun onItemClicked(view: View, data: UploadedCatListItem) {
        //val navigation = CatsFragmentDirections.actionCatsFragmentToCardFragment(data.id)
        //findNavController().navigate(navigation)
        println("Favorites Flex")
    }
}