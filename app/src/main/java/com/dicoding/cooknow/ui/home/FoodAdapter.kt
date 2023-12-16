package com.dicoding.cooknow.ui.home

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.cooknow.R
import com.dicoding.cooknow.data.response.RecipesResponseItem
import com.dicoding.cooknow.ui.listRecipes.Food
import com.dicoding.cooknow.ui.detailRecipes.DetailRecipesActivity

class FoodAdapter(private val foodList: List<RecipesResponseItem>, private val context: Context) :
    RecyclerView.Adapter<FoodAdapter.FoodViewHolder>() {

    class FoodViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val foodImageView: ImageView = itemView.findViewById(R.id.img_meal)
        val foodNameTv: TextView = itemView.findViewById(R.id.tv_meal_name)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.food_card, parent, false)
        return FoodViewHolder(view)
    }

    override fun getItemCount(): Int {
        return foodList.size
    }

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        val recipe  = foodList[position]
        holder.foodImageView.setImageResource(R.drawable.img)
        holder.foodNameTv.text = recipe.name

        holder.itemView.setOnClickListener {
            val intent = Intent(context, DetailRecipesActivity::class.java).apply {
                this.putExtra(DetailRecipesActivity.EXTRA_RECIPE_ID, recipe.id)
            }
            context.startActivity(intent)
        }
    }
}
