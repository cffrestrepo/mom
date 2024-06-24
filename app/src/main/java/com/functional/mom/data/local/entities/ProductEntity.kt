package com.functional.mom.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.functional.mom.commons.Constants.Companion.TABLE_NAME_PRODUCT

@Entity(tableName = TABLE_NAME_PRODUCT)
data class ProductEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "thumbnail") val thumbnail: String,
    @ColumnInfo(name = "price") val price: String,
    @ColumnInfo(name = "nick_name") val nickname: String,
    @ColumnInfo(name = "address") val address: String,
    @ColumnInfo(name = "city") val city: String
)