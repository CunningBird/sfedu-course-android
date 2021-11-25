package com.cunningbird.cats.model.calls

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RemoveFavorite(val favorite_id: String)