package com.bangkit.tanikami_xml.ui.history.selling_history

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.tanikami_xml.data.remote.response.ProductItem
import com.bangkit.tanikami_xml.databinding.ItemSalesHistoryBinding
import com.bangkit.tanikami_xml.utils.Formatted
import com.bumptech.glide.Glide
import javax.inject.Inject


class HistorySelllAdapter @Inject constructor(private val listHistory: List<ProductItem>): RecyclerView.Adapter<HistorySelllAdapter.ProductViewHolder>(){

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
                .load(item.url_gambar)
                .into(ivProductImage)

            tvProductName.text = item.nama_produk
            tvProductDescription.text = item.deskripsi_produk
            tvTotalPayment.text = Formatted.formatIDRCurrency(item.harga)

            holder.itemView.setOnClickListener {
                onItemClickCallback.onHistoryClicked(listHistory[holder.bindingAdapterPosition])
            }
        }
    }

    interface OnItemClickCallback {
        fun onHistoryClicked(data: ProductItem)
    }
}