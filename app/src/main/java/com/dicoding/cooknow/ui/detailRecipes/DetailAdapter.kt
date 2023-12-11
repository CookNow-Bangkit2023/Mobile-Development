package com.dicoding.cooknow.ui.detailRecipes

import android.content.Context
import android.os.Bundle
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.dicoding.cooknow.R

class DetailAdapter(private val applicationContext: Context, fragmentManager: FragmentManager, initialDataBundle: Bundle) : FragmentPagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    private var dataBundle: Bundle

    init {
        dataBundle = initialDataBundle
    }

    @StringRes
    private val TAB_TITLES = intArrayOf(R.string.tab_1, R.string.tab_2)

    override fun getCount(): Int = 2

    override fun getItem(position: Int): Fragment {
        var selectedFragment: Fragment? = null
        when(position) {
            0 -> selectedFragment = IngredientFragment()
            1 -> selectedFragment = ProcedureFragment()
        }
        selectedFragment?.arguments = this.dataBundle
        return selectedFragment as Fragment
    }


    override fun getPageTitle(position: Int): CharSequence? {
        return applicationContext.resources.getString(TAB_TITLES[position])
    }
}
