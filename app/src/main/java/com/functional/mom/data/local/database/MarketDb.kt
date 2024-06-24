package com.functional.mom.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.functional.mom.commons.Constants.Companion.VERSION_DATA_BASE
import com.functional.mom.data.local.dao.ProductDao
import com.functional.mom.data.local.entities.ProductEntity

@Database(
    entities = [ProductEntity::class],
    version = VERSION_DATA_BASE
)
abstract class MarketDb : RoomDatabase() {

    abstract fun productDao(): ProductDao
}