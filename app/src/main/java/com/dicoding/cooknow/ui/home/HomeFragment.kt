package com.dicoding.cooknow.ui.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.cooknow.R
import com.dicoding.cooknow.databinding.FragmentHomeBinding
import com.dicoding.cooknow.ui.detailRecipes.DetailRecipesActivity

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var homeViewModel: HomeViewModel
    private var recipeAdapter: ArrayAdapter<String>? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root
        homeViewModel = ViewModelProvider(this)[HomeViewModel::class.java]

        recipeAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, ArrayList())
        binding.recipeList.adapter = recipeAdapter

        with(binding){
            searchView.setupWithSearchBar(searchBar)
            searchView
                .editText
                .setOnEditorActionListener { _, _, _ ->
                    searchBar.setText(searchView.text)
                    homeViewModel.searchRecipes(searchView.text.toString())
                    homeViewModel.searchrecipesResponseItem.observe(viewLifecycleOwner){ recipes ->
                        recipes?.let {
                            val recipeNames = it.map { recipe -> recipe.name }
                            Log.d("HomeFragment", "Recipe names: $recipeNames")
                            recipeAdapter?.clear()
                            recipeAdapter?.addAll(recipeNames)
                            recipeAdapter?.notifyDataSetChanged()
                        }
                    }
                    false
                }
        }


        binding.recipeList.setOnItemClickListener { parent, view, position, id ->
            // Mengambil ID resep berdasarkan posisi yang diklik
            val selectedRecipeId = homeViewModel.searchrecipesResponseItem.value?.get(position)?.id

            // Memeriksa apakah ID resep tidak null sebelum memulai DetailRecipesActivity
            selectedRecipeId?.let {
                val randomImageId = getRandomImageId()

                val intent = Intent(activity, DetailRecipesActivity::class.java)
                intent.putExtra(DetailRecipesActivity.EXTRA_RECIPE_ID, it)
                intent.putExtra(DetailRecipesActivity.EXTRA_RANDOM_IMAGE_ID, randomImageId)
                startActivity(intent)
            }
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        homeViewModel.findRecipes()

        homeViewModel.isLoading.observe(viewLifecycleOwner){
            showLoading(it)
        }

        // RecyclerView for Top Menu
        val topMenuRecyclerView: RecyclerView = view.findViewById(R.id.topMenuRecyclerView)
        val topMenuLayoutManager = GridLayoutManager(context, 2, GridLayoutManager.HORIZONTAL, false)
        topMenuRecyclerView.layoutManager = topMenuLayoutManager

        homeViewModel.recipesResponseItem.observe(viewLifecycleOwner) { recipes ->
            val topMenuAdapter = recipes?.let { FoodAdapter(it, requireContext()) }
            topMenuRecyclerView.adapter = topMenuAdapter
        }
    }

    private fun showLoading(state: Boolean) {
        binding.progressBarTopMenu.visibility = if (state) View.VISIBLE else View.GONE
    }

    private fun getRandomImageId(): Int {
        val imageArray = arrayOf(
            R.drawable.img_1, R.drawable.img_2, R.drawable.img_3,
            R.drawable.img_4, R.drawable.img_5, R.drawable.img_6,
            R.drawable.img_7, R.drawable.img_8, R.drawable.img_9,
            R.drawable.img_10, R.drawable.img_11, R.drawable.img_12,
            R.drawable.img_13, R.drawable.img_14, R.drawable.img_15
        )
        // Mendapatkan indeks acak dari imageArray
        val randomIndex = (0 until imageArray.size).random()
        // Mendapatkan ID gambar dari imageArray sesuai indeks acak
        return imageArray[randomIndex]
    }
}
