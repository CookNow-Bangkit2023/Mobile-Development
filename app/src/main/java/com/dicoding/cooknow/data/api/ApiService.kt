package com.dicoding.cooknow.data.api

import com.dicoding.cooknow.data.pref.RatingRequest
import com.dicoding.cooknow.data.response.DetailRecipesResponse
import com.dicoding.cooknow.data.response.GivingRatingResponse
import com.dicoding.cooknow.data.response.PredictRecipesResponseItem
import com.dicoding.cooknow.data.response.RecipesResponseItem
import com.dicoding.cooknow.data.response.SearchRecipesResponseItem
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @GET("recipes")
    fun getRecipes(): Call<List<RecipesResponseItem>>

    @GET("search/{recipe}")
    fun getSearchRecipe(
        @Path("recipe") recipeName: String
    ): Call<List<SearchRecipesResponseItem>>

    @GET("recipe/rating/{id}")
    fun getDetailRecipes(
        @Path("id") id: Int
    ): Call<DetailRecipesResponse>

    @POST("predict")
    fun postIngredients(
        @Body request: HashMap<String, Any>
    ): Call<List<PredictRecipesResponseItem>>

    @POST("rating")
    fun addRatingRecipe(
        @Body request: RatingRequest
    ): Call<GivingRatingResponse>
}