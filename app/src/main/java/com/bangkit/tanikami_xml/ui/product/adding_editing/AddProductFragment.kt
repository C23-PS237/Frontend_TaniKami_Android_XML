package com.bangkit.tanikami_xml.ui.product.adding_editing

//import android.Manifest
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bangkit.tanikami_xml.R
import com.bangkit.tanikami_xml.createCustomTempFile
import com.bangkit.tanikami_xml.data.helper.Response
import com.bangkit.tanikami_xml.databinding.FragmentAddProductBinding
import com.bangkit.tanikami_xml.reduceFileImage
import com.bangkit.tanikami_xml.uriToFile
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
//import java.time.Instant
//import java.time.ZoneOffset
//import java.time.format.DateTimeFormatter

@AndroidEntryPoint
class AddProductFragment : Fragment() {
    private var _binding: FragmentAddProductBinding? = null
    private val binding get() = _binding!!
    private var getFile: File? = null
    private lateinit var currentPhotoPath: String
    private val addEditProductViewModel by viewModels<AddEditProductViewModel>()

//    override fun onRequestPermissionsResult(
//        requestCode: Int,
//        permissions: Array<String>,
//        grantResults: IntArray
//    ) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//        if (requestCode == REQUEST_CODE_PERMISSIONS) {
//            if (!allPermissionsGranted()) {
//                Toast.makeText(
//                    requireActivity(),
//                    "Tidak mendapatkan permission.",
//                    Toast.LENGTH_SHORT
//                ).show()
//                activity?.finish()
//            }
//        }
//    }

//    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
//        ContextCompat.checkSelfPermission(requireContext(), it) == PackageManager.PERMISSION_GRANTED
//    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentAddProductBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        if (!allPermissionsGranted()) {
//            ActivityCompat.requestPermissions(
//                requireActivity(),
//                REQUIRED_PERMISSIONS,
//                REQUEST_CODE_PERMISSIONS
//            )
//        }

        binding.btnCameraAddProduct.setOnClickListener {
            startTakePhoto()
        }
        binding.btnGallery.setOnClickListener {
            startGallery()
        }
        binding.btnDone.setOnClickListener {
            uploadProduct()
            //findNavController().navigate(R.id.action_addProductFragment_to_confirmAddProductFragment)
        }

    }

    private fun uploadProduct(){
        if (getFile != null) {
            //val token = it.data.loginResult.token

            //val idProduk = id+1
            val besaran_stok = binding.stockSizeEditText.text.toString().toRequestBody("text/plain".toMediaType())
            val nama_produk = binding.productsNameSellEditText.text.toString().toRequestBody("text/plain".toMediaType())
            val harga = binding.priceSellEditText.text.toString().toRequestBody("text/plain".toMediaType())
            val rek_penjual = binding.bankNumberEditText.text.toString().toRequestBody("text/plain".toMediaType())
            val stok = binding.stocksEditText.text.toString().toRequestBody("text/plain".toMediaType())
            //val id_ktp = binding.IdKTPEditText.text.toString()
            val nama_bank = binding.bankNameEditText.text.toString().toRequestBody("text/plain".toMediaType())
            val deskripsi_produk = binding.descriptionSellEditText.text.toString().toRequestBody("text/plain".toMediaType())
//            val timestamp = DateTimeFormatter
//                .ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSS")
//                .withZone(ZoneOffset.UTC)
//                .format(Instant.now())
            val file = reduceFileImage(getFile as File)
            val requestImageFile = file.asRequestBody("image/jpeg".toMediaType())
            val gambar_produk: MultipartBody.Part = MultipartBody.Part.createFormData(
                "gambar_produk",
                file.name,
                requestImageFile
            )
            addEditProductViewModel.getIdKtp().observe(viewLifecycleOwner) {
                addEditProductViewModel.sellProduct(
                    //idProduk,
                    besaran_stok,
                    nama_produk,
                    harga,
                    gambar_produk,
                    rek_penjual,
                    it.toRequestBody("text/plain".toMediaType()),
                    stok,
                    deskripsi_produk,
                    nama_bank
                    //timestamp
                ).observe(viewLifecycleOwner) {
                    when (it) {
                        is Response.Loading -> ""
                        is Response.Error -> {
                            Toast.makeText(
                                requireActivity(),
                                "Upload Produk Gagal",
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                        is Response.Success -> {
                            Toast.makeText(
                                requireActivity(),
                                "Berhasil upload produk untuk dijual",
                                Toast.LENGTH_SHORT
                            ).show()
//                            findNavController().navigate(R.id.action_addProductFragment_to_confirmAddProductFragment)
                              findNavController().navigate(R.id.action_addProductFragment_to_nav_home)
//                            val toConfirmAddProductFragment = AddProductFragmentDirections.actionAddProductFragmentToConfirmAddProductFragment()
//                            toConfirmAddProductFragment.idProduct = ProductItem.
//                            findNavController().navigate(toConfirmAddProductFragment)

                        }
                    }
                }
            }
        }
    }
    private fun startTakePhoto() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        intent.resolveActivity(requireActivity().packageManager)

        createCustomTempFile(requireContext()).also {
            val photoURI: Uri = FileProvider.getUriForFile(
                requireActivity(),
                "com.bangkit.tanikami_xml",
                it
            )
            currentPhotoPath = it.absolutePath
            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
            launcherIntentCamera.launch(intent)
        }
    }

    private val launcherIntentCamera = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == AppCompatActivity.RESULT_OK) {
            val myFile = File(currentPhotoPath)
            myFile.let { file ->
//              Silakan gunakan kode ini jika mengalami perubahan rotasi
//              rotateFile(file)
                getFile = file
                binding.ivImageAddProduct.setImageBitmap(BitmapFactory.decodeFile(file.path))
            }
        }
    }
    private fun startGallery() {
        val intent = Intent()
        intent.action = Intent.ACTION_GET_CONTENT
        intent.type = "image/*"
        val chooser = Intent.createChooser(intent, "Choose a Picture")
        launcherIntentGallery.launch(chooser)
    }


    private val launcherIntentGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == AppCompatActivity.RESULT_OK) {
            val selectedImg = result.data?.data as Uri
            selectedImg.let {  uri ->
                val myFile = uriToFile(uri, requireActivity())
                getFile = myFile
                binding.ivImageAddProduct.setImageURI(uri)
            }
        }
    }
    companion object {
//        const val CAMERA_X_RESULT = 200
//
//        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
//        private const val REQUEST_CODE_PERMISSIONS = 10
    }
}