package com.bangkit.tanikami_xml.ui.history.selling_history

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.tanikami_xml.data.remote.response.ProductItem
import com.bangkit.tanikami_xml.databinding.ItemSalesHistoryBinding
import com.bangkit.tanikami_xml.utils.Formatted
import com.bumptech.glide.Glide
import javax.inject.Inject


class HistorySellAdapter @Inject constructor(private val listHistory: List<ProductItem>): RecyclerView.Adapter<HistorySellAdapter.ProductViewHolder>(){

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    inner class ProductViewHolder(var binding: ItemSalesHistoryBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        return ProductViewHolder(ItemSalesHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount() = listHistory.size

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val item = listHistory[position]

        holder.binding.apply {
            Glide.with(holder.itemView.context)
                .load(item.gambar_produk)
                .into(ivProductImage)

            tvProductName.text = item.nama_produk
            tvProductDescription.text = item.deskripsi_produk
            tvTotalPayment.text = Formatted.formatIDRCurrency(item.harga)
            tvTimestamp.text = item.timestamp
            holder.itemView.setOnClickListener {
                onItemClickCallback.onHistorySellClicked(listHistory[holder.bindingAdapterPosition])
            }
        }
    }

    interface OnItemClickCallback {
        fun onHistorySellClicked(data: ProductItem)
    }
}