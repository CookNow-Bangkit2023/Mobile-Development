package com.dicoding.cooknow.data

import android.content.Context
import com.dicoding.cooknow.R

object DataUtils {

    val staticIngredients = listOf(
        R.string.onion, R.string.garlic, R.string.chicken_broth, R.string.dijon_mustard,
        R.string.egg, R.string.flour, R.string.cheddar_cheese, R.string.chicken_breast,
        R.string.olive_oil, R.string.milk, R.string.bacon, R.string.granulated_sugar,
        R.string.lemon_juice, R.string.all_purpose_flour, R.string.oil, R.string.basil,
        R.string.carrot, R.string.bell_pepper, R.string.wine, R.string.paprika,
        R.string.tomato, R.string.parmesan_cheese, R.string.unsalted_butter, R.string.orange_juice,
        R.string.potato, R.string.parsley, R.string.vanilla, R.string.zucchini,
        R.string.mayonnaise, R.string.cream_cheese, R.string.mushroom, R.string.mustard,
        R.string.baking_powder, R.string.celery, R.string.lime_juice, R.string.corn,
        R.string.sour_cream, R.string.duck, R.string.baking_soda,
        R.string.boneless_chicken_breast, R.string.ginger, R.string.cilantro,
        R.string.cumin, R.string.soy_sauce, R.string.chili_powder,
        R.string.honey, R.string.cinnamon, R.string.worcestershire_sauce,
        R.string.beef, R.string.extra_virgin_olive_oil, R.string.kosher_salt,
        R.string.garlic_powder, R.string.vegetable_oil, R.string.bean
    )

    fun getIngredientStrings(context: Context): List<String> {
        return staticIngredients.map { context.getString(it) }
    }

    fun getAllIngredientsString(context: Context): String {
        return getIngredientStrings(context).joinToString(", ")
    }

}