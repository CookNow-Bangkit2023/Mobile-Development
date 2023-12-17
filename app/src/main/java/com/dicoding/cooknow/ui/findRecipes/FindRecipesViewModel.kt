package com.dicoding.cooknow.ui.findRecipes

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.cooknow.data.api.ApiConfig
import com.dicoding.cooknow.data.response.PredictRecipesResponseItem
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FindRecipesViewModel() : ViewModel() {
    private val _findRecipes = MutableLiveData<List<PredictRecipesResponseItem>?>()
    val findRecipes: LiveData<List<PredictRecipesResponseItem>?> = _findRecipes

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun addIngredients(ingredients: List<String>, user_uid: String){
        val requestMap = hashMapOf(
            "ingres" to ingredients,
            "user_id" to user_uid
        )

        val response = ApiConfig.getApiService().postIngredients(requestMap)
        response.enqueue(object : Callback<List<PredictRecipesResponseItem>> {
            override fun onResponse(
                call: Call<List<PredictRecipesResponseItem>>,
                response: Response<List<PredictRecipesResponseItem>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val recipes = response.body()
                    _findRecipes.value = recipes
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<PredictRecipesResponseItem>>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    companion object {
        private const val TAG = "FindRecipesViewModel"
    }
}