package com.mesum.showtimes.ui

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.mesum.showtimes.ui.theme.MovieViewModel

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MovieContent(navController: NavHostController, viewModel: MovieViewModel) {

    Scaffold(
        bottomBar = {
            BottomNavigation(navController, viewModel)
        },
        topBar = {

            MovieTopBar( viewModel, navController)
        }
    ) {
        MovieNavHost(navController, viewModel, it)
    }
}