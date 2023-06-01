package com.bangkit.tanikami_xml.ui.product.confirm_add

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.bangkit.tanikami_xml.R
import com.bangkit.tanikami_xml.databinding.FragmentConfirmAddProductBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ConfirmAddProductFragment : Fragment() {

    private var _binding: FragmentConfirmAddProductBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentConfirmAddProductBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnCancel.setOnClickListener {
            findNavController().navigate(R.id.action_confirmAddProductFragment_to_addProductFragment)
        }
        binding.btnSell.setOnClickListener {
            findNavController().navigate(R.id.action_confirmAddProductFragment_to_historySellFragment)
        }
    }

    companion object {
    }
}