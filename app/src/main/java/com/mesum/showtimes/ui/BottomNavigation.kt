package com.mesum.showtimes.ui

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.navigation.NavHostController
import com.mesum.showtimes.R
import com.mesum.showtimes.data.BottomNavigationItem
import com.mesum.showtimes.ui.theme.MovieViewModel


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun BottomNavigation(navController: NavHostController, viewModel: MovieViewModel) {

    var selectedItemIndex by rememberSaveable {
        mutableStateOf(0)
    }
    val trending: Painter = painterResource(id = R.drawable.trending)
    val upcoming : Painter = painterResource(id = R.drawable.upcoming)
    val popular : Painter = painterResource(id = R.drawable.popular)
    val topRated : Painter = painterResource(id = R.drawable.toprated)


    val movieCategories = listOf(
        BottomNavigationItem("Trending",trending, trending),
        BottomNavigationItem("Upcoming", upcoming, upcoming),
        BottomNavigationItem("Popular", popular, popular),
        BottomNavigationItem("Top Rated", topRated, topRated)
    )



    NavigationBar {
        movieCategories.forEachIndexed { index, bottomNavigationItem ->
            NavigationBarItem(selected = selectedItemIndex == index, onClick = {
                selectedItemIndex = index
                when(selectedItemIndex){
                    0 ->{ navController.navigate(Movies.Trending.name) }
                    1 ->{
                        navController.navigateUp()
                        navController.navigate(Movies.Upcoming.name)
                    }
                    2 -> {
                        navController.navigateUp()
                        navController.navigate(Movies.Popular.name)}
                    3 -> {
                        navController.navigateUp()
                        navController.navigate(Movies.TopRated.name)
                    }
                }

            }, icon = {
                Icon(painter =
                if (index == selectedItemIndex) {
                    bottomNavigationItem.selectedIcon

                }else{
                    bottomNavigationItem.selectedIcon
                }
                    , contentDescription =bottomNavigationItem.title )
            },

                label = {
                    Text(text = bottomNavigationItem.title , style = TextStyle(
                        color = if (index == selectedItemIndex){
                            Color.LightGray
                        }else{
                            Color.Gray
                        }
                    )
                    )
                })

        }

    }


}