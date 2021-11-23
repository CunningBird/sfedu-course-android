package com.cunningbird.cats.ui.downloads

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.cunningbird.cats.databinding.FragmentDownloadsBinding

class DownloadsFragment : Fragment() {

    private lateinit var downloadsViewModel: DownloadsViewModel
    private var _binding: FragmentDownloadsBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        downloadsViewModel =
            ViewModelProvider(this)[DownloadsViewModel::class.java]

        _binding = FragmentDownloadsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textDownloads
        downloadsViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}