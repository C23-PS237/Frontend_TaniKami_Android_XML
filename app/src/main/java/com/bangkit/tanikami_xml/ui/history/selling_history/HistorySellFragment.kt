package com.bangkit.tanikami_xml.ui.history.selling_history

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.bangkit.tanikami_xml.R
import com.bangkit.tanikami_xml.databinding.FragmentHistorySellBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HistorySellFragment : Fragment() {

    private var _binding:FragmentHistorySellBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHistorySellBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvSalesHistory.setOnClickListener {
            findNavController().navigate(R.id.action_historySellFragment_to_detailFragment)
        }
    }
    companion object {
    }
}