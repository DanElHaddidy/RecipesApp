package com.drakemin.recipes.data.local


import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.compose.ui.res.colorResource
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.hamcrest.CoreMatchers
import org.hamcrest.CoreMatchers.`is`
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.matchers.JUnitMatchers.hasItem
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class RecipeDaoTest {


    private lateinit var database: RecipeDatabase
    private lateinit var dao: RecipeDao

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            RecipeDatabase::class.java
        ).allowMainThreadQueries().build()
        dao = database.dao
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun insertRecipe() = runTest {

        val recipeItem1 = RecipeEntity(1, "Pasta carbonara", "Italian", 1)
        val recipeItem2 = RecipeEntity(2, "Pasta bolognese ", "Italian", 2)
        val listOfRecipes = listOf(recipeItem1, recipeItem2)

        dao.insertRecipes(listOfRecipes)

        val allRecipes = dao.getRecipesByCategory("Italian")

        assertThat(listOfRecipes[1].title).isEqualTo(allRecipes[1].title)
    }

    @Test
    fun deleteRecipes() = runTest {
        val recipeItem1 = RecipeEntity(1, "Pasta carbonara", "Italian", 1)
        val recipeItem2 = RecipeEntity(2, "Pasta bolognese ", "Italian", 2)
        val listOfRecipes = listOf(recipeItem1, recipeItem2)

        dao.insertRecipes(listOfRecipes)
        dao.clearRecipesByCategory("Italian")

        val allRecipes = dao.getRecipesByCategory("Italian")

        assertThat(allRecipes).isEmpty()
    }

    @Test
    fun searchRecipesByCategory() = runTest {
        val recipeItem1 = RecipeEntity(1, "Pasta carbonara", "Italian", 1)
        val recipeItem2 = RecipeEntity(2, "Pasta bolognese ", "Italian", 2)
        val listOfRecipes = listOf(recipeItem1, recipeItem2)

        dao.insertRecipes(listOfRecipes)

        val searchedRecipes = dao.getRecipesByCategory("Italian")

        assertThat(searchedRecipes.size).isEqualTo(listOfRecipes.size)
    }
}