package com.dicoding.cooknow.data.api

import com.dicoding.cooknow.data.response.DetailRecipesResponse
import com.dicoding.cooknow.data.response.RecipesResponse
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

    @POST("predict") // Menambahkan method POST
    fun postIngredients(
        @Body request: Map<String, Any>
    ): Call<RecipesResponse> // Sesuaikan dengan respons yang diharapkan
}