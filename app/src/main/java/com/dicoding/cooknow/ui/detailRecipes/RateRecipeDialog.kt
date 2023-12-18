package com.dicoding.cooknow.ui.detailRecipes

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.dicoding.cooknow.databinding.RatingDialogBinding

class RateRecipeDialog(private val context: Context, private val rateRecipeViewModel: RateRecipeViewModel, private val userId: String, private val recipeId: Int)
    : DialogFragment() {
    private lateinit var binding: RatingDialogBinding

    fun show(onRatingSubmitted: (Float) -> Unit){
        val inflater = LayoutInflater.from(context)
        binding = RatingDialogBinding.inflate(inflater)

        val builder = AlertDialog.Builder(context)
        builder.setView(binding.root)

        val dialog = builder.create()

        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        binding.rateYesBtn.setOnClickListener {
            val rating = binding.ratingBar.rating
            val doubleRating: Double = rating.toDouble()
            onRatingSubmitted(rating)
            rateRecipeViewModel.addRatingRecipe(userId, recipeId, doubleRating)
            dialog.dismiss()
        }

        binding.rateNoBtn.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }
}