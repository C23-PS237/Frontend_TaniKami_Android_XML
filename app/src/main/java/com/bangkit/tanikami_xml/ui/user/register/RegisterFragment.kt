package com.bangkit.tanikami_xml.ui.user.register

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bangkit.tanikami_xml.R
import com.bangkit.tanikami_xml.data.helper.Response
import com.bangkit.tanikami_xml.databinding.FragmentRegisterBinding
import com.bangkit.tanikami_xml.ui.user.UserViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import java.io.File

@AndroidEntryPoint
class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!
    private val userViewModel by viewModels<UserViewModel>()

    private var getFile: File? = null
    private val launchIntentCamera = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == Activity.RESULT_OK) {
            getFile = it.data?.extras?.get("data") as File
            val profilBitmap = it.data?.extras?.get("data") as Bitmap
            binding.tvProfileImage.setImageBitmap(profilBitmap)
        }
    }

    private val launchIntentGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val selectedImage = result.data?.data as Uri
            selectedImage.let { uri ->
                Log.e(TAG, "TESTING IMAGE: $uri")
                val path = imagePathProcessor(uri)
                getFile = File(path!!)

                val bitmapProfile: Bitmap = BitmapFactory.decodeFile(path)
                binding.tvProfileImage.setImageBitmap(bitmapProfile)
            }
        }
    }

    private fun imagePathProcessor(uri: Uri): String? {
        val projection = arrayOf(MediaStore.Images.Media.DATA)
        val cursor = requireActivity().contentResolver.query(uri, projection, null, null, null)
        val imagePath: String?
        if (cursor != null && cursor.moveToFirst()) {
            val columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            imagePath = cursor.getString(columnIndex)
            cursor.close()
        } else {
            imagePath = null
        }
        return imagePath
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

        launchIntentCamera.launch(intent)
    }

    private fun pickPictureFromGallery() {
        val intent = Intent(Intent.ACTION_PICK)
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

//            btnSignUp.setOnClickListener {
//                if (getFile != null) {
//
//                    val imageProfil = getFile as File
//                    val name = nameEditTextRegister.text.toString()
//                    val id_ktp = idKtpEditText.text.toString()
//                    val email = emailEditText.text.toString()
//                    val password = passwordEditText.text.toString()
//                    val alamat_regist = addressEditText.text.toString()
//                    val telepon = phoneEditText.text.toString()
//                    val gender = genderAutoComplete.text.toString() == "Male"
//                    val usia = ageEditText.text.toString()
//
//                    registerNewUser(
//                        imageProfil,
//                        id_ktp,
//                        name,
//                        email,
//                        password,
//                        alamat_regist,
//                        telepon,
//                        gender,
//                        usia.toInt()
//                    )
//                }  else {
//                    Toast.makeText(
//                        requireActivity(),
//                        getString(R.string.warning_register),
//                        Toast.LENGTH_SHORT
//                    ).show()
//                }
//
//            }
            findNavController().navigate(R.id.action_registerFragment_to_nav_home)
        }

        binding.toSignIn.setOnClickListener {
            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }

    }

    private fun registerNewUser(
        imageProfil: File,
        id_ktp: String,
        name: String,
        email: String,
        password: String,
        alamatRegist: String,
        telepon: String,
        gender: Boolean,
        usia: Int,
    ) {
        setComponentDisandAv(false)
        userViewModel.registerNewUser(
            imageProfil,
            id_ktp,
            name,
            email,
            password,
            alamatRegist,
            telepon,
            gender,
            usia,
            false
        ).observe(requireActivity()) {
            when (it) {
                is Response.Loading -> {
                    setComponentDisandAv(false)
                }
                is Response.Error -> {
                    setComponentDisandAv(true)
                    Snackbar.make(
                        binding.root,
                        it.error,
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
                                findNavController().navigate(R.id.action_registerFragment_to_nav_home)
                            }
                            create()
                            show()
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