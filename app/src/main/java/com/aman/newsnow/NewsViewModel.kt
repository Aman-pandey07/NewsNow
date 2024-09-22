package com.aman.newsnow

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kwabenaberko.newsapilib.NewsApiClient
import com.kwabenaberko.newsapilib.models.Article
import com.kwabenaberko.newsapilib.models.request.EverythingRequest
import com.kwabenaberko.newsapilib.models.request.TopHeadlinesRequest
import com.kwabenaberko.newsapilib.models.response.ArticleResponse

class NewsViewModel : ViewModel(){

    private val _articles = MutableLiveData<List<Article>>()
    val articles: LiveData<List<Article>> = _articles

    private val _query = MutableLiveData<String>("")
    val query: LiveData<String> = _query

    private val _selectedArticle = MutableLiveData<Article?>()
    val selectedArticle: LiveData<Article?> = _selectedArticle

    fun onArticleSelected(article: Article) {
        _selectedArticle.value = article
    }

    init {
        fetchNewsTopHeadlines()
    }

    fun onQueryChanged(newQuery: String) {
        _query.value = newQuery
    }

    fun searchArticles() {
        val query = _query.value ?: return
        val newsApiClient = NewsApiClient(Constants.apiKey)

        val request = EverythingRequest.Builder()
            .q(query)
            .language("en")
            .build()

        newsApiClient.getEverything(request, object : NewsApiClient.ArticlesResponseCallback {
            override fun onSuccess(response: ArticleResponse?) {
                response?.articles?.let {
                    _articles.postValue(it)
                }
            }

            override fun onFailure(throwable: Throwable?) {
                throwable?.localizedMessage?.let { Log.i("NewsAPI Search Failed", it) }
            }
        })
    }

    fun fetchNewsTopHeadlines(){
        val newsApiClient = NewsApiClient(Constants.apiKey)


        val request = TopHeadlinesRequest.Builder().language("en").build()
        newsApiClient.getTopHeadlines(request,object :NewsApiClient.ArticlesResponseCallback{
            override fun onSuccess(response: ArticleResponse?) {
               response?.articles?.let {
                   _articles.postValue(it)
               }
            }

            override fun onFailure(throwable: Throwable?) {
                if (throwable!=null){
                    throwable.localizedMessage?.let { Log.i("NewsAPI Response Failed", it) }
                }
            }

        })
    }
}