package com.bangkit.tanikami_xml.ui.history.selling_history

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.tanikami_xml.R
import com.bangkit.tanikami_xml.data.helper.Response
import com.bangkit.tanikami_xml.data.remote.response.ProductItem
import com.bangkit.tanikami_xml.databinding.FragmentHistorySellBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HistorySellFragment : Fragment() {

    private var _binding:FragmentHistorySellBinding? = null
    private val binding get() = _binding!!
    private val historySellViewModel by viewModels<HistorySellViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHistorySellBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        historySellProduct()
//        binding.rvSalesHistory.setOnClickListener {
//            findNavController().navigate(R.id.action_historySellFragment_to_detailFragment)
//        }
    }

    private fun setLoading(state: Boolean) {
        binding.progressBar.visibility = if (state) View.VISIBLE else View.GONE
    }
    private fun historySellProduct(){
        binding.apply {
            rvSalesHistory.layoutManager = LinearLayoutManager(requireActivity())
            historySellViewModel.getIdKtp().observe(requireActivity()) {
                historySellViewModel.getProductbyIdKTP(it).observe(requireActivity()) {
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
                            setLoading(false)
                            val listData = HistorySellAdapter(it.data.payload)
                            rvSalesHistory.setHasFixedSize(true)
                            rvSalesHistory.adapter = listData

                            listData.setOnItemClickCallback(object :
                                HistorySellAdapter.OnItemClickCallback {
                                override fun onHistorySellClicked(data: ProductItem) {
//                                    val toDetailFragment = HistorySellFragmentDirections.actionHistorySellFragmentToDetailFragment()
//                                    toDetailFragment.actionId = data.id_produk
                                    findNavController().navigate(R.id.action_historySellFragment_to_detailFragment)
                                }
                            })
                        }
                    }
                }
            }
        }

    }
    companion object {

    }
}