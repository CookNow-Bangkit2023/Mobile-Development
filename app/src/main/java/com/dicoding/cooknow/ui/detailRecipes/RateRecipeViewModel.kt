package com.dicoding.cooknow.ui.detailRecipes

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.cooknow.data.api.ApiConfig
import com.dicoding.cooknow.data.pref.RatingRequest
import com.dicoding.cooknow.data.response.GivingRatingResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RateRecipeViewModel: ViewModel() {
    private val _addRating = MutableLiveData<GivingRatingResponse?>()
    val addRating: MutableLiveData<GivingRatingResponse?> = _addRating

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun addRatingRecipe(user_uid: String, recipe_id: Int, rating: Double){
        val request = RatingRequest(user_uid, recipe_id, rating)
        val response = ApiConfig.getApiService().addRatingRecipe(request)
        response.enqueue(object : Callback<GivingRatingResponse> {
            override fun onResponse(
                call: Call<GivingRatingResponse>,
                response: Response<GivingRatingResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val add = response.body()
                    _addRating.value = add
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<GivingRatingResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    companion object {
        private const val TAG = "RateRecipeViewModel"
    }
}