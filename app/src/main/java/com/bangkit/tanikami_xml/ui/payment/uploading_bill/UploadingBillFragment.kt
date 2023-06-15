package com.bangkit.tanikami_xml.ui.payment.uploading_bill

import android.app.AlertDialog
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
import com.bangkit.tanikami_xml.databinding.FragmentUploadingBillBinding
import com.bangkit.tanikami_xml.ui.payment.PaymentViewModel
import com.bangkit.tanikami_xml.uriToFile
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File


@AndroidEntryPoint
class UploadingBillFragment : Fragment() {
    private var _binding: FragmentUploadingBillBinding?=null
    private val binding get() = _binding!!
    private val paymentViewModel by viewModels<PaymentViewModel>()

    private var getFile: File? = null
    private lateinit var currentPhotoPath: String

    private val launcherIntentCamera = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == AppCompatActivity.RESULT_OK) {
            val myFile = File(currentPhotoPath)
            myFile.let { file ->
                getFile = file

                binding.ivBill.setImageBitmap(BitmapFactory.decodeFile(file.path))
            }
        }
    }

    private val launcherIntentGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == AppCompatActivity.RESULT_OK) {
            val selectedImg = result.data?.data as Uri
            selectedImg.let {  uri ->
                val myFile = uriToFile(uri, requireActivity())
                getFile = myFile

                binding.ivBill.setImageURI(uri)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentUploadingBillBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val id_transaksi = UploadingBillFragmentArgs.fromBundle(arguments as Bundle).idTransaksi

        binding.btnCamera.setOnClickListener { startTakePhoto() }
        binding.btnGallery.setOnClickListener { startGallery() }

        binding.btnSubmit.setOnClickListener {
            if (getFile != null) {
                updateValidData(id_transaksi)
            } else {
                Toast.makeText(
                    requireActivity(),
                    getString(R.string.choose_one_bill_image_first),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun updateValidData(id_transaksi: Int) {

        paymentViewModel.getDataPembelianByIdTransaksi(id_transaksi).observe(viewLifecycleOwner) { data ->
            when (data) {
                is Response.Loading -> ""
                is Response.Error -> ""
                is Response.Success -> {
                    val data_used = data.data.payload

                    paymentViewModel.updatePembelian(
                        data_used.idTransaksi,
                        getFile!!,
                        data_used.idKtp.toRequestBody("text/plain".toMediaTypeOrNull()),
                        data_used.idProduk.toString().toRequestBody("text/plain".toMediaTypeOrNull()),
                        data_used.alamatPenerima.toRequestBody("text/plain".toMediaTypeOrNull()),
                        data_used.harga.toString().toRequestBody("text/plain".toMediaTypeOrNull()),
                        data_used.jumlahDibeli.toString().toRequestBody("text/plain".toMediaTypeOrNull()),
                        data_used.biayaPengiriman.toString().toRequestBody("text/plain".toMediaTypeOrNull()),
                        data_used.pajak.toString().toRequestBody("text/plain".toMediaTypeOrNull()),
                        data_used.biayaAdmin.toString().toRequestBody("text/plain".toMediaTypeOrNull()),
                        data_used.biayaTotal.toString().toRequestBody("text/plain".toMediaTypeOrNull()),
                        "1".toRequestBody("text/plain".toMediaTypeOrNull()),
                        "1".toRequestBody("text/plain".toMediaTypeOrNull()),
                        data_used.idPenjual.toRequestBody("text/plain".toMediaTypeOrNull())
                    ).observe(viewLifecycleOwner) { response_update ->
                        when (response_update) {
                            is Response.Loading -> ""
                            is Response.Error -> ""
                            is Response.Success -> {
                                if (response_update.data.payload.isSuccess == 1) {
                                    AlertDialog.Builder(requireActivity()).apply {
                                        setTitle("Bill Successfully Uploaded")
                                        setMessage("Your transfer bill uploaded, data being updated")
                                        setPositiveButton("OK") { _, _ ->
                                            findNavController().navigate(R.id.action_uploadingBillFragment_to_historyBuyFragment)
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

    private fun startGallery() {
        val intent = Intent()
        intent.action = Intent.ACTION_GET_CONTENT
        intent.type = "image/*"
        val chooser = Intent.createChooser(intent, "Choose a Picture")
        launcherIntentGallery.launch(chooser)
    }

    companion object {

    }

}