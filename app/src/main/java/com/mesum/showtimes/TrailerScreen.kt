package com.mesum.showtimes

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.util.Log
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mesum.showtimes.ui.theme.MovieViewModel
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TrailerScreen(viewModel: MovieViewModel, videoString: (String) -> Unit) {
    val videoDetails by viewModel.uiState.collectAsState()

    videoString(videoDetails.original_title.toString())

    val formattedDate = formatDate(videoDetails.release_date.toString())
    Column {
        YoutubeScreen(videoId = videoDetails.videoId.toString())
        Text(
            text = formattedDate,
            fontSize = 11.sp,
            textAlign = TextAlign.End,
            modifier = Modifier.align(Alignment.End).padding(end = 16.dp)
        )
        Text(
            text = videoDetails.original_title.toString(),
            fontSize = 20.sp,
            modifier = Modifier.padding(16.dp),
            fontWeight = FontWeight.Bold
        )
        Text(
            text = videoDetails.overview.toString(),
            fontSize = 18.sp,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun formatDate(dateString: String): String {
    val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    val formattedDate = LocalDate.parse(dateString, dateFormatter)
    return DateTimeFormatter.ofPattern("MMMM dd, yyyy ").format(formattedDate)
}

@Composable
fun YoutubeScreen(videoId: String) {
    val ctx = LocalContext.current
    AndroidView(factory = { createYouTubePlayerViewFactory(ctx, videoId) }) {
        updateYouTubePlayerView(it, videoId)
    }
}

private fun createYouTubePlayerViewFactory(context: Context, videoId: String): YouTubePlayerView {
    val view = YouTubePlayerView(context)
    val fragment = view.addYouTubePlayerListener(
        object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                super.onReady(youTubePlayer)
                youTubePlayer.loadVideo(videoId, 0f)
            }
        }
    )
    return view
}

private fun updateYouTubePlayerView(view: YouTubePlayerView, videoId: String) {
    val fragment = view.addYouTubePlayerListener(
        object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                super.onReady(youTubePlayer)
                youTubePlayer.loadVideo(videoId, 0f)
            }
        }
    )
}



