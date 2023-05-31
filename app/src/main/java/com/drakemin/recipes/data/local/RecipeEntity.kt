package com.drakemin.recipes.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
class RecipeEntity(

    val apiId: Int,
    val title: String,
    val category: String,
    @PrimaryKey val id: Int? = null
)
