package com.cunningbird.cats.model.calls

import com.squareup.moshi.Json

data class RemoveImage(@field:Json(name = "image_id") val image_id: String)