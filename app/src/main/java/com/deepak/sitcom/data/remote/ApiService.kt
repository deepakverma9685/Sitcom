package com.deepak.sitcom.data.remote

import com.deepak.sitcom.data.models.BaseModel
import com.deepak.sitcom.data.models.ShowDetails
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("trending/tv/week?language=en-US")
    suspend fun getTrendingShows(
        @Query("page") page: Int,
        @Query("api_key") api_key: String = ApiURL.API_KEY
    ): BaseModel

    @GET("tv/{showId}")
    suspend fun showDetail(
        @Path("showId") movieId: Int, @Query("api_key") api_key: String = ApiURL.API_KEY
    ): ShowDetails

    @GET("movie/{movieId}/recommendations")
    suspend fun recommendedMovie(
        @Path("movieId") movieId: Int,
        @Query("page") one: Int,
        @Query("api_key") api_key: String = ApiURL.API_KEY
    ): BaseModel

    @GET("search/tv?include_adult=false&language=en-US&page=1")
    suspend fun search(
        @Query("query") searchKey: String, @Query("api_key") api_key: String = ApiURL.API_KEY
    ): BaseModel

}