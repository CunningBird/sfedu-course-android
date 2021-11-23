package com.cunningbird.cats.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CatImageModel(@PrimaryKey val id: String, val url: String)