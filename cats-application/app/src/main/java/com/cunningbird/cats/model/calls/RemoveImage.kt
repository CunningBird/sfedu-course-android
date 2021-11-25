package com.cunningbird.cats.model.calls

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RemoveImage(val image_id: String)