package com.drakemin.recipes.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface RecipeDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecipes(recipeEntity: List<RecipeEntity>)



    @Query("DELETE FROM RecipeEntity where category= :category")
    suspend fun clearRecipesByCategory(category:String)

    @Query("""
        SELECT*
        FROM RecipeEntity
        WHERE category = :category 
    """)
    suspend fun getRecipesByCategory(category: String):List<RecipeEntity>

    @Query("""
        SELECT*
        FROM RecipeEntity
        WHERE LOWER(title) LIKE "%" || LOWER(:search) || "%" AND category = :category
    """)
    suspend fun searchRecipeByCategory(category: String,search:String):List<RecipeEntity>

}