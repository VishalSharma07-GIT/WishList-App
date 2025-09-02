package com.example.mywishlistapp.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "wish-table")
data class Wish(
     @PrimaryKey(autoGenerate = true)
    val id: Long=0L,
     @ColumnInfo(name="wish-title")
    val title: String=" ",
     @ColumnInfo(name="wish-desc")
    val description: String=" "
)

object DummyWish{
    val wishList = listOf(
        Wish(title = "Google Watch 2",
            description = "An Android watch"),

        Wish(title = "Sci fiction book",
            description = "a science friction book from the best seller "),

        Wish(title = "A table with screen",
            description = "A table that contain the screen for use"),

        Wish(title = "Watch dog 2",
            description = "A game to watch and play "),

    )
}