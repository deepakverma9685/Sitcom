package com.deepak.sitcom.ui.screens.mainscreen

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.deepak.sitcom.data.models.BaseModel
import com.deepak.sitcom.data.repository.DataState
import com.deepak.sitcom.data.repository.TvShowsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(private val repo: TvShowsRepository) : ViewModel() {
    val searchData: MutableState<DataState<BaseModel>?> = mutableStateOf(null)
    @ExperimentalCoroutinesApi
    @FlowPreview
    fun searchApi(searchKey: String) {
        viewModelScope.launch {
            flowOf(searchKey).debounce(300)
                .filter {
                    it.trim().isEmpty().not()
                }
                .distinctUntilChanged()
                .flatMapLatest {
                    repo.search(it)
                }.collect {
                    if (it is DataState.Success){
                        it.data
                        Log.e("TAG", "searchApi: data ${it.data.totalPages} ")
                    }
                    searchData.value = it
                }
        }
    }
}