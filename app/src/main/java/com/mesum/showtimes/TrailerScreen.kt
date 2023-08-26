package com.mesum.showtimes

import android.annotation.SuppressLint
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
    //sets the youtube tile to this callback variable
    videoString(videoDetails.original_title.toString())

    val dateSt = videoDetails.release_date.toString()
    val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    val formattedDate = LocalDate.parse(dateSt, dateFormatter) // Use LocalDate instead of LocalDateTime
    val res = DateTimeFormatter.ofPattern("MMMM dd, yyyy ").format(formattedDate) // August 04, 2017

    Column {
        YoutubeScreen(videoId = videoDetails.videoId.toString(), modifier = Modifier)
        Text(text = res, fontSize = 11.sp, textAlign = TextAlign.End, modifier = Modifier.align(Alignment.End).padding(end = 16.dp))
        Text(text = videoDetails.original_title.toString(), fontSize = 20.sp, modifier = Modifier.padding(top = 16.dp, bottom = 16.dp, start = 16.dp, end = 16.dp), fontWeight = FontWeight.Bold)
        Text(text = videoDetails.overview.toString(), fontSize = 18.sp, modifier = Modifier.padding(start = 16.dp, end = 16.dp))
    }
}


@Composable
fun YoutubeScreen(
    videoId: String,
    modifier: Modifier
) {
    val ctx = LocalContext.current
    AndroidView(factory = {
        val view = YouTubePlayerView(it)
        val fragment = view.addYouTubePlayerListener(
            object : AbstractYouTubePlayerListener() {
                override fun onReady(youTubePlayer: YouTubePlayer) {
                    super.onReady(youTubePlayer)
                    youTubePlayer.loadVideo(videoId, 0f)
                }
            }
        )
        view
    },
        update = {
            val view = it
            val fragment = view.addYouTubePlayerListener(
                object : AbstractYouTubePlayerListener() {
                    override fun onReady(youTubePlayer: YouTubePlayer) {
                        super.onReady(youTubePlayer)
                        youTubePlayer.loadVideo(videoId, 0f)
                    }

                    override fun onError(
                        youTubePlayer: YouTubePlayer,
                        error: PlayerConstants.PlayerError
                    ) {
                        super.onError(youTubePlayer, error)
                    }
                }
            )
            view
        }

    )
}







