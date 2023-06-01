package com.bangkit.tanikami_xml.ui.payment.uploading_bill

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.bangkit.tanikami_xml.R
import com.bangkit.tanikami_xml.databinding.FragmentUploadingBillBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UploadingBillFragment : Fragment() {
    private var _binding: FragmentUploadingBillBinding?=null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentUploadingBillBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnSubmit.setOnClickListener {
            findNavController().navigate(R.id.action_uploadingBillFragment_to_paymentsStepFragment)
        }
    }
    companion object {
    }
}