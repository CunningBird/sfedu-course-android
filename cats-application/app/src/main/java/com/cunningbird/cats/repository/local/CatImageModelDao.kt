package com.cunningbird.cats.repository.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.cunningbird.cats.model.CatImageModel

@Dao
interface CatImageModelDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(catModel: List<CatImageModel>)

    @Query("SELECT * FROM catimagemodel")
    fun getAllCatModel(): PagingSource<Int, CatImageModel>

    @Query("DELETE FROM catimagemodel")
    suspend fun clearAllCats()
}