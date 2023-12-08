// HomeFragment.kt
package com.dicoding.cooknow.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.cooknow.R

class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        // RecyclerView for Recommendation
        val recommendationRecyclerView: RecyclerView = view.findViewById(R.id.recommendationRecyclerView)
        val recommendationLayoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        recommendationRecyclerView.layoutManager = recommendationLayoutManager

        // Inisialisasi data untuk RecyclerView Rekomendasi (contoh data sementara)
        val recommendationData = listOf(
            Food(R.drawable.img, "Menu 1"),
            Food(R.drawable.img, "Menu 2"),
            Food(R.drawable.img, "Menu 3"),
            // ...Tambahkan data makanan lainnya
        )

        val recommendationAdapter = FoodAdapter(recommendationData)
        recommendationRecyclerView.adapter = recommendationAdapter

        // RecyclerView for Top Menu
        val topMenuRecyclerView: RecyclerView = view.findViewById(R.id.topMenuRecyclerView)
        val topMenuLayoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        topMenuRecyclerView.layoutManager = topMenuLayoutManager

        // Inisialisasi data untuk RecyclerView Menu Teratas (contoh data sementara)
        val topMenuData = listOf(
            Food(R.drawable.img, "Menu 1"),
            Food(R.drawable.img, "Menu 2"),
            Food(R.drawable.img, "Menu 3"),
            // ...Tambahkan data menu lainnya
        )

        val topMenuAdapter = FoodAdapter(topMenuData)
        topMenuRecyclerView.adapter = topMenuAdapter

        return view
    }

    companion object {
        @JvmStatic
        fun newInstance() = HomeFragment()
    }
}
