package com.dicoding.cooknow.data.response

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class PredictRecipesResponse(

	@field:SerializedName("PredictRecipesResponse")
	val predictRecipesResponse: List<PredictRecipesResponseItem?>? = null
) : Parcelable

@Parcelize
data class PredictRecipesResponseItem(

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id")
	val id: Int? = null
) : Parcelable
