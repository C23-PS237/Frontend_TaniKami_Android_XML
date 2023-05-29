package com.bangkit.tanikami_xml.ui.product.confirm_add

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bangkit.tanikami_xml.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ConfirmAddProductFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_confirm_add_product, container, false)
    }

    companion object {
    }
}