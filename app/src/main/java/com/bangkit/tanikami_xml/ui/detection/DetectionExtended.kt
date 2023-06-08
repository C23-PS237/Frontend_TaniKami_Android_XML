package com.bangkit.tanikami_xml.ui.detection

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.ThumbnailUtils
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import com.bangkit.tanikami_xml.R
import com.bangkit.tanikami_xml.databinding.FragmentDetectionExtendedBinding
import com.bangkit.tanikami_xml.ml.Model2
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import java.io.File
import java.nio.ByteBuffer
import java.nio.ByteOrder

class DetectionExtended : Fragment() {
    private var _binding: FragmentDetectionExtendedBinding? = null
    private val binding get() = _binding!!

    private val imageSize = 224
    private var reSizedImage: Bitmap? = null

    private lateinit var currentPhotoPath: String
    private var getFile: File? = null
    private val launcherIntentCamera = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == Activity.RESULT_OK) {
            val myFile =  it.data?.extras?.get("data") as Bitmap //File(currentPhotoPath)

//            myFile.let { file ->
//                getFile = file
//                val image: Bitmap = BitmapFactory.decodeFile(file.path)
//                val dimension: Int = image.width.coerceAtMost(image.height)
//                val imageOut = ThumbnailUtils.extractThumbnail(image, dimension, dimension)
//
//                binding.ivDetection.setImageBitmap(imageOut)
//
//                reSizedImage = Bitmap.createScaledBitmap(imageOut, imageSize, imageSize, false)
//            }

            //getFile = myFile
            val image: Bitmap = myFile
            val dimension: Int = image.width.coerceAtMost(image.height)
            val imageOut = ThumbnailUtils.extractThumbnail(image, dimension, dimension)

            binding.ivDetection.setImageBitmap(imageOut)

            reSizedImage = Bitmap.createScaledBitmap(imageOut, imageSize, imageSize, false)

        }
    }

    private val launcherIntentGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val selectedImage = result.data?.data as Uri
            Log.e("selectedImage", "selectedImage: $selectedImage")
            selectedImage.let { uri ->
                Log.e("selectedImage", "selectedImage: $uri")
                val myFile = imagePathProcessor(uri)
                val image: Bitmap = BitmapFactory.decodeFile(myFile)
                binding.ivDetection.setImageBitmap(image)

                reSizedImage = Bitmap.createScaledBitmap(image, imageSize, imageSize, false)
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
        _binding = FragmentDetectionExtendedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            btnCamera.setOnClickListener { startTakePicture() }
            btnGallery.setOnClickListener { startPicPicture() }
            btnSubmit.setOnClickListener { classifyPic(reSizedImage) }
        }


    }

    private fun classifyPic(image: Bitmap?) {
        if (image != null) {
            val model = Model2.newInstance(requireActivity().applicationContext)

            // Creates inputs for reference.
            val inputFeature0 = TensorBuffer.createFixedSize(intArrayOf(1, 224, 224, 3), DataType.FLOAT32)
            val byteBuffer: ByteBuffer = ByteBuffer.allocateDirect(4 * imageSize * imageSize * 3)
            byteBuffer.order(ByteOrder.nativeOrder())

            val intValues = IntArray(imageSize * imageSize)
            image.getPixels(intValues, 0, image.width, 0, 0, image.width, image.height)
            var pixel = 0
            for (i in 0 until imageSize) {
                for (j in 0 until imageSize) {
                    val value = intValues[pixel++] // RGB
                    byteBuffer.putFloat(((value shr 16) and  0xFF) * (1f / 255))
                    byteBuffer.putFloat(((value shr  8) and  0xFF) * (1f / 255))
                    byteBuffer.putFloat((value and 0xFF) * (1f / 255))
                }
            }

            inputFeature0.loadBuffer(byteBuffer)

            // Runs model inference and gets result.
            val outputs = model.process(inputFeature0)
            val outputFeature0 = outputs.outputFeature0AsTensorBuffer

            val confidence = outputFeature0.floatArray
            var maxPos = 0
            var maxConfidence = 0f
            var maxPos2 = 0
            var maxConfidence2 = 0f
            var maxPos3 = 0
            var maxConfidence3 = 0f

            for (i in confidence.indices) {
                if (confidence[i] > maxConfidence) {
                    maxConfidence = confidence[i]
                    maxPos = i
                }
            }
            for (i in confidence.indices) {
                if (confidence[i] > maxConfidence && confidence[i] < maxConfidence) {
                    maxConfidence2 = confidence[i]
                    maxPos2 = i
                }
            }
            for (i in confidence.indices) {
                if (confidence[i] > maxConfidence3 && confidence[i] < maxConfidence2) {
                    maxConfidence3 = confidence[i]
                    maxPos3 = i
                }
            }

            val classes = resources.getStringArray(R.array.label)
            binding.result.text = getString(R.string.classified_as, classes[maxPos], maxConfidence, classes[maxPos2], maxConfidence2, classes[maxPos3], maxConfidence3)

            // Releases model resources if no longer used.
            model.close()
        }
    }

    private fun startPicPicture() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        intent.type = "image/*"
        val chooser = Intent.createChooser(intent, getString(R.string.pick_one_picture))
        launcherIntentGallery.launch(chooser)
    }

    private fun startTakePicture() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        intent.resolveActivity(requireActivity().packageManager)

        launcherIntentCamera.launch(intent)

//        createTemporaryFile(requireActivity()).also {
//            val photoUri: Uri = FileProvider.getUriForFile(requireActivity(), "com.bangkit.tanikami_xml", it)
//            currentPhotoPath = it.absolutePath
//            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri)
//        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {

    }
}