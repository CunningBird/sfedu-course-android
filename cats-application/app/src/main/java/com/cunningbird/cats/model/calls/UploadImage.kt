package com.cunningbird.cats.model.calls

import com.squareup.moshi.JsonClass
import java.io.File

@JsonClass(generateAdapter = true)
data class UploadImage(val file: File, val sub_id: String)