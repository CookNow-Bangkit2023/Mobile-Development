package com.dicoding.cooknow.ui.findRecipes

import android.app.ProgressDialog
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
import com.dicoding.cooknow.databinding.FragmentFindRecipesBinding
import com.dicoding.cooknow.ui.listRecipes.ListRecipesActivity
import com.google.firebase.auth.FirebaseAuth

class FindRecipesFragment : Fragment() {
    private lateinit var binding: FragmentFindRecipesBinding
    private val selectedIngredients = mutableListOf("salt", "pepper", "water", "sugar")
    private lateinit var findRecipesViewModel: FindRecipesViewModel
    private lateinit var auth: FirebaseAuth
    private lateinit var progressDialog: ProgressDialog
    private val allButtons = mutableListOf<Button>()
    private var isSearchActive = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFindRecipesBinding.inflate(inflater, container, false)
        val view = binding.root
        findRecipesViewModel = ViewModelProvider(this)[FindRecipesViewModel::class.java]

        auth = FirebaseAuth.getInstance()

        with(binding){
            searchView.setupWithSearchBar(searchBar)
            searchView
                .editText
                .setOnEditorActionListener { _, _, _ ->
                    val query = searchView.text.toString()
                    handleSearchQuery(query)
                    searchView.hide()
                    false
                }
        }

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

            allButtons.add(button)
            gridLayout.addView(button)
        }
    }

    private fun onIngredientButtonClick(button: Button, ingredient: String) {
        if (isSearchActive) {
            // Reset visibilitas semua tombol
            for (b in allButtons) {
                b.visibility = View.VISIBLE
            }
            isSearchActive = false
        }

        if (selectedIngredients.contains(ingredient)) {
            selectedIngredients.remove(ingredient)
            button.setBackgroundResource(R.drawable.item_button)
        } else {
            Log.e("FindRecipes","Find Recipes $selectedIngredients")
            selectedIngredients.add(ingredient)
            button.setBackgroundResource(R.drawable.after_click)
        }
    }

    private fun handleSearchQuery(query: String) {
        if (query.isNotEmpty()) {
            isSearchActive = true
            for (button in allButtons) {
                val buttonText = button.text.toString().toLowerCase()

                if (buttonText.contains(query.toLowerCase())) {
                    button.visibility = View.VISIBLE
                } else {
                    button.visibility = View.GONE
                }
            }
        } else {
            isSearchActive = false
        }
    }

    private fun setupFindRecipeButton(view: View) {
        val findRecipeButton: Button = view.findViewById(R.id.recipesButton)
        val userID =  auth.currentUser!!.uid
        val loadingDialog = LoadingDialog(requireActivity())

        findRecipeButton.setOnClickListener {
            loadingDialog.showLoadingDialog()
            Log.d("Button_Clicked", "Find Recipe button clicked")
            findRecipesViewModel.addIngredients(selectedIngredients, userID)
            findRecipesViewModel.findRecipes.observe(viewLifecycleOwner) { recipes ->
                loadingDialog.hideDialog()
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
