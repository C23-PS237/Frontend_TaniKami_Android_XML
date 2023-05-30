package com.bangkit.tanikami_xml.utils

import java.text.NumberFormat
import java.util.Currency
import java.util.Locale

object Formatted {
    fun formatIDRCurrency(price: Int): String {
        val format = NumberFormat.getCurrencyInstance(Locale("id", "ID"))
        format.currency = Currency.getInstance("IDR")

        return format.format(price)
    }
}