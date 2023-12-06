package com.deepak.sitcom.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.deepak.sitcom.data.models.BaseModel
import com.deepak.sitcom.data.models.ShowDetails
import com.deepak.sitcom.data.models.TvShowsItem
import com.deepak.sitcom.data.remote.ApiService
import com.deepak.sitcom.data.remote.pagination.TrendingTvShowsPagination
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class TvShowsRepository @Inject constructor(
    private val apiService: ApiService
) : MovieRepositoryInterface {
    override suspend fun showDetails(movieId: Int): Flow<DataState<ShowDetails>> = flow {
        emit(DataState.Loading)
        try {
            val searchResult = apiService.showDetail(movieId)
            emit(DataState.Success(searchResult))
        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }

    override suspend fun recommendedMovie(movieId: Int, page: Int): Flow<DataState<BaseModel>> =
        flow {
            emit(DataState.Loading)
            try {
                val searchResult = apiService.recommendedMovie(movieId, page)
                emit(DataState.Success(searchResult))

            } catch (e: Exception) {
                emit(DataState.Error(e))
            }
        }


    override suspend fun search(searchKey: String): Flow<DataState<BaseModel>> = flow {
        emit(DataState.Loading)
        try {
            val searchResult = apiService.search(searchKey)
            emit(DataState.Success(searchResult))

        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }
//
//    override suspend fun genreList(): Flow<DataState<Genres>> = flow {
//        emit(DataState.Loading)
//        try {
//            val genreResult = apiService.genreList()
//            emit(DataState.Success(genreResult))
//
//        } catch (e: Exception) {
//            emit(DataState.Error(e))
//        }
//    }
//
//    override suspend fun movieCredit(movieId: Int): Flow<DataState<Artist>> = flow {
//        emit(DataState.Loading)
//        try {
//            val artistResult = apiService.movieCredit(movieId)
//            emit(DataState.Success(artistResult))
//
//        } catch (e: Exception) {
//            emit(DataState.Error(e))
//        }
//    }
//
//    override suspend fun artistDetail(personId: Int): Flow<DataState<ArtistDetail>> = flow {
//        emit(DataState.Loading)
//        try {
//            val artistDetailResult = apiService.artistDetail(personId)
//            emit(DataState.Success(artistDetailResult))
//
//        } catch (e: Exception) {
//            emit(DataState.Error(e))
//        }
//    }

    override fun trendingTvShowsPagination(): Flow<PagingData<TvShowsItem>> = Pager(
        pagingSourceFactory = { TrendingTvShowsPagination(apiService) },
        config = PagingConfig(pageSize = 1)
    ).flow

//    override fun popularPagingDataSource(genreId: String?): Flow<PagingData<MovieItem>> = Pager(
//        pagingSourceFactory = { PopularPagingDataSource(apiService, genreId) },
//        config = PagingConfig(pageSize = 1)
//    ).flow
//
//    override fun topRatedPagingDataSource(genreId: String?): Flow<PagingData<MovieItem>> = Pager(
//        pagingSourceFactory = { TopRatedPagingDataSource(apiService, genreId) },
//        config = PagingConfig(pageSize = 1)
//    ).flow
//
//    override fun upcomingPagingDataSource(genreId: String?): Flow<PagingData<MovieItem>> = Pager(
//        pagingSourceFactory = { UpcomingPagingDataSource(apiService, genreId) },
//        config = PagingConfig(pageSize = 1)
//    ).flow
//
//    override fun genrePagingDataSource(genreId: String): Flow<PagingData<MovieItem>> = Pager(
//        pagingSourceFactory = { GenrePagingDataSource(apiService, genreId) },
//        config = PagingConfig(pageSize = 1)
//    ).flow

}