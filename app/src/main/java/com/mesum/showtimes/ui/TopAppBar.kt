package com.mesum.showtimes.ui

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.mesum.showtimes.R
import com.mesum.showtimes.ui.theme.MovieViewModel
import com.mesum.showtimes.presentation.SearchBar

@OptIn(
    ExperimentalAnimationApi::class, ExperimentalComposeUiApi::class,
    ExperimentalMaterial3Api::class
)
@Composable
fun MovieTopBar(viewModel: MovieViewModel, navController: NavController) {
    var isSearchBarVisible by remember { mutableStateOf(false) }

    if (isSearchBarVisible) {
        val textSearchObservable by viewModel.stringValue.collectAsState()

        SearchBar(
            searchText = textSearchObservable,
            onSearchTextChanged = { query ->
                viewModel.searchMovies(query = query)
            },
            onClearClick = {
                isSearchBarVisible = false
                viewModel.searchMovies("")
                navController.navigateUp()
            },
            onNavigateBack = {
                isSearchBarVisible = false
                viewModel.searchMovies("")
                navController.navigateUp()
            }
        )
    } else {
        TopAppBar(
            title = {
                Text(
                    text = stringResource(id = R.string.filmflix),
                    fontFamily = FontFamily.SansSerif,
                    fontWeight = FontWeight.Light
                )
            },
            navigationIcon = {
                IconButton(onClick = {}) {
                    Icon(
                        painter = painterResource(id = R.drawable.app_icon),
                        contentDescription = "",
                        modifier = Modifier
                            .height(24.dp)
                            .width(24.dp)
                    )
                }
            },
            actions = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "",
                    modifier = Modifier.clickable {
                        isSearchBarVisible = true
                        navController.navigate(route = Movies.SearchScreen.name)
                    }
                )
            }
        )
    }
}