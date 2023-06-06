package com.bangkit.tanikami_xml.ui.article.article_start

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.tanikami_xml.data.remote.response.PayloadItem
import com.bangkit.tanikami_xml.databinding.ItemArticleBinding
import com.bumptech.glide.Glide
import javax.inject.Inject

class ArticleAdapter @Inject constructor(private val listArticle: List<PayloadItem>): RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder>(){

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    inner class ArticleViewHolder(var binding: ItemArticleBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        return ArticleViewHolder(ItemArticleBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount() = listArticle.size

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val item = listArticle[position]

        holder.binding.apply {
            Glide.with(holder.itemView.context)
                .load(item.gambarArtikel)
                .into(ivArticle)

            tvArticleAuthor.text = item.author
            tvArticleTitle.text = item.judul
            tvArticleBody.text = item.body

            holder.itemView.setOnClickListener {
                onItemClickCallback.onArticleClicked(listArticle[holder.bindingAdapterPosition])
            }
        }
    }

    interface OnItemClickCallback {
        fun onArticleClicked(data: PayloadItem)
    }
}