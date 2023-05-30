package com.bangkit.tanikami_xml.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.tanikami_xml.R
import com.bangkit.tanikami_xml.data.model.Product
import com.bangkit.tanikami_xml.databinding.ItemProductsBinding
import com.bangkit.tanikami_xml.utils.Formatted.formatIDRCurrency
import com.bumptech.glide.Glide
import javax.inject.Inject

class HomeProductAdapter @Inject constructor(private val listProduct: List<Product>): RecyclerView.Adapter<HomeProductAdapter.ProductViewHolder>(){

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    inner class ProductViewHolder(var binding: ItemProductsBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        return ProductViewHolder(ItemProductsBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount() = listProduct.size

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val item = listProduct[position]

        holder.binding.apply {
            Glide.with(holder.itemView.context)
                .load(item.url_gambar)
                .into(ivProducts)

            tvProductsName.text = item.nama_product
            tvProductsDescription.text = item.deskripsi_product
            tvPrice.text = formatIDRCurrency(item.harga)

            holder.itemView.setOnClickListener {
                onItemClickCallback.onProductClicked(listProduct[holder.bindingAdapterPosition])
            }
        }
    }

    interface OnItemClickCallback {
        fun onProductClicked(data: Product)
    }
}