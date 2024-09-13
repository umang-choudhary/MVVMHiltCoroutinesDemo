package com.app.koltinpoc.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.app.koltinpoc.di.DBRepository
import com.app.koltinpoc.di.Transformer
import com.app.koltinpoc.model.Article
import com.app.koltinpoc.utils.DataHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OfflineViewModel @Inject constructor(private val dbRepository: DBRepository) : ViewModel() {


    /*Transformation converts the LiveData article entity to LiveData article model class
    * and LiveData Datahandler  is observed from fragment
    */
    var article = dbRepository.getAllArticles().map { list ->

        val temp = list.map {
            Transformer.convertArticleEntityToArticleModel(it)
        }
        if (temp.isEmpty()) {
            DataHandler.ERROR(null, "LIST IS EMPTY OR NULL")
        } else {
            DataHandler.SUCCESS(temp)
        }
    }

    fun insertArticle(article: Article) {
        viewModelScope.launch(Dispatchers.IO) {
            dbRepository.insertArticle(article)
        }
    }

    fun deleteArticle(article: Article) {
        viewModelScope.launch(Dispatchers.IO) {
            dbRepository.delete(article)
        }
    }

}