package com.dicoding.cooknow.ui.detailRecipes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.cooknow.R
import com.dicoding.cooknow.databinding.FragmentHomeBinding
import com.dicoding.cooknow.databinding.FragmentIngredientBinding
import com.dicoding.cooknow.ui.home.HomeViewModel
import com.dicoding.cooknow.ui.model.IngredientsViewModel

class IngredientFragment : Fragment() {

    // Deklarasi list untuk menyimpan data bahan
    private lateinit var ingredientList: ArrayList<String>
    private lateinit var ingredientsViewModel: IngredientsViewModel
    private lateinit var binding: FragmentIngredientBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentIngredientBinding.inflate(inflater, container, false)
        val view = binding.root
        val recyclerView: RecyclerView = view.findViewById(R.id.rv_ingredient)

        ingredientsViewModel = ViewModelProvider(this)[IngredientsViewModel::class.java]

        val adapter = IngredientAdapter(emptyList())
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        ingredientsViewModel.ingredientsList.observe(viewLifecycleOwner) { ingredients ->
            // Update your UI with the list of ingredients
            // For example, you can use this list to populate a RecyclerView or other UI elements
            adapter.updateData(ingredients)
        }

        return view
    }

    class IngredientAdapter(private var ingredients: List<String>) :
        RecyclerView.Adapter<IngredientAdapter.IngredientViewHolder>() {

        fun updateData(newData: List<String>) {
            ingredients = newData
            notifyDataSetChanged()
        }

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
