package com.bangkit.tanikami_xml.ui.product.confirm_add

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
import com.bangkit.tanikami_xml.databinding.FragmentConfirmAddProductBinding
import com.bangkit.tanikami_xml.ui.detail.DetailFragmentArgs
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ConfirmAddProductFragment : Fragment() {

    private var _binding: FragmentConfirmAddProductBinding? = null
    private val binding get() = _binding!!
    private val confirmAddProductViewModel by viewModels<ConfirmAddProductViewModel>()
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

        val idProduk = ConfirmAddProductFragmentArgs.fromBundle(arguments as Bundle).idProduct
        confirmAddProductViewModel.getProductbyIdProduct(idProduk).observe(viewLifecycleOwner){
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
                    binding.apply {
                        Glide.with(requireActivity())
                            .load(it.data.payload.gambar_produk)
                            .into(ivImageConfirmProduct)

                        tvValueProductNameConfirm.text = it.data.payload.nama_produk.toString()
                        tvProductDescriptionConfirm.text = it.data.payload.deskripsi_produk.toString()
                        tvValueBankNameConfirm.text = it.data.payload.nama_bank.toString()
                        tvValueAccountNumberConfirm.text = it.data.payload.rek_penjual.toString()
                        tvValueProductPriceConfirm.text = it.data.payload.nama_produk.toString()
                        tvValueProductStockConfirm.text = it.data.payload.stok.toString()
                    }
                }
            }
        }
    }

    companion object {
    }
}