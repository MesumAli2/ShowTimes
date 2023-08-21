package com.mesum.showtimes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mesum.showtimes.ui.theme.MovieViewModel
import com.mesum.showtimes.ui.theme.ShowTimesTheme
import com.mesum.showtimes.ui.theme.TrendingMoviesScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ShowTimesTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    TrendingMoviesScreen("Android")
                }



            }
        }
    }
}



@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ShowTimesTheme {
        TrendingMoviesScreen("Android")
    }
}