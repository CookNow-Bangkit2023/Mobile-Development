package com.dicoding.cooknow.ui.findRecipes

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.GridLayout
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.cooknow.R
import com.dicoding.cooknow.data.DataUtils
import com.dicoding.cooknow.data.api.ApiConfig
import com.dicoding.cooknow.ui.listRecipes.ListRecipesActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.gson.Gson

class FindRecipesFragment : Fragment() {
    private val selectedIngredients = mutableListOf<String>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_find_recipes, container, false) // Ganti dengan layout fragment Anda
        createButtons(view)
        setupFindRecipeButton(view)
        return view
    }

    private fun createButtons(view: View) {
        val buttonGrid: GridLayout = view.findViewById(R.id.buttonGrid)
        val columnCount = 4

        for (ingredient in DataUtils.staticIngredients) {
            val button = Button(requireContext())
            button.text = getString(ingredient)
            button.setBackgroundResource(R.drawable.item_button) // Ganti dengan drawable shape yang diinginkan
            button.setOnClickListener { onIngredientButtonClick(getString(ingredient)) }

            val params = GridLayout.LayoutParams()
            params.width = 0
            params.height = GridLayout.LayoutParams.WRAP_CONTENT
            params.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f / columnCount)
            button.layoutParams = params

            buttonGrid.addView(button)
        }
    }


    private fun onIngredientButtonClick(ingredient: String) {
        if (selectedIngredients.contains(ingredient)) {
            selectedIngredients.remove(ingredient)
        } else {
            selectedIngredients.add(ingredient)
        }
    }

    private fun setupFindRecipeButton(view: View) {
        val findRecipeButton: Button = view.findViewById(R.id.recipesButton) // Ganti dengan ID button Anda

        findRecipeButton.setOnClickListener {
            // Panggil fungsi untuk mengirim data ke API
            sendIngredientsToApi(selectedIngredients)
        }
    }

    private fun sendIngredientsToApi(ingredients: List<String>) {

    }
}

