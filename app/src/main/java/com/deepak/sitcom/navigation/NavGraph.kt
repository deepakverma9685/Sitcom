package com.deepak.sitcom.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navArgument
import com.deepak.sitcom.R
import com.deepak.sitcom.ui.screens.nowplaying.TrendingTvShows
import com.deepak.sitcom.ui.screens.showdetail.ShowDetail

@Composable
fun Navigation(
    navController: NavHostController, modifier: Modifier,
) {
    NavHost(navController, startDestination = Screen.Home.route, modifier) {
        composable(Screen.Home.route) {
            TrendingTvShows(
                navController = navController,
            )
        }
        composable(
            Screen.MovieDetail.route.plus(Screen.MovieDetail.objectPath),
            arguments = listOf(navArgument(Screen.MovieDetail.objectName) {
                type = NavType.IntType
            })
        ) {
            val movieId = it.arguments?.getInt(Screen.MovieDetail.objectName)
            movieId?.let {
                ShowDetail(
                    navController = navController, movieId
                )
            }
        }
    }
}

@Composable
fun navigationTitle(navController: NavController): String {
    return when (currentRoute(navController)) {
        Screen.MovieDetail.route -> stringResource(id = R.string.movie_detail)
        else -> {
            ""
        }
    }
}

@Composable
fun currentRoute(navController: NavController): String? {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.destination?.route?.substringBeforeLast("/")
}
