package com.dicoding.cooknow.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.cooknow.R
import com.dicoding.cooknow.databinding.FragmentHomeBinding
import com.dicoding.cooknow.ui.listRecipes.Food

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root
        homeViewModel = ViewModelProvider(this)[HomeViewModel::class.java]


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

}
