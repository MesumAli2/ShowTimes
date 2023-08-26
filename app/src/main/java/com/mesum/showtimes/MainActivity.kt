package com.mesum.showtimes

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.mesum.showtimes.ui.theme.MovieViewModel
import com.mesum.showtimes.ui.theme.ShowTimesTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ShowTimesTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Scaffold(bottomBar = {
                        BottomNavigation()
                    }, content = {
                        ShowTimeApp(it)
                    })

                }

            }
        }
    }
}



data class BottomNavigationItem(val title: String, val selectedIcon: ImageVector, val unselectedIcon: ImageVector)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun BottomNavigation(){
    val viewModel : MovieViewModel = viewModel()

    val navController = rememberNavController()
    var selectedItemIndex by rememberSaveable {
        mutableStateOf(0)
    }
    val movieCategories = listOf(
        BottomNavigationItem("Trending", Icons.Filled.Home, Icons.Default.Home),
        BottomNavigationItem("Upcoming", Icons.Filled.MailOutline, Icons.Default.MailOutline),
        BottomNavigationItem("Popular", Icons.Filled.Star, Icons.Default.Star),
        BottomNavigationItem("Top Rated", Icons.Filled.ThumbUp, Icons.Default.ThumbUp)
    )


    NavigationBar() {
        
        movieCategories.forEachIndexed { index, bottomNavigationItem ->

            NavigationBarItem(selected = selectedItemIndex == index, onClick = {
                selectedItemIndex = index


            }, icon = {
                Icon(imageVector =
                    if (index == selectedItemIndex) {
                        bottomNavigationItem.selectedIcon

                    }else{
                        bottomNavigationItem.selectedIcon
                    }
                , contentDescription =bottomNavigationItem.title )
            })


        }
        

    }
    

}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ShowTimesTheme {
    }
}