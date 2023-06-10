package com.bangkit.tanikami_xml.ui.article

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.bangkit.tanikami_xml.data.helper.Response
import com.bangkit.tanikami_xml.data.remote.response.PayloadItem
import com.bangkit.tanikami_xml.data.repository.ArticleRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ArticleViewModel @Inject constructor(
    private val articleRepo: ArticleRepository
): ViewModel(){

    fun getArticle() : LiveData<Response<List<PayloadItem>>> {
        return articleRepo.getAllArticle()
    }

    fun getDetailArticle(id_artikel: String): LiveData<Response<PayloadItem>> {
        return articleRepo.getDetailArticle(id_artikel)
    }
}