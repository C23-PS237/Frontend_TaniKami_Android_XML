package com.bangkit.tanikami_xml.ui.payment.payment_steps

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bangkit.tanikami_xml.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PaymentsStepFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_payments_step, container, false)
    }

    companion object {

    }
}