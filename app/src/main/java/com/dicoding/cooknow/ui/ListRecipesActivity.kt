package com.dicoding.cooknow.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.cooknow.R

class ListRecipesActivity : AppCompatActivity() {
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
        // Ganti data dummy ini dengan data sesuai kebutuhan Anda
        recipesList.add("Resep 1")
        recipesList.add("Resep 2")
        recipesList.add("Resep 3")
        recipesList.add("Resep 4")
        recipesList.add("Resep 5")
        recipesList.add("Resep 6")
        recipesList.add("Resep 7")
        recipesList.add("Resep 8")
        recipesList.add("Resep 9")
        adapter = RecipesAdapter(recipesList)
        recyclerView.adapter = adapter
    }
}