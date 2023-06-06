package com.bangkit.tanikami_xml.utils

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
}