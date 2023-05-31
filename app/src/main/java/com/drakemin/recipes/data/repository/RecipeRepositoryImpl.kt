package com.drakemin.recipes.data.repository

import android.util.Log
import com.drakemin.recipes.data.local.RecipeDatabase
import com.drakemin.recipes.data.mapper.toRecipe
import com.drakemin.recipes.data.mapper.toRecipeEntity
import com.drakemin.recipes.data.mapper.toRecipeSourceUrl
import com.drakemin.recipes.data.remote.RecipeApi
import com.drakemin.recipes.domain.model.Recipe
import com.drakemin.recipes.domain.model.RecipeSourceUrl
import com.drakemin.recipes.domain.repository.RecipeRepository
import com.drakemin.recipes.util.Resource
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class RecipeRepositoryImpl @Inject constructor(
    private val api: RecipeApi,
    private val db: RecipeDatabase
) : RecipeRepository {

    private val dao = db.dao

    override suspend fun getRecipes(
        query_category: String,
        query_search: String,
        fetchFromRemote: Boolean
    ): Flow<Resource<List<Recipe>>> {
        return flow {
            emit(Resource.Loading(isLoading = true))
            val localRecipes = dao.searchRecipeByCategory(query_category, query_search)
            emit(Resource.Success(
                data = localRecipes.map { it.toRecipe() }
            ))
            val isDBEmpty = localRecipes.isEmpty() && query_search.isBlank()
            val shouldJustLoadFromCache = !isDBEmpty && !fetchFromRemote
            if (shouldJustLoadFromCache) {
                emit(Resource.Loading(false))
                return@flow
            }
            val remoteRecipes =
                try {
                    api.getRecipesByCategory(query_category).listOfRecipes.map { it.toRecipe() }

                } catch (e: IOException) {
                    e.printStackTrace()
                    emit(Resource.Error("Could not load data IO (getRecipe)"))
                    null
                } catch (e: HttpException) {
                    e.printStackTrace()
                    emit(Resource.Error("Could not load data Http (getRecipe)"))
                    null
                }

            remoteRecipes?.let { recipes ->
                dao.clearRecipesByCategory(query_category)
                dao.insertRecipes(
                    recipes.map { it.toRecipeEntity(query_category) }
                )
                emit(Resource.Success(
                    data = dao
                        .searchRecipeByCategory(category = query_category, "")
                        .map { it.toRecipe() }
                ))
                emit(Resource.Loading(false))
            }

        }
    }

    override suspend fun getRecipeUrl(
        id: String
    ): Flow<Resource<RecipeSourceUrl>> {
        return flow {
            emit(Resource.Loading(isLoading = true))
            val remoteRecipeSourceUrl =
                try {
                    api.getRecipeSourceUrl(id).toRecipeSourceUrl()

                } catch (e: IOException) {
                    e.printStackTrace()
                    emit(Resource.Error("Could not load data IO (getRecipeUrl)"))
                    null
                } catch (e: HttpException) {
                    e.printStackTrace()
                    emit(Resource.Error("Could not load data Http (getRecipeUrl)"))
                    null
                }
            remoteRecipeSourceUrl?.let { recipeUrl ->
                emit(Resource.Success(
                    data = recipeUrl
                ))
                emit(Resource.Loading(false))
            }
        }
    }



}