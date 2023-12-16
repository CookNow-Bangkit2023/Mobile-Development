package com.dicoding.cooknow.data.response

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class DetailRecipesResponse(

	@field:SerializedName("result_recipe")
	val resultRecipe: ResultRecipe? = null,

	@field:SerializedName("average_rating")
	val averageRating: Double? = null
) : Parcelable

@Parcelize
data class ResultRecipe(

	@field:SerializedName("n_steps")
	val nSteps: Int? = null,

	@field:SerializedName("minutes")
	val minutes: Int? = null,

	@field:SerializedName("n_ingridients")
	val nIngridients: Int? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("ingredients")
	val ingredients: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("steps")
	val steps: String? = null
) : Parcelable
