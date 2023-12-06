package com.deepak.sitcom.ui.screens.showdetail

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.deepak.sitcom.R
import com.deepak.sitcom.data.models.ShowDetails
import com.deepak.sitcom.data.remote.ApiURL
import com.deepak.sitcom.data.repository.DataState
import com.deepak.sitcom.ui.components.CircleProgressBar
import com.deepak.sitcom.ui.theme.DefaultBackgroundColor
import com.deepak.sitcom.ui.theme.FontColor
import com.deepak.sitcom.ui.theme.SecondaryFontColor
import com.deepak.sitcom.utils.pagingLoadingState
import com.deepak.sitcom.ui.components.text.SubtitlePrimary
import com.deepak.sitcom.ui.components.text.SubtitleSecondary
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.animation.circular.CircularRevealPlugin
import com.skydoves.landscapist.coil.CoilImage
import com.skydoves.landscapist.components.rememberImageComponent
import com.skydoves.landscapist.placeholder.shimmer.ShimmerPlugin

@Composable
fun ShowDetail(navController: NavController, movieId: Int) {
    val showDetailViewModel = hiltViewModel<ShowDetailViewModel>()
    val progressBar = remember { mutableStateOf(false) }
    val movieDetail = showDetailViewModel.movieDetail

    LaunchedEffect(true) {
        showDetailViewModel.movieDetailApi(movieId)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                DefaultBackgroundColor
            )
    ) {
        CircleProgressBar(isDisplayed = progressBar.value, 0.4f)
        movieDetail.value?.let { it ->
            if (it is DataState.Success<ShowDetails>) {
                Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                    CoilImage(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(300.dp),
                        imageModel = { ApiURL.IMAGE_URL.plus(it.data.posterPath)},
                        imageOptions = ImageOptions(
                            contentScale = ContentScale.Crop,
                            alignment = Alignment.Center,
                            contentDescription = "Movie detail",
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
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(start = 10.dp, end = 10.dp)
                    ) {
                        Text(
                            text = it.data.name.toString(),
                            modifier = Modifier.padding(top = 10.dp),
                            color = FontColor,
                            fontSize = 30.sp,
                            fontWeight = FontWeight.W700,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 10.dp, top = 10.dp)
                        ) {

                            Column(Modifier.weight(1f)) {
                                SubtitlePrimary(
                                    text = it.data.originalLanguage.toString(),
                                )
                                SubtitleSecondary(
                                    text = stringResource(R.string.language)
                                )
                            }
                            Column(Modifier.weight(1f)) {
                                SubtitlePrimary(
                                    text = it.data.voteAverage.toString(),
                                )
                                SubtitleSecondary(
                                    text = stringResource(R.string.rating)
                                )
                            }
                            Column(Modifier.weight(1f)) {
                                SubtitlePrimary(
                                    text = it.data.episodeRunTime.toString()
                                )
                                SubtitleSecondary(
                                    text = stringResource(R.string.duration)
                                )
                            }
                            Column(Modifier.weight(1f)) {
                                SubtitlePrimary(
                                    text = it.data.firstAirDate.toString()
                                )
                                SubtitleSecondary(
                                    text = stringResource(R.string.release_date)
                                )
                            }
                        }
                        Text(
                            text = stringResource(R.string.description),
                            color = FontColor,
                            fontSize = 17.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }
        }

        movieDetail.pagingLoadingState {
            progressBar.value = it
        }
    }
}

