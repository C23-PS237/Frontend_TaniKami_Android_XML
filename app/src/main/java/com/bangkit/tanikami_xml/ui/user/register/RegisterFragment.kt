package com.bangkit.tanikami_xml.ui.user.register

import android.app.AlertDialog
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bangkit.tanikami_xml.R
import com.bangkit.tanikami_xml.createCustomTempFile
import com.bangkit.tanikami_xml.data.helper.Response
import com.bangkit.tanikami_xml.databinding.FragmentRegisterBinding
import com.bangkit.tanikami_xml.ui.user.UserViewModel
import com.bangkit.tanikami_xml.uriToFile
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

@AndroidEntryPoint
class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!
    private val userViewModel by viewModels<UserViewModel>()

    private var getFile: File? = null
    private lateinit var photoPath: String


    private val launchIntentCamera = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == AppCompatActivity.RESULT_OK) {
            val myFile = File(photoPath)
            myFile.let { result ->
                getFile = result
                binding.tvProfileImage.setImageBitmap(BitmapFactory.decodeFile(result.path))
            }
        }
    }

    private val launchIntentGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == AppCompatActivity.RESULT_OK) {
            val selectedImage = result.data?.data as Uri
            selectedImage.let { uri ->
                val myFile = uriToFile(uri, requireActivity())
                getFile = myFile

                binding.tvProfileImage.setImageURI(uri)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun takePicture() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        intent.resolveActivity(requireActivity().packageManager)

        createCustomTempFile(requireContext()).also {
            val photoUri: Uri = FileProvider.getUriForFile(
                requireActivity(),
                "com.bangkit.tanikami_xml",
                it
            )
            photoPath = it.absolutePath
            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri)

            launchIntentCamera.launch(intent)
        }
    }

    private fun pickPictureFromGallery() {
        val intent = Intent()
        intent.action = Intent.ACTION_GET_CONTENT
        intent.type = "image/*"
        val chooser = Intent.createChooser(intent, getString(R.string.pick_one_picture))
        launchIntentGallery.launch(chooser)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setGender()

        binding.apply {
            ibCamera.setOnClickListener{ takePicture() }
            btnGallery.setOnClickListener { pickPictureFromGallery() }

            btnSignUp.setOnClickListener {
                if (getFile != null) {
                    registerNewUser()
                }  else {
                    Toast.makeText(
                        requireActivity(),
                        getString(R.string.warning_register),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

        binding.toSignIn.setOnClickListener {
            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }

    }

    private fun registerNewUser() {
        setComponentDisandAv(false)

        binding.apply {
            val imageProfil = getFile as File
            val name = nameEditTextRegister.text.toString().toRequestBody("text/plain".toMediaType())
            val id_ktp = idKtpEditText.text.toString().toRequestBody("text/plain".toMediaType())
            val email = emailEditText.text.toString().toRequestBody("text/plain".toMediaType())
            val password = passwordEditText.text.toString().toRequestBody("text/plain".toMediaType())
            val alamat_regist = addressEditText.text.toString().toRequestBody("text/plain".toMediaType())
            val telepon = phoneEditText.text.toString().toRequestBody("text/plain".toMediaType())
            val temp = if (genderAutoComplete.text.toString() == "Male") "1" else "0"
              val gender = temp.toRequestBody("text/plain".toMediaType())
            val usia = ageEditText.text.toString().toRequestBody("text/plain".toMediaType())

            userViewModel.registerNewUser(
                imageProfil,
                id_ktp,
                name,
                email,
                password,
                telepon,
                alamat_regist,
                gender,
                usia,
                "1".toRequestBody("text/plain".toMediaType())
            ).observe(requireActivity()) {
                when (it) {
                    is Response.Loading -> {
                        setComponentDisandAv(false)
                    }

                    is Response.Error -> {
                        setComponentDisandAv(true)
                        Snackbar.make(
                            binding.root,
                            getString(R.string.error_warning_register),
                            Snackbar.LENGTH_SHORT
                        ).show()
                    }

                    is Response.Success -> {
                        val response = it.data.payload.isSuccess
                        if (response == 1) {
                            AlertDialog.Builder(requireActivity()).apply {
                                setTitle(getString(R.string.register_user_success))
                                setMessage(getString(R.string.msg_register_success))
                                setPositiveButton("OK") { _, _ ->
                                    findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
                                }
                                create()
                                show()
                            }
                        }
                    }
                }
            }
        }
    }


    private fun setComponentDisandAv(state: Boolean) {
        binding.apply {
            ibCamera.isEnabled = state
            btnGallery.isEnabled = state
            idKtpLayout.isEnabled = state
            nameEditTextLayout.isEnabled = state
            emailEditTextLayout.isEnabled = state
            passwordEditTextLayout.isEnabled = state
            addressEditTextLayout.isEnabled = state
            phoneEditTextLayout.isEnabled = state
            genderDropdown.isEnabled = state
            ageEditTextLayout.isEnabled = state
            btnSignUp.isEnabled = state
        }
    }

    private fun setGender() {
        // set up dropdown for gender
        val genders = resources.getStringArray(R.array.gender)
        val adapter = ArrayAdapter(requireContext(), R.layout.dropdown_register, genders)
        binding.genderAutoComplete.setAdapter(adapter)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        private const val TAG = "RegisterFragment"
        private const val CAMERA_PERMISSION_CODE = 1
        private const val GALLERY_PERMISSION_CODE = 2
    }
}