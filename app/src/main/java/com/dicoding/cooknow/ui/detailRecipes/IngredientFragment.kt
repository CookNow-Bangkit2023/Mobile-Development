package com.dicoding.cooknow.ui.detailRecipes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.cooknow.R

class IngredientFragment : Fragment() {

    // Deklarasi list untuk menyimpan data bahan
    private lateinit var ingredientList: ArrayList<String>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val view = inflater.inflate(R.layout.fragment_ingredient, container, false)
        val recyclerView: RecyclerView = view.findViewById(R.id.rv_ingredient)

        // Inisialisasi ingredientList dan tambahkan data bahan contoh
        ingredientList = ArrayList()
        ingredientList.add("Ingredient 1")
        ingredientList.add("Ingredient 2")
        ingredientList.add("Ingredient 3")

        val adapter = IngredientAdapter(ingredientList)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        return view
    }

    class IngredientAdapter(private val ingredients: List<String>) :
        RecyclerView.Adapter<IngredientAdapter.IngredientViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_ingredient, parent, false)
            return IngredientViewHolder(view)
        }

        override fun onBindViewHolder(holder: IngredientViewHolder, position: Int) {
            holder.bind(ingredients[position])
        }

        override fun getItemCount(): Int = ingredients.size

        inner class IngredientViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            private val ingredientTextView: TextView = itemView.findViewById(R.id.tv_ingredient)

            fun bind(ingredient: String) {
                ingredientTextView.text = ingredient
            }
        }
    }
}
