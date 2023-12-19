package com.dicoding.cooknow.ui.listRecipes

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.cooknow.R
import com.dicoding.cooknow.ui.detailRecipes.DetailRecipesActivity
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

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        recyclerView = findViewById(R.id.listrecipesRecyclerView)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val recipeList = intent.getSerializableExtra(ListRecipesActivity.EXTRA_LIST_ID) as? List<Pair<Int, String>>

        if (!recipeList.isNullOrEmpty()) {
            val adaptedRecipes = recipeList.map { it } // Keep existing id and name pairs
            adapter = RecipesAdapter(adaptedRecipes, this)
            recyclerView.adapter = adapter
        }

        supportActionBar?.apply {
            title = "List Recipes"
            setDisplayHomeAsUpEnabled(true)
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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }
}
