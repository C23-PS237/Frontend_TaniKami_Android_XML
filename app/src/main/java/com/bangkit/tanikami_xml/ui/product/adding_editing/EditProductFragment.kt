package com.bangkit.tanikami_xml.ui.product.adding_editing

import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bangkit.tanikami_xml.R
import com.bangkit.tanikami_xml.createCustomTempFile
import com.bangkit.tanikami_xml.data.helper.Response
import com.bangkit.tanikami_xml.databinding.FragmentEditProductBinding
import com.bangkit.tanikami_xml.uriToFile
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

@AndroidEntryPoint
class EditProductFragment : Fragment() {
    private var _binding: FragmentEditProductBinding? = null
    private val binding get() = _binding!!
    private val addEditViewModel by viewModels<AddEditProductViewModel>()

    private var getFile: File? = null
    private lateinit var photoPath: String

    private val launchIntentCamera = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == AppCompatActivity.RESULT_OK) {
            val myFile = File(photoPath)
            myFile.let { result ->
                getFile = result
                binding.ivImageEditProduct.setImageBitmap(BitmapFactory.decodeFile(result.path))
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

                binding.ivImageEditProduct.setImageURI(uri)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentEditProductBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val id_product = EditProductFragmentArgs.fromBundle(arguments as Bundle).idProduk

        setDataFromToEdit(id_product)

        binding.apply {
            btnCameraEditProduct.setOnClickListener { takePicture() }
            btnGalleryEdit.setOnClickListener { pickPictureFromGallery() }
            btnDoneEdit.setOnClickListener { editProduct(id_product) }
        }

    }

    private fun editProduct(idProduct: Int) {
        binding.apply {
            val name_product = productsNameSellEditTextEdit.text.toString()
            val bank_name = bankNameEditTextEdit.text.toString()
            val rek_penjual = bankNumberEditTextEdit.text.toString()
            val stocks = stocksEditTextEdit.text.toString()
            val stock_size = stockSizeEditTextEdit.text.toString()
            val price = priceSellEditTextEdit.text.toString()
            val description = descriptionSellEditTextEdit.text.toString()

            if (name_product == "" || bank_name == "" || rek_penjual == "" || stocks == "" || stock_size == "" || price == "" || description == "") {
                productNameSellEditTextLayoutEdit.error = getString(R.string.empty_warning_pname)
                bankNameEditTextLayoutEdit.error = getString(R.string.empty_warning_bname)
                bankNumberEditTextLayoutEdit.error = getString(R.string.empty_warning_bn)
                stocksEditTextLayoutEdit.error = getString(R.string.empty_warning_se)
                stockSizeEditTextLayoutEdit.error = getString(R.string.empty_warningss)
                priceSellEditTextLayoutEdit.error = getString(R.string.empty_warning_ps)
                descriptionSellEditTextLayoutEdit.error = getString(R.string.empty_warning_ds)
            } else {
                productNameSellEditTextLayoutEdit.error = ""
                bankNameEditTextLayoutEdit.error = ""
                bankNumberEditTextLayoutEdit.error = ""
                stocksEditTextLayoutEdit.error = ""
                stockSizeEditTextLayoutEdit.error = ""
                priceSellEditTextLayoutEdit.error = ""
                descriptionSellEditTextLayoutEdit.error = ""
            }

            if (name_product != null && bank_name != null && rek_penjual != null && stocks != null && stock_size != null && price != null && description != null) {
                if (getFile != null) {
                    addEditViewModel.updateProductAllData(
                        idProduct,
                        getFile as File,
                        name_product.toRequestBody("text/plain".toMediaTypeOrNull()),
                        stock_size.toRequestBody("text/plain".toMediaTypeOrNull()),
                        stocks.toRequestBody("text/plain".toMediaTypeOrNull()),
                        price.toRequestBody("text/plain".toMediaTypeOrNull()),
                        description.toRequestBody("text/plain".toMediaTypeOrNull()),
                        bank_name.toRequestBody("text/plain".toMediaTypeOrNull()),
                        rek_penjual.toRequestBody("text/plain".toMediaTypeOrNull())
                        ).observe(viewLifecycleOwner) { response ->
                            when (response) {
                                is Response.Loading -> ""
                                is Response.Error -> ""
                                is Response.Success -> {
                                    if (response.data.payload.isSuccess == 1) {
                                        findNavController().navigate(R.id.action_editProductFragment_to_nav_home)
                                        Toast.makeText(requireActivity(), getString(R.string.edit_data_success), Toast.LENGTH_SHORT).show()
                                    }
                                }
                            }
                    }
                } else {
                    Toast.makeText(requireActivity(), getString(R.string.watning_edit_product), Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun setDataFromToEdit(id_produk: Int) {
        addEditViewModel.getDataByIdProduct(id_produk).observe(viewLifecycleOwner) {
            when (it) {
                is Response.Loading -> ""
                is Response.Error -> ""
                is Response.Success -> {
                    binding.apply {
                        val data = it.data.payload
                        Glide.with(requireContext())
                            .load(data.gambar_produk)
                            .into(ivImageEditProduct)
                        productsNameSellEditTextEdit.setText(data.nama_produk)
                        bankNameEditTextEdit.setText(data.nama_bank)
                        bankNumberEditTextEdit.setText(data.rek_penjual)
                        stocksEditTextEdit.setText(data.stok.toString())
                        stockSizeEditTextEdit.setText(data.besaran_stok)
                        priceSellEditTextEdit.setText(data.harga.toString())
                        descriptionSellEditTextEdit.setText(data.deskripsi_produk)
                    }
                }
            }
        }
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

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {

    }
}