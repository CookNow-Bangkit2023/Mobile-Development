package com.dicoding.cooknow.ui.listRecipes

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
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

    private val imageArray = arrayOf(
        R.drawable.img_1, R.drawable.img_2, R.drawable.img_3,
        R.drawable.img_4, R.drawable.img_5, R.drawable.img_6,
        R.drawable.img_7, R.drawable.img_8, R.drawable.img_9,
        R.drawable.img_10, R.drawable.img_11, R.drawable.img_12,
        R.drawable.img_13, R.drawable.img_14, R.drawable.img_15
    )

    private val randomImageIds = HashMap<Int, Int>()

    class RecipesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val foodImageListView: ImageView = itemView.findViewById(R.id.img_list)
        var textViewRecipe: TextView = itemView.findViewById(R.id.tv_list_name)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipesViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item, parent, false)
        return RecipesViewHolder(view)
    }

    override fun getItemCount(): Int {
        return recipesList.size
    }

    override fun onBindViewHolder(holder: RecipesViewHolder, position: Int) {
        val recipe = recipesList[position]

        val randomListImageId = randomImageIds[position] ?: imageArray.random()

        randomImageIds[position] = randomListImageId
        holder.foodImageListView.setImageResource(randomListImageId)

        holder.textViewRecipe.text = recipe.second

        holder.itemView.setOnClickListener {
            val intent = Intent(context, DetailRecipesActivity::class.java).apply {
                this.putExtra(DetailRecipesActivity.EXTRA_RECIPE_ID, recipe.first)
                this.putExtra(DetailRecipesActivity.EXTRA_RANDOM_IMAGE_ID, randomListImageId)
            }
            context.startActivity(intent)
        }
    }
}
