package com.bangkit.tanikami_xml.ui.payment.payment_steps

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.bangkit.tanikami_xml.R
import com.bangkit.tanikami_xml.databinding.FragmentPaymentsStepBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PaymentsStepFragment : Fragment() {
    private var _binding:FragmentPaymentsStepBinding?=null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentPaymentsStepBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnUploadBill.setOnClickListener {
            findNavController().navigate(R.id.action_paymentsStepFragment_to_uploadingBillFragment)
        }
        binding.btnDone.setOnClickListener {
            findNavController().navigate(R.id.action_paymentsStepFragment_to_historyBuyFragment)
        }
    }

    companion object {

    }
}