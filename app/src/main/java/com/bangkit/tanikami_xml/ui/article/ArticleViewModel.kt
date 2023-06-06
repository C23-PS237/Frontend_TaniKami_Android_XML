package com.bangkit.tanikami_xml.ui.article

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.bangkit.tanikami_xml.data.helper.Response
import com.bangkit.tanikami_xml.data.remote.response.ArticleResponseItem
import com.bangkit.tanikami_xml.data.repository.ArticleRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ArticleViewModel @Inject constructor(
    private val articleRepo: ArticleRepository
): ViewModel(){

    fun getArticle() : LiveData<Response<List<ArticleResponseItem>>> {
        return articleRepo.getAllArticle()
    }

//    fun getDetailArtikel(id_artikel: Int): LiveData<Response<Article>> {
//        return articleRepo.getDetailArticle(id_artikel)
//    }
}