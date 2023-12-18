package com.dicoding.cooknow.ui.listRecipes

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.cooknow.R
import com.dicoding.cooknow.ui.detailRecipes.DetailRecipesActivity
import com.dicoding.cooknow.ui.detailRecipes.RecipeItemClickListener
import com.dicoding.cooknow.ui.findRecipes.FindRecipesFragment
import com.dicoding.cooknow.ui.main.MainActivity

class ListRecipesActivity : AppCompatActivity(), RecipeItemClickListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: RecipesAdapter

    companion object {
        const val EXTRA_LIST_ID = "recipe_id"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_recipes)

        recyclerView = findViewById(R.id.listrecipesRecyclerView)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val recipeList = intent.getSerializableExtra(ListRecipesActivity.EXTRA_LIST_ID) as? List<Pair<Int, String>>


        if (!recipeList.isNullOrEmpty()) {
            val adaptedRecipes = recipeList.map { it } // Keep existing id and name pairs
            adapter = RecipesAdapter(adaptedRecipes, this)
            recyclerView.adapter = adapter
        }

    }

    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    override fun onRecipeItemClicked(recipe: String) {
        val intent = Intent(this, DetailRecipesActivity::class.java)
        intent.putExtra("RECIPE_NAME", recipe)
        startActivity(intent)
    }
}
