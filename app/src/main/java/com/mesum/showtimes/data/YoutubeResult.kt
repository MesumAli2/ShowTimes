package com.mesum.showtimes.data

data class YoutubeResult(
    val etag: String,
    val items: List<Item>? = listOf(),
    val kind: String,
    val nextPageToken: String,
    val pageInfo: PageInfo,
    val regionCode: String
)