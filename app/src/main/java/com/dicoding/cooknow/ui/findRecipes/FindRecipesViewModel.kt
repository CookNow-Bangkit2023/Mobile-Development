package com.dicoding.cooknow.ui.findRecipes

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.cooknow.data.api.ApiConfig
import com.dicoding.cooknow.data.response.PredictRecipesResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FindRecipesViewModel() : ViewModel() {
    private val _findRecipes = MutableLiveData<PredictRecipesResponse?>()
    val findRecipes: LiveData<PredictRecipesResponse?> = _findRecipes

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun addIngredients(ingredients: List<String>, user_uid: String){
        val ingredientsString = ingredients.joinToString(",")

        val requestMap: Map<String, String> = mapOf(
            "ingres" to ingredientsString,
            "user_id" to user_uid
        )

        val response = ApiConfig.getApiService().postIngredients(requestMap)
        response.enqueue(object : Callback<PredictRecipesResponse> {
            override fun onResponse(
                call: Call<PredictRecipesResponse>,
                response: Response<PredictRecipesResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val recipes = response.body()
                    _findRecipes.value = recipes
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<PredictRecipesResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    companion object {
        private const val TAG = "FindRecipesViewModel"
    }
}