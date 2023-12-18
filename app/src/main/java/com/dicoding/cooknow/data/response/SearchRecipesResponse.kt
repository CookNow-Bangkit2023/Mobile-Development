package com.dicoding.cooknow.data.response

import com.google.gson.annotations.SerializedName

data class SearchRecipesResponse(

	@field:SerializedName("SearchRecipesResponse")
	val searchRecipesResponse: List<SearchRecipesResponseItem>
)

data class SearchRecipesResponseItem(

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id")
	val id: Int? = null
)
