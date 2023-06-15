package com.bangkit.tanikami_xml.ui.payment.confirmation_payment

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bangkit.tanikami_xml.R
import com.bangkit.tanikami_xml.data.helper.Response
import com.bangkit.tanikami_xml.data.model.PembelianTempData
import com.bangkit.tanikami_xml.databinding.FragmentConfirmPaymentBinding
import com.bangkit.tanikami_xml.ui.payment.PaymentViewModel
import com.bangkit.tanikami_xml.utils.Formatted.formatIDRCurrency
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody

@AndroidEntryPoint
class ConfirmPaymentFragment : Fragment() {
    private var _binding: FragmentConfirmPaymentBinding? = null
    private val binding get() = _binding!!
    private val paymentViewModel by viewModels<PaymentViewModel>()
    private lateinit var data_pembelian: PembelianTempData
    private var totalDibayar: Int = 0

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
            Log.d(TAG, "onViewCreated: $data_pembelian")
            paymentViewModel.buyProductNow(
                data_pembelian.id_ktp,
                data_pembelian.id_product,
                data_pembelian.alamat_penerima,
                data_pembelian.harga,
                data_pembelian.jumlah_beli,
                data_pembelian.biaya_pengiriman,
                data_pembelian.pajak,
                data_pembelian.biaya_admin,
                data_pembelian.biaya_total,
                data_pembelian.status_pembayaran,
                data_pembelian.status_pengiriman,
                data_pembelian.id_penjual
            ).observe(viewLifecycleOwner) { result ->
                when (result) {
                    is Response.Loading -> ""
                    is Response.Error -> ""
                    is Response.Success -> {
                        AlertDialog.Builder(requireActivity()).apply {
                            setTitle("Successfully buying")
                            setMessage("Product added to history buy, complete payment now!")
                            setPositiveButton("OK") { _,_ ->
                                val confDirections = ConfirmPaymentFragmentDirections.actionConfirmPaymentFragmentToPaymentsStepFragment()
                                confDirections.totalDibayar = totalDibayar
                                confDirections.idTransaksi = result.data.data.id
                                findNavController().navigate(confDirections)
                            }
                            create()
                            show()
                        }
                    }
                }
            }
        }

        binding.fabEditDetail.setOnClickListener {
            binding.tvAddress.isEnabled = true
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
                        val temp_total = tempPrice + temp_tax + 2000 + 20000
                        tvTotalPayment.text = formatIDRCurrency(temp_total)
                        tvAdminPaymentCost.text = formatIDRCurrency(2000)
                        tvBiayaPengirimanPayment.setText(formatIDRCurrency(20000))
                        paymentViewModel.getAddress().observe(viewLifecycleOwner) { dataAddress ->
                            addressEditText.setText(dataAddress)
                        }

                        totalDibayar = temp_total

                        paymentViewModel.getIdKtp().observe(viewLifecycleOwner) { dataKTP ->
                            val idKtp_ReqBody = dataKTP.toRequestBody("text/plain".toMediaTypeOrNull())
                            val idProduk_ReqBody = data.id_produk.toString().toRequestBody("text/plain".toMediaTypeOrNull())
                            val address_ReqBody = addressEditText.text.toString().toRequestBody("plain/text".toMediaTypeOrNull())
                            val harga_ReqBody = data.harga.toString().toRequestBody("text/plain".toMediaTypeOrNull())
                            val jumlah_ReqBody = amount.toString().toRequestBody("text/plain".toMediaTypeOrNull())
                            val pajak_ReqBody = temp_tax.toString().toRequestBody("text/plain".toMediaTypeOrNull())
                            val biayaAdm_ReqBody = "2000".toRequestBody("text/plain".toMediaTypeOrNull())
                            val biayaTot_ReqBody = temp_total.toString().toRequestBody("text/plain".toMediaTypeOrNull())
                            val status_ReqBody = "0".toRequestBody("text/plain".toMediaTypeOrNull())
                            val idPenjual_ReqBody = data.id_ktp.toRequestBody("text/plain".toMediaTypeOrNull())

                            data_pembelian = PembelianTempData(
                                idKtp_ReqBody,
                                idProduk_ReqBody,
                                address_ReqBody,
                                harga_ReqBody,
                                jumlah_ReqBody,
                                "20000".toRequestBody("text/plain".toMediaTypeOrNull()),
                                pajak_ReqBody,
                                biayaAdm_ReqBody,
                                biayaTot_ReqBody,
                                status_ReqBody,
                                status_ReqBody,
                                idPenjual_ReqBody
                            )
                            Log.d(TAG, "setValue: $data_pembelian")
                        }
                    }
                }
            }
        }
    }

    companion object {
        private const val TAG = "CONFIRM_PAYMENT"
    }
}