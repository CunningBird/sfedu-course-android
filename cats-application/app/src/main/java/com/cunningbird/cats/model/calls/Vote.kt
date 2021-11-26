package com.cunningbird.cats.model.calls

import com.squareup.moshi.Json

class Vote(@field:Json(name = "image_id") val image_id: String, @field:Json(name = "value") val value: Int)