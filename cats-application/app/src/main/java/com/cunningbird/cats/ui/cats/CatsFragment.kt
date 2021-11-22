package com.cunningbird.cats.ui.cats

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.cunningbird.cats.databinding.FragmentCatsBinding

class CatsFragment : Fragment() {

    private lateinit var catsViewModel: CatsViewModel
    private var _binding: FragmentCatsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        catsViewModel =
            ViewModelProvider(this)[CatsViewModel::class.java]

        _binding = FragmentCatsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textCats
        catsViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}