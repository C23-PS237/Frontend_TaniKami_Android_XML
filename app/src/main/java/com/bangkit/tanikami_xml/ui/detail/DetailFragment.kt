package com.bangkit.tanikami_xml.ui.detail

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
import com.bangkit.tanikami_xml.databinding.FragmentDetailBinding
import com.bangkit.tanikami_xml.utils.Formatted
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment() : Fragment() {
    private val detailProductViewModel by  viewModels<DetailProductViewModel>()
    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!
    private lateinit var id_produk: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val idProduk = DetailFragmentArgs.fromBundle(arguments as Bundle).idProduct

        Log.d("DetailProductId", "onViewCreated: $idProduk", )
        detailProductViewModel.getProductbyIdProduct(idProduk).observe(viewLifecycleOwner){
            when(it) {
                is Response.Loading -> ""
                is Response.Error -> {
                    Toast.makeText(
                        requireActivity(),
                        "Data gagal ditampilkan",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                is Response.Success -> {
                    setVisibilityEditButton(it.data.payload.id_ktp)
                    binding.apply {
                        id_produk = it.data.payload.id_produk.toString()
                        Glide.with(requireActivity())
                            .load(it.data.payload.gambar_produk)
                            .into(ivProductsDetail)
                        tvProductsNameDetail.text = it.data.payload.nama_produk
                        tvProductsPriceDetail.text = getString(R.string.products_price,
                            Formatted.formatIDRCurrency(it.data.payload.harga),
                            it.data.payload.besaran_stok
                        )
                        if (it.data.payload.stok > 0 ) {
                            tvProductsStockDetail.text = getString(R.string.products_stock, it.data.payload.stok, it.data.payload.besaran_stok)
                        } else {
                            tvProductsStockDetail.text = getString(R.string.habis)
                            btnBuy.isEnabled = false
                        }
                        tvProductsDescriptionDetailValue.text = it.data.payload.deskripsi_produk
                    }
                }
            }
        }

        binding.fabEditDetail.setOnClickListener{
            val editDirects = DetailFragmentDirections.actionDetailFragmentToEditProductFragment(idProduk)
            editDirects.idProduk = idProduk

            findNavController().navigate(editDirects)
        }

        binding.btnBuy.setOnClickListener{
            if (binding.amountEditTextProduct.text.toString() != "") {
                val toPaymentFragment = DetailFragmentDirections.actionDetailFragmentToConfirmPaymentFragment()
                toPaymentFragment.idProduct = id_produk.toInt()
                toPaymentFragment.totalBuy = binding.amountEditTextProduct.text.toString().toInt()
                findNavController().navigate(toPaymentFragment)
            } else {
                Toast.makeText(requireActivity(), "Amount to buy can't be empty", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setVisibilityEditButton(idKtpFromProduct: String) {
        detailProductViewModel.getIdKtpUserForEditingButtonVisibility().observe(viewLifecycleOwner) {
            if (it != idKtpFromProduct) {
                binding.fabEditDetail.visibility = View.GONE
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {

    }
}