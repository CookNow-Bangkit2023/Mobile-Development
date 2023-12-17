package com.dicoding.cooknow.ui.findRecipes

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.GridLayout
import androidx.fragment.app.Fragment
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.dicoding.cooknow.R
import com.dicoding.cooknow.data.DataUtils
import com.dicoding.cooknow.ui.listRecipes.ListRecipesActivity
import org.json.JSONException
import org.json.JSONObject

class FindRecipesFragment : Fragment() {
    private val selectedIngredients = mutableListOf<String>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_find_recipes, container, false)
        createButtons(view)
        setupFindRecipeButton(view)
        return view
    }

    private fun createButtons(view: View) {
        val gridLayout: GridLayout = view.findViewById(R.id.gridLayout)
        val buttonMargin = resources.getDimensionPixelSize(R.dimen.button_margin)
        val columnCount = 4
        val screenWidth = resources.displayMetrics.widthPixels
        val buttonWidth = (screenWidth - (buttonMargin * (columnCount + 1))) / columnCount
        val rowCount = DataUtils.staticIngredients.size / columnCount

        gridLayout.orientation = GridLayout.HORIZONTAL
        gridLayout.rowCount = rowCount
        gridLayout.columnCount = columnCount

        for ((index, ingredient) in DataUtils.staticIngredients.withIndex()) {
            val button = Button(requireContext())
            button.text = getString(ingredient)
            button.setBackgroundResource(R.drawable.item_button)
            button.setOnClickListener {
                onIngredientButtonClick(button, getString(ingredient))
            }
            button.textSize = resources.getDimension(R.dimen.button_text_size)
            button.setPadding(8, 8, 8, 8)

            val params = GridLayout.LayoutParams()
            params.width = buttonWidth
            params.height = GridLayout.LayoutParams.WRAP_CONTENT
            params.setMargins(
                buttonMargin,
                buttonMargin,
                if (index % columnCount == columnCount - 1) buttonMargin else 0,
                buttonMargin
            )
            button.layoutParams = params

            gridLayout.addView(button)
        }
    }

    private fun onIngredientButtonClick(button: Button, ingredient: String) {
        if (selectedIngredients.contains(ingredient)) {
            selectedIngredients.remove(ingredient)
            button.setBackgroundResource(R.drawable.item_button)
        } else {
            selectedIngredients.add(ingredient)
            button.setBackgroundResource(R.drawable.after_click)
        }
    }

    private fun setupFindRecipeButton(view: View) {
        val findRecipeButton: Button = view.findViewById(R.id.recipesButton)

        findRecipeButton.setOnClickListener {
            Log.d("Button_Clicked", "Find Recipe button clicked")
            sendIngredientsToApi(selectedIngredients)
        }
    }

    private fun sendIngredientsToApi(ingredients: List<String>) {
        val apiUrl = "https://cook-book-now-fkvfn6mtna-uc.a.run.app/api/predict"
        val userId = "IsAY7xHrm2UgqlyM7e0yyyL58hn2"

        val jsonObject = JSONObject()
        jsonObject.put("ingres", ingredients)
        jsonObject.put("user_id", userId)

        val requestQueue = Volley.newRequestQueue(requireContext())
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.POST, apiUrl, jsonObject,
            Response.Listener { response ->
                handleApiResponse(response)
            },
            Response.ErrorListener { error ->
                handleApiError(error)
            })

        requestQueue.add(jsonObjectRequest)
    }

    private fun handleApiResponse(response: JSONObject) {
        val recipes = mutableListOf<String>()

        try {
            val jsonArray = response.getJSONArray("recipes")
            for (i in 0 until jsonArray.length()) {
                recipes.add(jsonArray.getString(i))
            }

            val intent = Intent(activity, ListRecipesActivity::class.java)
            intent.putStringArrayListExtra("RECIPES_LIST", ArrayList(recipes))
            startActivity(intent)
        } catch (e: JSONException) {
            Log.e("JSON_Parse_Error", e.toString())
            // Tindakan penanganan kesalahan jika parsing JSON gagal
        }
    }

    private fun handleApiError(error: VolleyError) {
        Log.e("API_Error", error.toString())
        // Tangani error di sini jika diperlukan
    }
}
