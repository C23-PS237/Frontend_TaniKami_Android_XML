package com.bangkit.tanikami_xml.ui.history.buying_history

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.tanikami_xml.R
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
            tvTotalPayment.text = Formatted.formatIDRCurrency(itemHistory.biayaTotal)
            tvTotalBuy.text = holder.itemView.context.getString(R.string.total_buy, itemHistory.jumlahDibeli, itemProduct.besaran_stok)
            if (itemHistory.statusPembayaran == 1) {
                btnPaymentStatus.setBackgroundColor(holder.itemView.context.getColor(R.color.green500))
                btnPaymentStatus.text = holder.itemView.context.getString(R.string.status_purcase)
            } else {
                btnPaymentStatus.setBackgroundColor(holder.itemView.context.getColor(R.color.red_warning))
                btnPaymentStatus.text = holder.itemView.context.getString(R.string.status_purcase2)
            }

            if (itemHistory.statusPengiriman == 1) {
                btnArrivedStatus.setBackgroundColor(holder.itemView.context.getColor(R.color.green500))
                btnArrivedStatus.text = holder.itemView.context.getString(R.string.status_purchase3)
                btnReceived.isEnabled = false
                btnReceived.text = holder.itemView.context.getString(R.string.received)
            } else {
                btnArrivedStatus.setBackgroundColor(holder.itemView.context.getColor(R.color.red_warning))
                btnArrivedStatus.text = holder.itemView.context.getString(R.string.status_purchase4)
                btnReceived.isEnabled = true
                btnReceived.text = holder.itemView.context.getString(R.string.received)
                btnReceived.setBackgroundColor(holder.itemView.context.getColor(R.color.red_warning))
            }


            holder.itemView.setOnClickListener {
                onItemClickCallback.onHistoryBuyClicked(listHistory[holder.bindingAdapterPosition])
            }
        }
    }

    interface OnItemClickCallback {
        fun onHistoryBuyClicked(data: BuyProduct)
    }
}