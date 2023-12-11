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

class ListRecipesActivity : AppCompatActivity(), RecipeItemClickListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var recipesList: MutableList<String>
    private lateinit var adapter: RecipesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_recipes)

        recyclerView = findViewById(R.id.listrecipesRecyclerView)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)

        recipesList = ArrayList()
        recipesList.add("Resep 1")
        recipesList.add("Resep 2")
        recipesList.add("Resep 3")
        // Add recipes as needed

        adapter = RecipesAdapter(recipesList, this)
        recyclerView.adapter = adapter

        val imageBack: ImageView = findViewById(R.id.imageBack)
        imageBack.setOnClickListener {
            onBackPressed()
        }
    }

    override fun onRecipeItemClicked(recipe: String) {
        val intent = Intent(this, DetailRecipesActivity::class.java)
        intent.putExtra("RECIPE_NAME", recipe)
        startActivity(intent)
    }
}
