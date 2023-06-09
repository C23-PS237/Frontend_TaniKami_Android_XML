package com.bangkit.tanikami_xml.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.text.NumberFormat
import java.util.Currency
import java.util.Locale
import kotlin.text.StringBuilder

object Formatted {
    fun formatIDRCurrency(price: Int): String {
        val format = NumberFormat.getCurrencyInstance(Locale("id", "ID"))
        format.currency = Currency.getInstance("IDR")

        return addSpaceToIDR(format.format(price))
    }

    private fun addSpaceToIDR(value: String): String = StringBuilder(value).insert(2, ". ").toString()


    fun compressImage(file: File): File {
        val bitmap = BitmapFactory.decodeFile(file.path)
        var compressQuality = 100
        var streamLength: Int

        do {
            val bmpStream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, compressQuality, bmpStream)
            val bmpPickByteArray = bmpStream.toByteArray()
            streamLength = bmpPickByteArray.size
            compressQuality -= -5
        } while (streamLength > 1000000)
        bitmap.compress(Bitmap.CompressFormat.JPEG, compressQuality, FileOutputStream(file))
        return file
    }
}