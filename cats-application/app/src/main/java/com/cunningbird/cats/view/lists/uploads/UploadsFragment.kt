package com.cunningbird.cats.view.lists.uploads

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.ExperimentalPagingApi
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cunningbird.cats.R
import com.cunningbird.cats.model.lists.UploadedCatListItem
import com.cunningbird.cats.view.lists.favorites.FavoritesFragmentDirections
import com.cunningbird.cats.view.lists.uploads.adapter.UploadsImageAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException


@ExperimentalPagingApi
class UploadsFragment : Fragment(R.layout.fragment_uploads), UploadsImageAdapter.RecyclerViewClickListener {

    private lateinit var thisView: View
    private lateinit var galleryButton: Button
    private lateinit var cameraButton: Button
    private lateinit var rvCatRemote: RecyclerView
    private lateinit var uploadsViewModel: UploadsViewModel
    private lateinit var adapter: UploadsImageAdapter

    private var cameraCallback = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {

            val data: Intent? = result.data
            val thumbnail: Bitmap = data?.extras?.get("data") as Bitmap
            val bytes = ByteArrayOutputStream()
            thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes)
            val destination = File(Environment.getExternalStorageDirectory(), "temp.jpg")
            val fo: FileOutputStream
            try {
                fo = FileOutputStream(destination)
                fo.write(bytes.toByteArray())
                fo.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }

            addUploaded(destination)
        }
    }

    private var galleryCallback = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {

            val data: Intent? = result.data
            val uri: String = data?.extras?.get("data") as String // TODO Get uri from data

            val destination = File(uri)

            addUploaded(destination)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initMembers()
        setUpViews(view)
        fetchCatImages()
        applyListeners()
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
        thisView = view
        galleryButton = view.findViewById(R.id.upload_from_gallery)
        cameraButton = view.findViewById(R.id.upload_from_camera)
        rvCatRemote = view.findViewById(R.id.rvCatUploads)
        rvCatRemote.layoutManager = GridLayoutManager(context, 2)
        rvCatRemote.adapter = adapter
    }

    private fun applyListeners() {
        galleryButton.setOnClickListener {
            getFileFromGallery()
        }
        cameraButton.setOnClickListener {
            getFileFromCamera()
        }
    }

    override fun onItemClicked(view: View, data: UploadedCatListItem) {
        val navigation = FavoritesFragmentDirections.actionFavoritesFragmentToFeaturedFragment(data.id)
        findNavController().navigate(navigation)
    }

    override fun onItemLongClicked(view: View, data: UploadedCatListItem): Boolean {
        lifecycleScope.launch {
            val status = uploadsViewModel.removeUploaded(data.id)
            printResult(view, "Remove Uploaded: $status")
            adapter.refresh()
        }
        return true
    }

    private fun getFileFromGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
        galleryCallback.launch(intent)
    }

    private fun getFileFromCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        cameraCallback.launch(intent)
    }

    private fun addUploaded(file: File) {
        lifecycleScope.launch {
            val status = uploadsViewModel.addUploaded(file, "1")
            printResult(thisView, "Add new file: $status")
            adapter.refresh()
        }
    }

    private fun printResult(view: View, message: String) {
        Toast.makeText(view.context, message, Toast.LENGTH_SHORT).show();
    }
}