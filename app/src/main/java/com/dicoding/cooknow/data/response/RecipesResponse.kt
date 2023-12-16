package com.dicoding.cooknow.data.response

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class RecipesResponse(
	@field:SerializedName("RecipesResponse")
	val recipesResponse: List<RecipesResponseItem>
) : Parcelable

@Parcelize
data class RecipesResponseItem(
	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,
) : Parcelable
