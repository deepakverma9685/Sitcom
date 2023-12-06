package com.deepak.sitcom.data.repository

import androidx.paging.PagingData
import com.deepak.sitcom.data.models.BaseModel
import com.deepak.sitcom.data.models.ShowDetails
import com.deepak.sitcom.data.models.TvShowsItem
import kotlinx.coroutines.flow.Flow

interface MovieRepositoryInterface {
    suspend fun showDetails(movieId: Int): Flow<DataState<ShowDetails>>
    suspend fun recommendedMovie(movieId: Int, page: Int): Flow<DataState<BaseModel>>
    suspend fun search(searchKey: String): Flow<DataState<BaseModel>>
    fun trendingTvShowsPagination(): Flow<PagingData<TvShowsItem>>

}