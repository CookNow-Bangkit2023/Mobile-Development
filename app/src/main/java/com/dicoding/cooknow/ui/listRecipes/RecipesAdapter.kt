package com.dicoding.cooknow.ui.listRecipes

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.cooknow.R
import com.dicoding.cooknow.data.response.PredictRecipesResponseItem
import com.dicoding.cooknow.ui.detailRecipes.DetailRecipesActivity
import com.dicoding.cooknow.ui.detailRecipes.RecipeItemClickListener

class RecipesAdapter(
    private val recipesList: List<Pair<Int, String>>,
    private val context: Context
) : RecyclerView.Adapter<RecipesAdapter.RecipesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipesViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item, parent, false)
        return RecipesViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecipesViewHolder, position: Int) {
        val recipe = recipesList[position]
        holder.textViewRecipe.text = recipe.second

        holder.itemView.setOnClickListener {
            val intent = Intent(context, DetailRecipesActivity::class.java).apply {
                this.putExtra(DetailRecipesActivity.EXTRA_RECIPE_ID, recipe.first)
            }
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return recipesList.size
    }

    class RecipesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var textViewRecipe: TextView = itemView.findViewById(R.id.tv_list_name)
    }
}
