package com.deepak.sitcom.data.remote.pagination

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.deepak.sitcom.data.models.TvShowsItem
import com.deepak.sitcom.data.remote.ApiService
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class TrendingTvShowsPagination  @Inject constructor(private val apiService: ApiService):
    PagingSource<Int, TvShowsItem>() {

    private  val TAG = "TrendingTvShowsPaginati"

    override fun getRefreshKey(state: PagingState<Int, TvShowsItem>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, TvShowsItem> {
        return try {
            val nextPage = params.key ?: 1
            val movieList = apiService.getTrendingShows(nextPage)
            LoadResult.Page(
                data = movieList.results,
                prevKey = if (nextPage == 1) null else nextPage - 1,
                nextKey =  if (movieList.results.isNotEmpty()) movieList.page + 1 else  null
            )
        } catch (exception: IOException) {
            Log.e(TAG, "load: " +"exception ${exception.message}")
            return LoadResult.Error(exception)
        } catch (httpException: HttpException) {
            Log.e(TAG, "load: " +"httpException ${httpException.message}")
            return LoadResult.Error(httpException)
        }
    }
}
