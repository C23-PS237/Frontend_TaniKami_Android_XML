package com.bangkit.tanikami_xml.ui.payment.payment_steps

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.bangkit.tanikami_xml.R
import com.bangkit.tanikami_xml.databinding.FragmentPaymentsStepBinding
import dagger.hilt.android.AndroidEntryPoint
import com.bangkit.tanikami_xml.utils.Formatted.formatIDRCurrency

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

        val totalDibayar = PaymentsStepFragmentArgs.fromBundle(arguments as Bundle).totalDibayar
        val id_transaksi = PaymentsStepFragmentArgs.fromBundle(arguments as Bundle).idTransaksi

        binding.apply {
            tvTotalPayment.text = formatIDRCurrency(totalDibayar)
            tvTheAccountNumber.text = getString(R.string.account_number)
            tvCopyAccountNumber.setOnClickListener {
                val clipboardManager = activity?.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager

                val clipData = ClipData.newPlainText(getString(R.string.clip_label), getString(R.string.account_number))
                clipboardManager.setPrimaryClip(clipData)

                Toast.makeText(requireActivity(), getString(R.string.toast_indi_clip), Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnUploadBill.setOnClickListener {
            val toUploadBill = PaymentsStepFragmentDirections.actionPaymentsStepFragmentToUploadingBillFragment()
            toUploadBill.idTransaksi = id_transaksi
            findNavController().navigate(toUploadBill)
        }
    }

    companion object {

    }
}