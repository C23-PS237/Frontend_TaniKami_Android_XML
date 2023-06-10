package com.bangkit.tanikami_xml.ui.user.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.bangkit.tanikami_xml.R
import com.bangkit.tanikami_xml.databinding.FragmentLoginBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

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

        binding.apply {
            val id_ktp = idKtpeditTextLogin.text.toString()
            val email = emailEditTextLogin.text.toString()
            val password = passwordEditTextLogin.toString()

            btnSignIn.setOnClickListener {
                if (id_ktp == "") {
                    idktpLayoutLogin.error = "NIK tidak boleh kosong"
                } else {
                    idktpLayoutLogin.error = ""
                }

                if (email == "") {
                    emailEditTextLayout.error = "Email tidak boleh kosong"
                } else {
                    emailEditTextLayout.error = ""
                }

                if (password == "") {
                    passwordEditTextLayout.error = "Password tidak boleh kosong"
                } else {
                    passwordEditTextLayout.error = ""
                }

                findNavController().navigate(R.id.action_loginFragment_to_nav_home)
            }
        }

        binding.toSignUp.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
    }
}