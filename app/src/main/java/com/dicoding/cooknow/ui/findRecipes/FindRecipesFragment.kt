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
import androidx.lifecycle.ViewModelProvider
import com.dicoding.cooknow.R
import com.dicoding.cooknow.data.DataUtils
import com.dicoding.cooknow.ui.home.HomeViewModel
import com.dicoding.cooknow.ui.listRecipes.ListRecipesActivity
import com.google.firebase.auth.FirebaseAuth
import org.json.JSONArray
import org.json.JSONObject

class FindRecipesFragment : Fragment() {
    private val selectedIngredients = mutableListOf("salt", "pepper", "water", "sugar")
    private lateinit var findRecipesViewModel: FindRecipesViewModel
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_find_recipes, container, false)
        findRecipesViewModel = ViewModelProvider(this)[FindRecipesViewModel::class.java]

        auth = FirebaseAuth.getInstance()

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
            Log.e("FindRecipes","Find Recipes $selectedIngredients")
            selectedIngredients.add(ingredient)
            button.setBackgroundResource(R.drawable.after_click)
        }
    }

    private fun setupFindRecipeButton(view: View) {
        val findRecipeButton: Button = view.findViewById(R.id.recipesButton)
        val userID =  auth.currentUser!!.uid

        findRecipeButton.setOnClickListener {
            Log.d("Button_Clicked", "Find Recipe button clicked")
            findRecipesViewModel.addIngredients(selectedIngredients, userID)
            findRecipesViewModel.findRecipes.observe(viewLifecycleOwner) { recipes ->
                val validRecipes = recipes?.filterNotNull()

                val recipeList = ArrayList<Pair<Int, String>>()

                validRecipes?.forEach { recipe ->
                    val id = recipe.id ?: return@forEach
                    val name = recipe.name ?: return@forEach
                    recipeList.add(Pair(id, name))
                }

                val intent = Intent(activity, ListRecipesActivity::class.java)
                intent.putExtra(ListRecipesActivity.EXTRA_LIST_ID, recipeList)
                startActivity(intent)
            }
        }
    }
}
