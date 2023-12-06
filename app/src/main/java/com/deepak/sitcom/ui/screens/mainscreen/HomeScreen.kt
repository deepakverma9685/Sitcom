package com.deepak.sitcom.ui.screens.mainscreen

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.deepak.sitcom.R
import com.deepak.sitcom.data.remote.ConnectionState
import com.deepak.sitcom.data.remote.connectivityState
import com.deepak.sitcom.navigation.Navigation
import com.deepak.sitcom.navigation.Screen
import com.deepak.sitcom.navigation.currentRoute
import com.deepak.sitcom.navigation.navigationTitle
import com.deepak.sitcom.ui.components.AppBarWithArrow
import com.deepak.sitcom.ui.components.CircleProgressBar
import com.deepak.sitcom.ui.components.HomeAppBar
import com.deepak.sitcom.ui.components.SearchBar
import com.deepak.sitcom.ui.components.SearchUI
import com.deepak.sitcom.ui.theme.FloatingActionBackground
import com.deepak.sitcom.utils.pagingLoadingState
import kotlinx.coroutines.launch


@Composable
fun HomeScreen() {
    val mainViewModel = hiltViewModel<HomeScreenViewModel>()
    val navController = rememberNavController()
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    val isAppBarVisible = remember { mutableStateOf(true) }
    val searchProgressBar = remember { mutableStateOf(false) }
    // internet connection
    val connection by connectivityState()
    val isConnected = connection === ConnectionState.Available

    Scaffold(scaffoldState = scaffoldState, topBar = {
        when (currentRoute(navController)) {
            Screen.Home.route -> {
                if (isAppBarVisible.value) {
                    val appTitle: String = stringResource(R.string.app_name)
                    HomeAppBar(title = appTitle, openDrawer = {
                        scope.launch {
                            scaffoldState.drawerState.apply {
                                if (isClosed) open() else close()
                            }
                        }
                    }, openFilters = {
                        isAppBarVisible.value = false
                    })
                } else {
                    SearchBar(isAppBarVisible, mainViewModel)
                }
            }
            else -> {
                AppBarWithArrow(navigationTitle(navController)) {
                    navController.popBackStack()
                }
            }
        }
    },  floatingActionButton = {
        when (currentRoute(navController)) {
            Screen.Home.route -> {
                FloatingActionButton(
                    onClick = {
                        isAppBarVisible.value = false
                    }, backgroundColor = FloatingActionBackground
                ) {
                    Icon(Icons.Filled.Search, "", tint = Color.White)
                }
            }
        }
    }, snackbarHost = {
        if (isConnected.not()) {
            Snackbar(
                action = {}, modifier = Modifier.padding(8.dp)
            ) {
                Text(text = stringResource(R.string.there_is_no_internet))
            }
        }
    }) {
        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            Navigation(navController, Modifier.padding(it))
            Column {
                CircleProgressBar(isDisplayed = searchProgressBar.value, 0.1f)
                if (isAppBarVisible.value.not()) {
                    SearchUI(navController, mainViewModel.searchData) {
                        isAppBarVisible.value = true
                    }
                }
            }
        }
        mainViewModel.searchData.pagingLoadingState {
            searchProgressBar.value = it
        }
    }
}


