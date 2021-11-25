package com.cunningbird.cats.model

import com.squareup.moshi.Json

data class AddFavorite(@field:Json(name = "image_id") val image_id: String)