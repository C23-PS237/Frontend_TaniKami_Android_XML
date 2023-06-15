package com.bangkit.tanikami_xml.ui.user.profile

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
import com.bangkit.tanikami_xml.databinding.FragmentProfileBinding
import com.bangkit.tanikami_xml.ui.user.UserViewModel
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import com.bangkit.tanikami_xml.data.helper.Response

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private val userViewModel by viewModels<UserViewModel>()
    private var stateUser: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userViewModel.getDataFromDataStore().observe(viewLifecycleOwner) { data ->
            setDataProfile(data.id_ktp)
        }

        binding.btnLogout.setOnClickListener {
             // must run in lifecycle only 1 time when inserted into viewLifecycleOwner he can logout when we go to diff menu and come back
            stateUser = true
            userViewModel.logoutDeleteDataStore()
            findNavController().navigate(R.id.action_nav_profile_to_onBoardingFragment)
        }

        binding.btnPurchaseHistory.setOnClickListener {
            findNavController().navigate(R.id.action_nav_profile_to_historyBuyFragment)
        }
        binding.btnPurchaseBuyerHistory.setOnClickListener {
            findNavController().navigate(R.id.action_nav_profile_to_historyPurchaseBuyerFragment)
        }
        binding.btnSalesHistory.setOnClickListener {
            findNavController().navigate(R.id.action_nav_profile_to_historySellFragment)
        }
    }


    private fun setDataProfile(idKtp: String) {
        userViewModel.loginUser(idKtp).observe(viewLifecycleOwner) {
            when (it) {
                is Response.Loading -> ""
                is Response.Error -> {
                    Toast.makeText(requireActivity(), "${it.error}", Toast.LENGTH_SHORT).show()
                }
                is Response.Success -> {
                    val data = it.data

                    if (data != null) {
                        binding.apply {
                            civImage.borderColor = resources.getColor(R.color.green700)
                            Glide.with(requireActivity())
                                .load(data.profil)
                                .into(civImage)

                            tvName.text = data.nama
                            tvEmail.text = data.email
                            tvPhoneNumber.text = data.telepon
                            tvAddress.text = data.alamatRegist
                        }
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onDestroy() {
        super.onDestroy()
        if (stateUser) {
            stateUser = false
            Log.d("PROFILE", "onDestroy: PROFILE logout")
        }
    }

    companion object {
    }
}