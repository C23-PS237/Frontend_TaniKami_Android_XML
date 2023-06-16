package com.bangkit.tanikami_xml.ui.user.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bangkit.tanikami_xml.R
import com.bangkit.tanikami_xml.data.data_store.UserModel
import com.bangkit.tanikami_xml.data.helper.Response
import com.bangkit.tanikami_xml.databinding.FragmentLoginBinding
import com.bangkit.tanikami_xml.ui.user.UserViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private val userViewModel by viewModels<UserViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setDataIfNotNull()

        binding.apply {

            btnSignIn.setOnClickListener {
                val id_ktp = idKtpeditTextLogin.text.toString()
                val email = emailEditTextLogin.text.toString()
                val password = passwordEditTextLogin.text.toString()

                if (id_ktp == "") {
                    idktpLayoutLogin.error = getString(R.string.nik_can_t_empty)
                } else {
                    idktpLayoutLogin.error = ""
                }

                if (email == "") {
                    emailEditTextLayout.error = getString(R.string.email_can_t_empty)
                } else {
                    emailEditTextLayout.error = ""
                }

                if (password == "") {
                    passwordEditTextLayout.error = getString(R.string.password_can_t_empty)
                } else {
                    passwordEditTextLayout.error = ""
                }

                if (password !== null && email != null && id_ktp != null) {
                    loginNow(id_ktp, email, password)
                }

                //findNavController().navigate(R.id.action_loginFragment_to_nav_home)
            }
        }

        binding.toSignUp.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
    }

    private fun setDataIfNotNull() {
        userViewModel.getDataFromDataStore().observe(viewLifecycleOwner) {
            if (it != null) {
                binding.apply {
                    idKtpeditTextLogin.setText(it.id_ktp)
                    emailEditTextLogin.setText(it.email)
                    passwordEditTextLogin.setText(it.password)
                }
            }
        }
    }

    private fun loginNow(idKtp: String, email: String, password: String) {
        userViewModel.loginUser(idKtp).observe(requireActivity()) {
            when (it) {
                is Response.Loading -> ""
                is Response.Error -> {
                    Snackbar.make(requireActivity().findViewById(android.R.id.content), it.error, Snackbar.LENGTH_LONG).show()
                }
                is Response.Success -> {
                    val data = it.data
                    if (data != null) {
                        if (data.email == email && data.password == password) {
                            userViewModel.saveUserToDataStore(UserModel(data.idKtp, data.nama, data.telepon, data.alamatRegist, data.profil, email, password, true))
                            findNavController().navigate(R.id.action_loginFragment_to_nav_home)
                            Snackbar.make(requireActivity().findViewById(android.R.id.content), getString(R.string.successfully_login), Snackbar.LENGTH_LONG).show()
                        } else {
                            Snackbar.make(requireActivity().findViewById(android.R.id.content), getString(R.string.password_or_email_wrong), Snackbar.LENGTH_LONG).show()
                        }
                    }
                }
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