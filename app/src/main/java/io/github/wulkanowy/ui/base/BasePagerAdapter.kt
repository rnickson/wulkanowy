package io.github.wulkanowy.ui.base

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter

class BasePagerAdapter(fragmentManager: FragmentManager) : FragmentStatePagerAdapter(fragmentManager) {

    val fragments = mutableMapOf<String?, Fragment>()

    override fun getItem(position: Int) = fragments.values.elementAt(position)

    override fun getCount() = fragments.size

    override fun getPageTitle(position: Int): CharSequence? {
        return fragments.keys.elementAtOrNull(position)
    }
}
