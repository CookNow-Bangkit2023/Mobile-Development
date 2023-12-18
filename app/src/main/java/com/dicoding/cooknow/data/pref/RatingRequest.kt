package com.dicoding.cooknow.data.pref

data class RatingRequest(
    val user_id: String,
    val recipe_id: Int,
    val rating: Double
)