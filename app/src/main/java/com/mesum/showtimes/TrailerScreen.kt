package com.mesum.showtimes

import android.annotation.SuppressLint
import android.util.Log
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mesum.showtimes.ui.theme.MovieViewModel
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView

@SuppressLint("SuspiciousIndentation", "StateFlowValueCalledInComposition")
@Composable
fun TrailerScreen( viewModel: MovieViewModel,videoString : (String) -> Unit) {
    val videoModelVideo : MovieViewModel = viewModel()
    val videoDetails by viewModel.uiState.collectAsState()
//    val videoId by viewModel.youtubeState.collectAsState()

    var rememberedString : String = "NBqfEFRczTc"

    Log.d("ViewDetailsLog", "Video : $videoDetails")

    Toast.makeText(LocalContext.current, videoDetails.toString(), Toast.LENGTH_LONG).show()

    videoString(videoDetails.original_title.toString())


   // getVideo(videoDetails.original_title, viewModel = viewModel)



    val videoState = viewModel.uiState.collectAsState().value

    Text(text =videoState.videoId.toString() )
  WebViewScreen(videoId = videoState.videoId.toString(), viewModel = viewModel)

    YoutubeScreen(videoId = videoState.videoId.toString(), modifier = Modifier)
    Log.d("VideoStateIn", videoState.videoId.toString())





}





@Composable
fun WebViewScreen(videoId: String, viewModel: MovieViewModel) {
    Log.d("VideoIdis", videoId.toString())
   val viewDiaoState by  viewModel.uiState.collectAsState()
    Log.d("VideoIdis", viewDiaoState.videoId.toString())

    AndroidView(
        factory = { context ->
            WebView(context).apply {
                settings.javaScriptEnabled = true
                webViewClient = WebViewClient()
                val video =
                    "<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/$videoId\" title=\"YouTube video player\" frameborder=\"0\" allowfullscreen></iframe>"

                loadData(video,"text/html", "utf-8" )
              //  loadUrl("https://www.youtube.com/watch?v=${videoId}")
            }
        },
        modifier = Modifier.fillMaxSize(),
        update = { webView ->
            // Update the WebView here, if needed
//            webView.loadUrl("https://www.youtube.com/watch?v=$videoId")
            val video =
                "<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/$videoId\" title=\"YouTube video player\" frameborder=\"0\" allowfullscreen></iframe>"

            webView.loadData(video,"text/html", "utf-8" )
        }
    )
}

@Composable
fun YoutubeScreen(
    videoId: String,
    modifier: Modifier
) {
    val ctx = LocalContext.current
    AndroidView(factory = {
        var view = YouTubePlayerView(it)
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

            var view = it
            val fragment = view.addYouTubePlayerListener(
                object : AbstractYouTubePlayerListener() {
                    override fun onReady(youTubePlayer: YouTubePlayer) {
                        super.onReady(youTubePlayer)
                        youTubePlayer.loadVideo(videoId, 0f)
                    }
                }
            )
            view
        }

    )
}







