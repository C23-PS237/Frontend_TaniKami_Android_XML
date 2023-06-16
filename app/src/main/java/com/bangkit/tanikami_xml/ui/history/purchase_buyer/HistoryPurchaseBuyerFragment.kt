package com.bangkit.tanikami_xml.ui.history.purchase_buyer

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.tanikami_xml.data.helper.Response
import com.bangkit.tanikami_xml.data.remote.response.Product
import com.bangkit.tanikami_xml.data.remote.response.PurchaseBuyer
import com.bangkit.tanikami_xml.databinding.FragmentHistoryPurchaseBuyerBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HistoryPurchaseBuyerFragment : Fragment() {
    private var _binding: FragmentHistoryPurchaseBuyerBinding? = null
    private val binding get() = _binding!!
    private val historyPurchaseBuyerViewModel by viewModels<HistoryPurchaseBuyerViewModel>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHistoryPurchaseBuyerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        historyPurchaseBuyer()
    }

    private fun setLoading(state: Boolean) {
        binding.progressBar.visibility = if (state) View.VISIBLE else View.GONE
    }

    private fun historyPurchaseBuyer() {
        binding.apply {
            rvPurchaseBuyerHistory.layoutManager = LinearLayoutManager(requireActivity())
            historyPurchaseBuyerViewModel.getIdKTP().observe(viewLifecycleOwner) {
                historyPurchaseBuyerViewModel.getBuybyIdPenjual(it).observe(viewLifecycleOwner) {
                    when (it) {
                        is Response.Loading -> setLoading(true)
                        is Response.Error -> {
                            setLoading(false)
                            Snackbar.make(
                                binding.root,
                                it.error,
                                Snackbar.LENGTH_SHORT
                            ).show()
                        }

                        is Response.Success -> {
                            val listData = it.data.payload
                            val listIdProduk = mutableListOf<Int>()
                            val listProduk = mutableListOf<Product>()
                            for (item in listData) {
                                listIdProduk.add(item.idProduk)
                            }
                            Log.d(TAG, listIdProduk.toString())

                            historyPurchaseBuyerViewModel.getListProductsByIdProducts(listIdProduk)
                                .observe(viewLifecycleOwner) { result ->
                                    listProduk.addAll(result)

                                    Log.d(TAG, listProduk.toString())
                                    setLoading(false)
                                    val historyPurchaseBuyerAdapter = HistoryPurchaseBuyerAdapter(listData, listProduk)
                                    rvPurchaseBuyerHistory.setHasFixedSize(true)
                                    rvPurchaseBuyerHistory.adapter = historyPurchaseBuyerAdapter

                                    historyPurchaseBuyerAdapter.setOnItemClickCallback(object :
                                        HistoryPurchaseBuyerAdapter.OnItemClickCallback {
                                        override fun onHistoryBuyClicked(data: PurchaseBuyer) {
                                            val toDetailFragment = HistoryPurchaseBuyerFragmentDirections.actionHistoryPurchaseBuyerFragmentToDetailFragment()
                                            toDetailFragment.idProduct = data.idProduk
                                            findNavController().navigate(toDetailFragment)
                                    }
                                })
                            }
                        }
                    }
                }
            }
        }
    }

    companion object {
        private const val TAG = "listIdProduk&Produk"
    }
}