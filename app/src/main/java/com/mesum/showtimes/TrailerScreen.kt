package com.mesum.showtimes

import android.util.Log
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mesum.showtimes.ui.theme.MovieViewModel

@Composable
fun TrailerScreen(viewModel: MovieViewModel) {


    val videoDetails by viewModel.uiState.collectAsState()
    Log.d("ViewDetailsLog", "Video : $videoDetails")
    Toast.makeText(LocalContext.current, videoDetails.toString(), Toast.LENGTH_LONG).show()
    WebViewScreen("NBqfEFRczTc")
}


@Composable
fun WebViewScreen(videoId: String) {
    AndroidView(
        factory = { context ->
            WebView(context).apply {
                settings.javaScriptEnabled = true
                webViewClient = WebViewClient()
                loadUrl("https://www.youtube.com/watch?v=$videoId")
            }
        },
        modifier = Modifier.fillMaxSize()
    )
}

