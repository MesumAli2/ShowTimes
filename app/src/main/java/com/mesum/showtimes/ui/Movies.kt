package com.mesum.showtimes.ui

import androidx.annotation.StringRes
import com.mesum.showtimes.R

enum class Movies(@StringRes val title: Int) {
    Trending(title = R.string.trending),
    Upcoming(title = R.string.upcoming),
    Popular(title = R.string.popular),
    TopRated(title = R.string.top_rated),
    SearchScreen(title = R.string.searchview)
}