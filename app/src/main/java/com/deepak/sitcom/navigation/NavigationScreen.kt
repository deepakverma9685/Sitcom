package com.deepak.sitcom.navigation

import androidx.annotation.StringRes
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import com.deepak.sitcom.R

sealed class Screen(
    val route: String,
    val objectName: String = "",
    val objectPath: String = ""
) {
    object Home : Screen("home_screen")
    object MovieDetail : Screen("movie_detail_screen", objectName = "movieItem", objectPath = "/{showId}")

}