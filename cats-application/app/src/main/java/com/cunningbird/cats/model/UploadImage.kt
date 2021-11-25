package com.cunningbird.cats.model

import com.squareup.moshi.JsonClass
import java.io.File

@JsonClass(generateAdapter = true)
data class UploadImage(val file: File)