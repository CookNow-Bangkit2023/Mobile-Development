package com.dicoding.cooknow.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.cooknow.data.api.ApiConfig
import com.dicoding.cooknow.data.response.RecipesResponse
import com.dicoding.cooknow.data.response.RecipesResponseItem
import com.dicoding.cooknow.data.response.SearchRecipesResponse
import com.dicoding.cooknow.data.response.SearchRecipesResponseItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel : ViewModel() {
    private val _recipesResponseItem = MutableLiveData<List<RecipesResponseItem>?>()
    val recipesResponseItem: MutableLiveData<List<RecipesResponseItem>?> = _recipesResponseItem

    private val _searchrecipesResponseItem = MutableLiveData<List<SearchRecipesResponseItem>?>()
    val searchrecipesResponseItem: MutableLiveData<List<SearchRecipesResponseItem>?> = _searchrecipesResponseItem

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun findRecipes(){
        _isLoading.postValue(true)
        val response = ApiConfig.getApiService().getRecipes()
        response.enqueue(object : Callback<List<RecipesResponseItem>> {
            override fun onResponse(
                call: Call<List<RecipesResponseItem>>,
                response: Response<List<RecipesResponseItem>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val recipesList = response.body()
                    _recipesResponseItem.value = recipesList
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<RecipesResponseItem>>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    fun searchRecipes(query: String) {
        _isLoading.postValue(true)
        val response = ApiConfig.getApiService().getSearchRecipe(query)
        response.enqueue(object : Callback<List<SearchRecipesResponseItem>> {
            override fun onResponse(
                call: Call<List<SearchRecipesResponseItem>>,
                response: Response<List<SearchRecipesResponseItem>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val recipesList = response.body()
                    _searchrecipesResponseItem.value = recipesList
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<SearchRecipesResponseItem>>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    companion object {

        private const val TAG = "HomeViewModel"
    }
}