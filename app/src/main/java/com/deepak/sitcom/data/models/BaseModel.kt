package com.deepak.sitcom.data.models


import com.google.gson.annotations.SerializedName

data class BaseModel(
    @SerializedName("page")
    val page: Int,
    @SerializedName("results")
    val results: List<TvShowsItem>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
)