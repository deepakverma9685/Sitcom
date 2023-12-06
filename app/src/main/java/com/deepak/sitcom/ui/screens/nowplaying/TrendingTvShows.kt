package com.deepak.sitcom.ui.screens.nowplaying

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.deepak.sitcom.data.models.GenreId

@Composable
fun TrendingTvShows(
    navController: NavController,
) {
    val nowPlayViewModel = hiltViewModel<TrendingTvShowsViewModel>()
    TvShowsItemList(
        navController = navController,
        movies = nowPlayViewModel.nowPlayingMovies,
        selectedName = nowPlayViewModel.selectedGenre.value
    ){
        nowPlayViewModel.filterData.value = GenreId(it?.id.toString())
        it?.let {
            nowPlayViewModel.selectedGenre.value = it
        }
    }
}