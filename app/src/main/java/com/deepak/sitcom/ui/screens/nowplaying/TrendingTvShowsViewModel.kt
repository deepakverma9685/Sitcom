package com.deepak.sitcom.ui.screens.nowplaying


import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.deepak.sitcom.data.models.Genre
import com.deepak.sitcom.data.models.GenreId
import com.deepak.sitcom.data.repository.TvShowsRepository
import com.deepak.sitcom.utils.AppConstant
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

@HiltViewModel
class TrendingTvShowsViewModel @Inject constructor(val repo: TvShowsRepository) : ViewModel() {
    var selectedGenre: MutableState<Genre> =
        mutableStateOf(Genre(null, AppConstant.DEFAULT_GENRE_ITEM))
    val filterData = MutableStateFlow<GenreId?>(null)

    @OptIn(ExperimentalCoroutinesApi::class)
    val nowPlayingMovies = filterData.flatMapLatest {
        repo.trendingTvShowsPagination()
    }.cachedIn(viewModelScope)

}