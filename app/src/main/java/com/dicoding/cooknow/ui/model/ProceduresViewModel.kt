package com.dicoding.cooknow.ui.model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.cooknow.data.api.ApiConfig
import com.dicoding.cooknow.data.response.DetailRecipesResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProceduresViewModel: ViewModel() {
    private val _proceduresList = MutableLiveData<List<String>>()
    val proceduresList: LiveData<List<String>> = _proceduresList

    private val _procedureCount = MutableLiveData<Int>()
    val procedureCount: LiveData<Int> = _procedureCount

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getProcedure(id: Int) {
        _isLoading.postValue(true)
        val response = ApiConfig.getApiService().getDetailRecipes(id)
        response.enqueue(object : Callback<DetailRecipesResponse> {
            override fun onResponse(
                call: Call<DetailRecipesResponse>,
                response: Response<DetailRecipesResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val procedureList = response.body()?.resultRecipe?.steps
                    if (!procedureList.isNullOrBlank()) {
                        // Convert the string representation of a list into an actual list
                        val procedures = parseProceduresList(procedureList)
                        _proceduresList.value = procedures
                        _procedureCount.value = procedures.size
                    } else {
                        _proceduresList.value = emptyList()
                        _procedureCount.value = 0
                    }
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

    private fun parseProceduresList(procedureString: String): List<String> {
        // Use regex to match content inside single quotes
        val regex = Regex("'(.*?)'")
        val matches = regex.findAll(procedureString)

        // Extract matched content and map it to a list of strings
        return matches.map { it.groupValues[1] }.toList()
    }

    companion object {
        private const val TAG = "ProcedureViewModel"
    }
}