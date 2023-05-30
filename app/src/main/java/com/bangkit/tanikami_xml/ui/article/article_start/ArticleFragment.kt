package com.bangkit.tanikami_xml.ui.article.article_start

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.tanikami_xml.R
import com.bangkit.tanikami_xml.data.helper.Response
import com.bangkit.tanikami_xml.data.model.Article
import com.bangkit.tanikami_xml.databinding.FragmentArticleBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ArticleFragment : Fragment() {

    private var _binding: FragmentArticleBinding? = null
    private val binding get() = _binding!!
    private val articleViewModel by viewModels<ArticleViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_article, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showListArticles()
    }

    private fun setLoading(bool: Boolean) {
        binding.progressBar.visibility = if (bool) View.VISIBLE else View.GONE
    }

    private fun showListArticles() {
        binding.apply {
            rvArticle.layoutManager = LinearLayoutManager(requireActivity())
            articleViewModel.getArticle().observe(requireActivity()) {
                when (it) {
                    is Response.Loading -> setLoading(true)
                    is Response.Error -> {
                        Snackbar.make(
                            binding.root,
                            it.error,
                            Snackbar.LENGTH_SHORT
                        ).show()
                    }

                    is Response.Success -> {
                        val listData = ArticleAdapter(it.data)
                        rvArticle.adapter = listData

                        listData.setOnItemClickCallback(object :
                            ArticleAdapter.OnItemClickCallback {
                            override fun onArticleClicked(data: Article) {
                                findNavController().navigate(R.id.action_nav_article_to_detailArticleFragment)
                            }
                        })
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}