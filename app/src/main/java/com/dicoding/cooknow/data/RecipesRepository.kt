package com.dicoding.cooknow.data

import com.dicoding.cooknow.data.api.ApiService
import com.dicoding.cooknow.data.response.RecipesResponse
import com.dicoding.cooknow.data.response.RecipesResponseItem
import retrofit2.Call

class RecipesRepository(private val apiService: ApiService) {
    suspend fun getDetailRecipes() : Call<List<RecipesResponseItem>> {
        return apiService.getRecipes()
    }
}