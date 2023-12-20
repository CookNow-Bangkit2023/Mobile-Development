package com.dicoding.cooknow.ui.model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.cooknow.data.api.ApiConfig
import com.dicoding.cooknow.data.response.DetailRecipesResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class IngredientsViewModel: ViewModel() {
    private val _ingredientsList = MutableLiveData<List<String>>()
    val ingredientsList: LiveData<List<String>> = _ingredientsList

    private val _ingredientCount = MutableLiveData<Int>()
    val ingredientCount: LiveData<Int> = _ingredientCount

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getIngredients(id: Int) {
        _isLoading.postValue(true)
        val response = ApiConfig.getApiService().getDetailRecipes(id)
        response.enqueue(object : Callback<DetailRecipesResponse> {
            override fun onResponse(
                call: Call<DetailRecipesResponse>,
                response: Response<DetailRecipesResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val ingredientsList = response.body()?.resultRecipe?.ingredients
                    if (!ingredientsList.isNullOrBlank()) {
                        // Convert the string representation of a list into an actual list
                        val ingredients = parseIngredientsList(ingredientsList)
                        _ingredientsList.value = ingredients
                        _ingredientCount.value = ingredients.size
                    } else {
                        _ingredientsList.value = emptyList()
                        _ingredientCount.value = 0
                    }
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<DetailRecipesResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    private fun parseIngredientsList(ingredientsString: String): List<String> {
        // Remove square brackets and single quotes from the string
        val cleanedString = ingredientsString.replace("[", "").replace("]", "").replace("'", "")

        // Split the cleaned string into a list of strings
        return cleanedString.split(",").map { it.trim() }
    }

    companion object {
        private const val TAG = "IngredientsViewModel"
    }
}