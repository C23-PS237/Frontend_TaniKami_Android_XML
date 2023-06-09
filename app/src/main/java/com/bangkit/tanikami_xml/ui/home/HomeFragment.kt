package com.bangkit.tanikami_xml.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.bangkit.tanikami_xml.R
import com.bangkit.tanikami_xml.data.helper.Response
import com.bangkit.tanikami_xml.data.remote.response.ProductItem
import com.bangkit.tanikami_xml.databinding.FragmentHomeBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val homeViewModel by viewModels<HomeViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.fabSell.setOnClickListener {
            findNavController().navigate(R.id.action_nav_home_to_addProductFragment)
        }

        showListProducts()
    }

    private fun setLoading(state: Boolean) {
        binding.progressBar.visibility = if (state) View.VISIBLE else View.GONE
    }

    private fun showListProducts() {
        binding.apply {
            rvProductSell.layoutManager = GridLayoutManager(requireActivity(), 2)
            homeViewModel.getAllProducts().observe(requireActivity()) {
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
                        val listData = HomeProductAdapter(it.data.payload)
                        rvProductSell.setHasFixedSize(true)
                        rvProductSell.adapter = listData

                        listData.setOnItemClickCallback(object: HomeProductAdapter.OnItemClickCallback {
                            override fun onProductClicked(data: ProductItem) {
                                findNavController().navigate(R.id.action_nav_home_to_detailFragment)
                            }
                        })
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        Log.e("HOMEO", "onDestroy: ooooohhhh Destroyed MAMA") // testing lifecycle fragment
    }

    override fun onStart() {
        super.onStart()
        Log.e("HOMEO", "onStart: OHHH MAMA I'AM BACK")  // testing lifecycle fragment
    }

    companion object {

    }
}