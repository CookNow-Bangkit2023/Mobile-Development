package com.dicoding.cooknow.data.api

import com.dicoding.cooknow.data.response.DetailRecipesResponse
import com.dicoding.cooknow.data.response.PredictRecipesResponse
import com.dicoding.cooknow.data.response.RecipesResponseItem
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @GET("recipes")
    fun getRecipes(): Call<List<RecipesResponseItem>>

    @GET("recipe/rating/{id}")
    fun getDetailRecipes(
        @Path("id") id: Int
    ): Call<DetailRecipesResponse>

    @POST("predict")
    fun postIngredients(
        @Body request: Map<String, String>
    ): Call<PredictRecipesResponse>
}