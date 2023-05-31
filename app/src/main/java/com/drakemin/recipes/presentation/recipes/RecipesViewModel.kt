package com.drakemin.recipes.presentation.recipes


import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.drakemin.recipes.domain.repository.RecipeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.drakemin.recipes.util.Resource
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@HiltViewModel
class RecipesViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val repository: RecipeRepository
) : ViewModel() {

    var state by mutableStateOf(RecipesState())

    private var searchJob: Job? = null


    init {
        getRecipes()
    }

    fun onEvent(event: RecipesEvents) {
        when (event) {
            is RecipesEvents.Refresh -> {
                getRecipes(fetchFromRemote = true)
            }
            is RecipesEvents.OnSearchQueryChange ->{
                state = state.copy(searchQuery = event.search_query)
                searchJob?.cancel()
                searchJob = viewModelScope.launch {
                    delay(500L)
                    getRecipes()
                }

            }
        }

    }


    private fun getRecipes(
        query: String = state.searchQuery.lowercase(),
        fetchFromRemote: Boolean = false
    ) {
        viewModelScope.launch {
            val category = savedStateHandle.get<String>("category") ?: return@launch

            repository.getRecipes(category, query, fetchFromRemote).collect { result ->
                when (result) {
                    is Resource.Success -> {
                        result.data?.let { recipes ->
                            state = state.copy(
                                listOfRecipes = recipes
                            )
                        }
                    }
                    is Resource.Error -> Unit
                    is Resource.Loading -> {
                        state = state.copy(isLoading = result.isLoading)
                    }
                }
            }

        }
    }


}