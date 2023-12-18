package com.dicoding.cooknow.data.response

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class GivingRatingResponse(

	@field:SerializedName("message")
	val message: String? = null
) : Parcelable
