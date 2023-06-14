package com.bangkit.tanikami_xml.ui.payment.confirmation_payment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bangkit.tanikami_xml.R
import com.bangkit.tanikami_xml.data.helper.Response
import com.bangkit.tanikami_xml.databinding.FragmentConfirmPaymentBinding
import com.bangkit.tanikami_xml.ui.payment.PaymentViewModel
import com.bangkit.tanikami_xml.utils.Formatted.formatIDRCurrency
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ConfirmPaymentFragment : Fragment() {
    private var _binding: FragmentConfirmPaymentBinding? = null
    private val binding get() = _binding!!
    private val paymentViewModel by viewModels<PaymentViewModel>()

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

        val id_product = ConfirmPaymentFragmentArgs.fromBundle(arguments as Bundle).idProduct
        val amount = ConfirmPaymentFragmentArgs.fromBundle(arguments as Bundle).totalBuy

        setValue(id_product, amount)

        binding.btnPay.setOnClickListener {
            findNavController().navigate(R.id.action_confirmPaymentFragment_to_paymentsStepFragment)
        }
    }

    private fun setValue(idProduct: Int, amount: Int) {
        paymentViewModel.getDataProductById(idProduct).observe(viewLifecycleOwner) {
            when (it) {
                is Response.Loading -> ""
                is Response.Error -> {
                    Toast.makeText(requireActivity(), "${it.error}", Toast.LENGTH_SHORT).show()
                }
                is Response.Success -> {
                    val data = it.data.payload
                    binding.apply {
                        Glide.with(requireContext())
                            .load(data.gambar_produk)
                            .into(ivProducts)
                        tvProductName.text = data.nama_produk
                        tvProductStock.text = getString(R.string.amount_buy, amount, data.besaran_stok)
                        tvProductPrice.text = getString(R.string.products_price, formatIDRCurrency(data.harga), data.besaran_stok)
                        val tempPrice = amount * data.harga
                        tvProductNameCostPayment.text = formatIDRCurrency(tempPrice)
                        val temp_tax = tempPrice * 2 / 100
                        tvTaxPayment.text = formatIDRCurrency(temp_tax)
                        val temp_total = tempPrice + temp_tax + 2000
                        tvTotalPayment.text = formatIDRCurrency(temp_total)
                        tvAdminPaymentCost.text = formatIDRCurrency(2000)
                        paymentViewModel.getAddress().observe(viewLifecycleOwner) { dataAddress ->
                            tvAddress.text = dataAddress
                        }
                    }
                }
            }
        }
    }

    companion object {

    }
}