package com.example.wardrobe.ui.main.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.example.wardrobe.config.SHIRT_FRAGMENT
import com.example.wardrobe.config.TROUSER_FRAGMENT
import com.example.wardrobe.ui.main.ShirtsFragment
import com.example.wardrobe.ui.main.TrousersFragment
import java.lang.IllegalArgumentException

/*private val TAB_TITLES = arrayOf(
        R.string.tab_text_1,
        R.string.tab_text_2
)*/

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
class ImagePagerAdapter(
    private val fragmentType: Int,
    private val mImageListCount: Int,
    fm: FragmentManager
) : FragmentStatePagerAdapter(fm) {

    // private val mImageList: ArrayList<String> = ArrayList()

    override fun getItem(position: Int): Fragment {
        // getItem is called to instantiate the fragment for the given page.
        return when(fragmentType){
            SHIRT_FRAGMENT -> ShirtsFragment.newInstance(
                position
            )
            TROUSER_FRAGMENT -> TrousersFragment.newInstance(
                position
            )
            else -> throw IllegalArgumentException()
        }
    }

    /*override fun getPageTitle(position: Int): CharSequence? {
        return context.resources.getString(TAB_TITLES[position])
    }*/

    override fun getCount(): Int = mImageListCount

}