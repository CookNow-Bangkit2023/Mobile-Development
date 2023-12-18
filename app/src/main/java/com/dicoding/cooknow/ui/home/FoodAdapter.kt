package com.dicoding.cooknow.ui.home

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.cooknow.R
import com.dicoding.cooknow.data.response.RecipesResponseItem
import com.dicoding.cooknow.ui.detailRecipes.DetailRecipesActivity

class FoodAdapter(private val foodList: List<RecipesResponseItem>, private val context: Context) :
    RecyclerView.Adapter<FoodAdapter.FoodViewHolder>() {

    private val imageArray = arrayOf(
        R.drawable.img_1, R.drawable.img_2, R.drawable.img_3,
        R.drawable.img_4, R.drawable.img_5, R.drawable.img_6,
        R.drawable.img_7, R.drawable.img_8, R.drawable.img_9,
        R.drawable.img_10, R.drawable.img_11, R.drawable.img_12,
        R.drawable.img_13, R.drawable.img_14, R.drawable.img_15
    )

    private val randomImageIds = HashMap<Int, Int>()

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

        val randomImageId = randomImageIds[position] ?: imageArray.random()

        randomImageIds[position] = randomImageId
        holder.foodImageView.setImageResource(randomImageId)

        holder.foodNameTv.text = recipe.name

        holder.itemView.setOnClickListener {
            val intent = Intent(context, DetailRecipesActivity::class.java).apply {
                this.putExtra(DetailRecipesActivity.EXTRA_RECIPE_ID, recipe.id)
                this.putExtra(DetailRecipesActivity.EXTRA_RANDOM_IMAGE_ID, randomImageId)
            }
            context.startActivity(intent)
        }
    }
}
