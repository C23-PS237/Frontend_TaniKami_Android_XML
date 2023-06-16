package com.bangkit.tanikami_xml.ui.article.article_start

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.tanikami_xml.R
import com.bangkit.tanikami_xml.data.helper.Response
import com.bangkit.tanikami_xml.data.remote.response.PayloadItem
import com.bangkit.tanikami_xml.databinding.FragmentArticleBinding
import com.bangkit.tanikami_xml.ui.article.ArticleViewModel
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
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentArticleBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            activity?.finish()
        }

        showListArticles()
    }

    private fun setLoading(state: Boolean) {
        binding.progressBar.visibility = if (state) View.VISIBLE else View.GONE
    }

    private fun showListArticles() {
        binding.apply {
            rvArticle.layoutManager = LinearLayoutManager(requireActivity())
            articleViewModel.getArticle().observe(requireActivity()) {
                when (it) {
                    is Response.Loading -> setLoading(true)
                    is Response.Error -> {
                        setLoading(true)
                        Snackbar.make(
                            binding.root,
                            getString(R.string.warning_connection),
                            Snackbar.LENGTH_SHORT
                        ).show()
                    }

                    is Response.Success -> {
                        setLoading(false)
                        val list = it.data
                        val listData = ArticleAdapter(list)
                        rvArticle.adapter = listData

                        listData.setOnItemClickCallback(object :
                            ArticleAdapter.OnItemClickCallback {
                            override fun onArticleClicked(data: PayloadItem) {
                                val toDetailArticleFragment = ArticleFragmentDirections.actionNavArticleToDetailArticleFragment()
                                toDetailArticleFragment.idArtikel = data.idArtikel
                                findNavController().navigate(toDetailArticleFragment)
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