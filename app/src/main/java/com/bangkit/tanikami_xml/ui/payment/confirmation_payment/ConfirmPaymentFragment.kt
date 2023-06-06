package com.bangkit.tanikami_xml.ui.payment.confirmation_payment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.bangkit.tanikami_xml.R
import com.bangkit.tanikami_xml.databinding.FragmentConfirmPaymentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ConfirmPaymentFragment : Fragment() {
    private var _binding: FragmentConfirmPaymentBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentConfirmPaymentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnCancel.setOnClickListener {
           findNavController().navigate(R.id.action_confirmPaymentFragment_to_detailFragment)
        }
        binding.btnPay.setOnClickListener {
            findNavController().navigate(R.id.action_confirmPaymentFragment_to_paymentsStepFragment)
        }
    }
    companion object {

    }
}