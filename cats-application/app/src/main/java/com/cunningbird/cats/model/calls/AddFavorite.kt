package com.cunningbird.cats.model.calls

import com.squareup.moshi.Json

data class AddFavorite(@field:Json(name = "image_id") val image_id: String, @field:Json(name = "sub_id") val sub_id: String)