package com.deepak.sitcom.ui.screens.showdetail

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.deepak.sitcom.data.models.BaseModel
import com.deepak.sitcom.data.models.ShowDetails
import com.deepak.sitcom.data.repository.DataState
import com.deepak.sitcom.data.repository.TvShowsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShowDetailViewModel @Inject constructor(private val repo: TvShowsRepository) : ViewModel() {
    val movieDetail: MutableState<DataState<ShowDetails>?> = mutableStateOf(null)

    fun movieDetailApi(movieId: Int) {
        viewModelScope.launch {
            repo.showDetails(movieId).onEach {
                movieDetail.value = it
            }.launchIn(viewModelScope)
        }
    }
}