package com.bangkit.tanikami_xml.ui.history.buying_history

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.tanikami_xml.data.helper.Response
import com.bangkit.tanikami_xml.data.remote.response.BuyProduct
import com.bangkit.tanikami_xml.data.remote.response.Product
import com.bangkit.tanikami_xml.databinding.FragmentHistoryBuyBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HistoryBuyFragment : Fragment() {
    private var _binding: FragmentHistoryBuyBinding? = null
    private val binding get() = _binding!!
    private val historyBuyViewModel by viewModels<HistoryBuyViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHistoryBuyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        historyBuyProduct()
//        binding.rvPurchaseHistory.setOnClickListener {
//            findNavController().navigate(R.id.action_historyBuyFragment_to_detailFragment)
//        }
    }

    private fun setLoading(state: Boolean) {
        binding.progressBar.visibility = if (state) View.VISIBLE else View.GONE
    }

    private fun historyBuyProduct() {
        binding.apply {
            rvPurchaseHistory.layoutManager = LinearLayoutManager(requireActivity())
            historyBuyViewModel.getIdKtp().observe(viewLifecycleOwner) {
                historyBuyViewModel.getBuybyIdKtp(it).observe(viewLifecycleOwner) {
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
                            for (item in listData){
                                listIdProduk.add(item.idProduk)
                            }
                            Log.d("listIdProduk", listIdProduk.toString())

                            historyBuyViewModel.getListProductsByIdProducts(listIdProduk).observe(viewLifecycleOwner) { result ->
                                listProduk.addAll(result)

                                Log.d("listProduk", listProduk.toString())
                                setLoading(false)
                                val historyBuyAdapter = HistoryBuyAdapter(listData, listProduk)
                                rvPurchaseHistory.setHasFixedSize(true)
                                rvPurchaseHistory.adapter = historyBuyAdapter

                                historyBuyAdapter.setOnItemClickCallback(object :
                                    HistoryBuyAdapter.OnItemClickCallback {
                                    override fun onHistoryBuyClicked(data: BuyProduct) {
                                        val toDetailFragment = HistoryBuyFragmentDirections.actionHistoryBuyFragmentToDetailFragment()
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

    }
}
