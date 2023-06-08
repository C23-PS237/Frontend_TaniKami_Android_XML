package com.bangkit.tanikami_xml.ui.article.detail_article

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.bangkit.tanikami_xml.data.helper.Response
import com.bangkit.tanikami_xml.databinding.FragmentDetailArticleBinding
import com.bangkit.tanikami_xml.ui.article.ArticleViewModel
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailArticleFragment : Fragment() {

    private var _binding: FragmentDetailArticleBinding? = null
    private val binding get() = _binding!!
    private val articleViewModel by  viewModels<ArticleViewModel>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentDetailArticleBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val idArticle = DetailArticleFragmentArgs.fromBundle(arguments as Bundle).idArtikel

        articleViewModel.getDetailArticle(idArticle).observe(requireActivity()) {
            when (it) {
                is Response.Loading -> ""
                is Response.Success -> {
                    val data = it.data[0]
                    binding.apply {
                        Glide.with(this@DetailArticleFragment)
                            .load(data)
                            .into(ivArticle)

                        tvArticleAuthor.text = data.author
                        tvArticleTitle.text = data.judul
                        tvArticleBody.text = data.body
                    }
                }
                is Response.Error -> {
                    Snackbar.make(
                        binding.root,
                        it.error,
                        Snackbar.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
    }
}