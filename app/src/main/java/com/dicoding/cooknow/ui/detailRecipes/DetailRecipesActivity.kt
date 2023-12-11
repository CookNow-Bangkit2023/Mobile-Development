package com.dicoding.cooknow.ui.detailRecipes

import android.graphics.Color
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.dicoding.cooknow.R
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener

class DetailRecipesActivity : AppCompatActivity() {
    private var tabLayout: TabLayout? = null
    private var viewPager: ViewPager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_recipes)
        tabLayout = findViewById(R.id.tabs)
        viewPager = findViewById(R.id.view_pager)

        // Set warna teks secara langsung pada tab pertama kali dimuat
        tabLayout?.setTabTextColors(Color.BLACK, Color.BLACK)

        setupViewPager(viewPager)
        tabLayout?.setupWithViewPager(viewPager)
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

        val imageBack: ImageView = findViewById(R.id.imageBack)
        imageBack.setOnClickListener {
            onBackPressed() // Ganti dengan aksi yang ingin Anda lakukan
        }
    }

    private fun setupViewPager(viewPager: ViewPager?) {
        viewPager?.let {
            val adapter = ViewPagerAdapter(
                supportFragmentManager
            )
            adapter.addFragment(IngredientFragment(), "Ingredient")
            adapter.addFragment(ProcedureFragment(), "Procedure")
            it.adapter = adapter
        }
    }

    internal class ViewPagerAdapter(manager: FragmentManager) :
        FragmentPagerAdapter(manager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
        private val mFragmentList: MutableList<Fragment> = ArrayList()
        private val mFragmentTitleList: MutableList<String> = ArrayList()

        override fun getItem(position: Int): Fragment {
            return mFragmentList[position]
        }

        override fun getCount(): Int {
            return mFragmentList.size
        }

        fun addFragment(fragment: Fragment, title: String) {
            mFragmentList.add(fragment)
            mFragmentTitleList.add(title)
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return mFragmentTitleList[position]
        }
    }
}
