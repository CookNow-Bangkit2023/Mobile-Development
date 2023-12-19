package com.dicoding.cooknow.ui.detailRecipes

import SectionPagerAdapter
import android.graphics.Color
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.dicoding.cooknow.R
import com.dicoding.cooknow.databinding.ActivityDetailRecipesBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.auth.FirebaseAuth
import java.text.DecimalFormat

class DetailRecipesActivity : AppCompatActivity() {
    private var tabLayout: TabLayout? = null
    private var viewPager: ViewPager2? = null

    private lateinit var detailViewModel: DetailViewModel
    private lateinit var rateRecipeViewModel: RateRecipeViewModel
    private lateinit var binding: ActivityDetailRecipesBinding
    private lateinit var auth: FirebaseAuth

    companion object {
        const val EXTRA_RECIPE_ID = "recipe_id"
        const val EXTRA_RANDOM_IMAGE_ID = "random_image_id"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailRecipesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        tabLayout = binding.tabs
        viewPager = findViewById(R.id.view_pager)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        supportActionBar?.apply {
            title = "Detail Recipe"
            setDisplayHomeAsUpEnabled(true)
        }

        // Set warna teks secara langsung pada tab pertama kali dimuat
        tabLayout?.setTabTextColors(Color.BLACK, Color.BLACK)

        // Set data
        detailViewModel = ViewModelProvider(this)[DetailViewModel::class.java]
        rateRecipeViewModel = ViewModelProvider(this)[RateRecipeViewModel::class.java]
        auth = FirebaseAuth.getInstance()

        val randomImageId = intent.getIntExtra(EXTRA_RANDOM_IMAGE_ID, -1)
        val recipe = intent.getIntExtra(EXTRA_RECIPE_ID, -1)

        detailViewModel.detailRecipes.observe(this) { recipe ->

            detailViewModel.isLoading.observe(this){
                showLoading(it)
            }

            if (recipe != null) {
                binding.imgList.setImageResource(randomImageId)
                binding.tvListName.text = recipe.resultRecipe?.name ?: ""

                val averageRating = recipe.averageRating ?: 0.0 // Use the default value as needed
                val decimalFormat = DecimalFormat("#.#")
                val formattedRating = decimalFormat.format(averageRating)
                binding.ratingRate.text = formattedRating

                setupViewPager(viewPager!!)
                val tabTitles = arrayOf("Ingredient", "Procedure")
                TabLayoutMediator(tabLayout!!, viewPager!!) { tab, position ->
                    tab.text = tabTitles[position]
                }.attach()

                tabLayout?.addOnTabSelectedListener(object : OnTabSelectedListener {
                    override fun onTabSelected(tab: TabLayout.Tab) {
                        // Mengubah warna teks untuk tab yang dipilih
                        tabLayout?.setTabTextColors(Color.BLACK, Color.parseColor("#129575"))

                        // Menampilkan fragment sesuai dengan posisi tab yang dipilih
                        viewPager?.currentItem = tab.position
                    }

                    override fun onTabUnselected(tab: TabLayout.Tab) {
                        // Mengembalikan warna teks untuk tab yang tidak dipilih
                        tabLayout?.setTabTextColors(Color.BLACK, Color.BLACK)
                    }

                    override fun onTabReselected(tab: TabLayout.Tab) {
                        // Aksi jika tab sudah dipilih kembali
                    }
                })
            }
        }
        detailViewModel.detailRecipes(recipe)

        val userID =  auth.currentUser!!.uid
        val rateRecipeDialog = RateRecipeDialog(this, rateRecipeViewModel, userID, recipe)

        binding.ratingShape.setOnClickListener{
            rateRecipeDialog.show {rating ->
                rateRecipeViewModel.newRating.observe(this) { newRating ->
                    // Update tampilan rating di sini
                    val decimalFormat = DecimalFormat("#.#")
                    val formattedRating = decimalFormat.format(newRating)
                    binding.ratingRate.text = formattedRating
                }
            }
        }
    }

    private fun showLoading(state: Boolean) {
        binding.progressBar.visibility = if (state) View.VISIBLE else View.GONE
    }

    private fun setupViewPager(viewPager: ViewPager2) {
        val adapter = SectionPagerAdapter(this)
        val recipeId = intent.getIntExtra(EXTRA_RECIPE_ID, -1)
        adapter.addFragment(IngredientFragment.newInstance(recipeId), "Ingredient")
        adapter.addFragment(ProcedureFragment.newInstance(recipeId), "Procedure")
        viewPager.adapter = adapter
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }
}
