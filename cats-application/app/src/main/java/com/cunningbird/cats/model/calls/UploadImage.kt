package com.cunningbird.cats.model.calls

import com.squareup.moshi.Json
import java.io.File

data class UploadImage(@field:Json(name = "file") val file: File, @field:Json(name = "sub_id") val sub_id: String)