package com.bangkit.tanikami_xml.ui.history.buying_history

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.tanikami_xml.data.remote.response.BuyProduct
import com.bangkit.tanikami_xml.data.remote.response.Product
import com.bangkit.tanikami_xml.databinding.ItemPurchaseHistoryBinding
import com.bangkit.tanikami_xml.utils.Formatted
import com.bumptech.glide.Glide
import javax.inject.Inject

class HistoryBuyAdapter @Inject constructor(private val listHistory: List<BuyProduct>, private val listProduct: List<Product>): RecyclerView.Adapter<HistoryBuyAdapter.ProductViewHolder>(){

    private lateinit var onItemClickCallback: OnItemClickCallback
    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    inner class ProductViewHolder(var binding: ItemPurchaseHistoryBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        return ProductViewHolder(ItemPurchaseHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount() = listHistory.size

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val itemHistory = listHistory[position]
        val itemProduct = listProduct[position]

        holder.binding.apply {
            Glide.with(holder.itemView.context)
                .load(itemProduct.gambar_produk)
                .into(ivPurchaseHistory)

            tvProductName.text = itemProduct.nama_produk
            tvTotalPayment.text = Formatted.formatIDRCurrency(itemHistory.harga)
            tvCreatedAt.text = itemHistory.createdAt
            tvUpdatedAt.text = itemHistory.updatedAt

            holder.itemView.setOnClickListener {
                onItemClickCallback.onHistoryBuyClicked(listHistory[holder.bindingAdapterPosition])
            }
        }
    }

    interface OnItemClickCallback {
        fun onHistoryBuyClicked(data: BuyProduct)
    }
}