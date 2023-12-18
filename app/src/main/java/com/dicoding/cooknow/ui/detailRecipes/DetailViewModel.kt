package com.dicoding.cooknow.ui.detailRecipes

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.cooknow.data.api.ApiConfig
import com.dicoding.cooknow.data.response.DetailRecipesResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel: ViewModel() {
    private val _detailRecipes = MutableLiveData<DetailRecipesResponse?>()
    val detailRecipes: MutableLiveData<DetailRecipesResponse?> = _detailRecipes

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun detailRecipes(id: Int){
        _isLoading.postValue(true)
        val response = ApiConfig.getApiService().getDetailRecipes(id)
        response.enqueue(object : Callback<DetailRecipesResponse> {
            override fun onResponse(
                call: Call<DetailRecipesResponse>,
                response: Response<DetailRecipesResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val recipesDetail = response.body()
                    _detailRecipes.value = recipesDetail
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<DetailRecipesResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    companion object {
        private const val TAG = "DetailViewModel"
    }
}