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
                        // Ubah representasi string dari list menjadi list yang sebenarnya
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
        // Remove square brackets from the entire string
        val sanitizedString = procedureString.removeSurrounding("[", "]")

        val steps = mutableListOf<String>()
        var insideQuotes = false
        val builder = StringBuilder()

        for (char in sanitizedString) {
            when {
                char == '\'' -> insideQuotes = !insideQuotes
                char == ',' && !insideQuotes -> {
                    val step = builder.toString().trim()
                    if (step.isNotBlank()) {
                        steps.add(step)
                    }
                    builder.clear()
                }
                else -> builder.append(char)
            }
        }

        // Add the last step
        val lastStep = builder.toString().trim()
        if (lastStep.isNotBlank()) {
            steps.add(lastStep)
        }

        // Handle multiline steps by splitting on newline characters
        val finalSteps = mutableListOf<String>()
        steps.forEach { step ->
            finalSteps.addAll(step.split('\n').map { it.trim() })
        }

        return finalSteps
    }

    companion object {
        private const val TAG = "ProcedureViewModel"
    }
}