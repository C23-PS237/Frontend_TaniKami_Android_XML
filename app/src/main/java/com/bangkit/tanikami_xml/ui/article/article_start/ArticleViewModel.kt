package com.bangkit.tanikami_xml.ui.article.article_start

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.bangkit.tanikami_xml.data.helper.Response
import com.bangkit.tanikami_xml.data.model.Article
import com.bangkit.tanikami_xml.data.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ArticleViewModel @Inject constructor(
    private val articleRepo: ProductRepository
): ViewModel(){

    fun getArticle() : LiveData<Response<List<Article>>> {
        return articleRepo.getArticle()
    }
}