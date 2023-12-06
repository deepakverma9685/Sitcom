package com.deepak.sitcom.ui.screens.nowplaying

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Colors
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.deepak.sitcom.data.models.Genre
import com.deepak.sitcom.data.models.TvShowsItem
import com.deepak.sitcom.data.remote.ApiURL
import com.deepak.sitcom.navigation.Screen
import com.deepak.sitcom.navigation.currentRoute
import com.deepak.sitcom.ui.components.CircleProgressBar
import com.deepak.sitcom.ui.theme.DefaultBackgroundColor
import com.deepak.sitcom.ui.theme.FontColor
import com.deepak.sitcom.ui.theme.Purple40
import com.deepak.sitcom.ui.theme.Purple80
import com.deepak.sitcom.ui.theme.SecondaryFontColor
import com.deepak.sitcom.utils.conditional
import com.deepak.sitcom.utils.items
import com.deepak.sitcom.utils.pagingLoadingState
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.animation.circular.CircularRevealPlugin
import com.skydoves.landscapist.coil.CoilImage
import com.skydoves.landscapist.components.rememberImageComponent
import com.skydoves.landscapist.placeholder.shimmer.ShimmerPlugin
import kotlinx.coroutines.flow.Flow

@Composable
fun TvShowsItemList(
    navController: NavController,
    movies: Flow<PagingData<TvShowsItem>>,
    selectedName: Genre?,
    onclick: (genreId: Genre?) -> Unit
) {
    val activity = (LocalContext.current as? Activity)
    val progressBar = remember { mutableStateOf(false) }
    val openDialog = remember { mutableStateOf(false) }
    val moviesItems: LazyPagingItems<TvShowsItem> = movies.collectAsLazyPagingItems()

    BackHandler(enabled = (currentRoute(navController) == Screen.Home.route)) {
        openDialog.value = true
    }
    Column(modifier = Modifier.background(DefaultBackgroundColor)) {
        CircleProgressBar(isDisplayed = progressBar.value, 0.4f)
        LazyVerticalGrid(columns = GridCells.Fixed(2),
            modifier = Modifier
                .padding(start = 5.dp, end = 5.dp)
                .conditional(movies == null) {
                    padding(top = 8.dp)
                },
            content = {
                items(moviesItems) { item ->
                    item?.let {
                        MovieItemView(item, navController)
                    }
                }
            })
    }
    moviesItems.pagingLoadingState {
        progressBar.value = it
    }
}


@Composable
fun MovieItemView(item: TvShowsItem, navController: NavController) {
    Column(modifier = Modifier.padding(start = 5.dp, end = 5.dp, top = 0.dp, bottom = 10.dp)) {
        CoilImage(
            modifier = Modifier
                .size(250.dp)
                .clickable {
                    navController.navigate(Screen.MovieDetail.route.plus("/${item.id}"))
                },
            imageModel = { ApiURL.IMAGE_URL.plus(item.posterPath) },
            imageOptions = ImageOptions(
                contentScale = ContentScale.Crop,
                alignment = Alignment.Center,
                contentDescription = "Movie item",
                colorFilter = null,
            ),
            component = rememberImageComponent {
                +CircularRevealPlugin(
                    duration = 800
                )
                +ShimmerPlugin(
                    baseColor = SecondaryFontColor,
                    highlightColor = DefaultBackgroundColor
                )
            },
        )
        Text(
            text = item.name.toString(),
            modifier = Modifier.padding(top = 10.dp),
            color = FontColor,
            fontSize = 30.sp,
            fontWeight = FontWeight.W700,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
fun SelectableGenreChip(
    selected: Boolean,
    genre: String,
    onclick: () -> Unit
) {

    val animateChipBackgroundColor by animateColorAsState(
        targetValue = if (selected) Purple80 else Purple40,
        animationSpec = tween(
            durationMillis = 50,
            delayMillis = 0,
            easing = LinearOutSlowInEasing
        )
    )
    Box(
        modifier = Modifier
            .padding(end = 8.dp)

            .background(
                color = animateChipBackgroundColor
            )
            .height(32.dp)
            .widthIn(min = 80.dp)
            .padding(horizontal = 8.dp)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) {
                onclick()
            }
    ) {
        Text(
            text = genre,
            fontSize = 14.sp,
            fontWeight = FontWeight.Light,
            textAlign = TextAlign.Center,
            modifier = Modifier.align(Alignment.Center),
            color = Color.White.copy(alpha = 0.80F)
        )
    }
}



