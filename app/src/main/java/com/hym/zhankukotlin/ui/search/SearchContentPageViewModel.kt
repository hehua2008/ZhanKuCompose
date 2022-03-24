package com.hym.zhankukotlin.ui.search

import androidx.lifecycle.*
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.hym.zhankukotlin.MyApplication
import com.hym.zhankukotlin.model.*
import com.hym.zhankukotlin.paging.LoadParamsHolder
import com.hym.zhankukotlin.paging.SearchContentPagingSource
import kotlinx.coroutines.flow.Flow

class SearchContentPageViewModel(private val contentType: ContentType) : ViewModel() {
    companion object {
        private const val TAG = "SearchContentPageViewModel"
    }

    private val _page = MutableLiveData<Int>(1)
    val page: LiveData<Int> = _page

    private val _totalPages = MutableLiveData<Int>(2)
    val totalPages: LiveData<Int> = _totalPages

    private val _pageSize = MutableLiveData<Int>(10)
    val pageSize: LiveData<Int> = _pageSize

    private var word = ""

    private var topCate: TopCate? = null

    private var recommendLevel = RecommendLevel.ALL_LEVEL

    private var sortOrder = SortOrder.BEST_MATCH

    private val _mediatorLiveData = MediatorLiveData<Unit>().apply {
        addSource(page) { value = Unit }
        addSource(pageSize) { value = Unit }
    }
    val mediatorLiveData: LiveData<Unit> = _mediatorLiveData

    fun setPage(page: Int) {
        if (page < 1 || _page.value == page) return
        _page.value = page.coerceAtMost(totalPages.value ?: page)
    }

    /**
     * Set the [pageSize] field does not take effect.
     * I have no idea why the server always considers the page size as 10 when returning a response.
     */
    fun setPageSize(pageSize: Int) {
        if (_pageSize.value == pageSize) return
        _pageSize.value = pageSize
    }

    fun setWord(word: String) {
        val word = word.trim()
        if (this.word == word) return
        this.word = word
        _page.value = 1
    }

    fun setTopCate(topCate: TopCate?) {
        if (this.topCate == topCate) return
        this.topCate = topCate
        _page.value = 1
    }

    fun setRecommendLevel(recommendLevel: RecommendLevel) {
        if (this.recommendLevel == recommendLevel) return
        this.recommendLevel = recommendLevel
        _page.value = 1
    }

    fun setSortOrder(sortOrder: SortOrder) {
        if (this.sortOrder == sortOrder) return
        this.sortOrder = sortOrder
        _page.value = 1
    }

    val pagingFlow: Flow<PagingData<Content>> = Pager(
        // Configure how data is loaded by passing additional properties to
        // PagingConfig, such as prefetchDistance.
        config = PagingConfig(pageSize = pageSize.value ?: 10),
        initialKey = LoadParamsHolder.INITIAL,
        pagingSourceFactory = {
            SearchContentPagingSource(
                networkService = MyApplication.networkService,
                initialPage = page.value ?: 1,
                pageSize = pageSize.value ?: 10,
                word = word,
                contentType = contentType,
                topCate = topCate,
                recommendLevel = recommendLevel,
                sortOrder = sortOrder
            ) {
                _totalPages.postValue(it)
            }
        }
    )
        .flow
        .cachedIn(viewModelScope)
}