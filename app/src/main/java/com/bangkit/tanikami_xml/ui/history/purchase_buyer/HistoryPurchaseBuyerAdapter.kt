package com.bangkit.tanikami_xml.ui.history.purchase_buyer

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.tanikami_xml.R
import com.bangkit.tanikami_xml.data.remote.response.Product
import com.bangkit.tanikami_xml.data.remote.response.PurchaseBuyer
import com.bangkit.tanikami_xml.databinding.ItemPurchaseBuyerHistoryBinding
import com.bangkit.tanikami_xml.utils.Formatted
import com.bumptech.glide.Glide
import javax.inject.Inject

class HistoryPurchaseBuyerAdapter @Inject constructor(private val listHistory: List<PurchaseBuyer>, private val listProduct: List<Product>): RecyclerView.Adapter<HistoryPurchaseBuyerAdapter.ProductViewHolder>(){

    private lateinit var onItemClickCallback: OnItemClickCallback
    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    inner class ProductViewHolder(var binding: ItemPurchaseBuyerHistoryBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        return ProductViewHolder(ItemPurchaseBuyerHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount() = listHistory.size

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val itemHistory = listHistory[position]
        val itemProduct = listProduct[position]

        holder.binding.apply {
            Glide.with(holder.itemView.context)
                .load(itemProduct.gambar_produk)
                .into(ivProductImage)

            tvProductName.text = itemProduct.nama_produk
            tvOneProductPrice.text = "${itemProduct.harga} / ${itemProduct.besaran_stok}"
            tvProductAmountProductTobuy.text = "${itemHistory.jumlahDibeli.toString()} ${itemProduct.besaran_stok}"
            tvTotalPaymentHistory.setText(R.string.total_payment_history)
            tvValueTotalPaymentHistory.text = Formatted.formatIDRCurrency(itemHistory.biayaTotal)
            tvTimestamp.text = itemHistory.createdAt

            holder.itemView.setOnClickListener {
                onItemClickCallback.onHistoryBuyClicked(listHistory[holder.bindingAdapterPosition])
            }
        }
    }

    interface OnItemClickCallback {
        fun onHistoryBuyClicked(data: PurchaseBuyer)
    }
}