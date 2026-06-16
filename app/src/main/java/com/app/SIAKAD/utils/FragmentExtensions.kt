package com.app.SIAKAD.utils

import androidx.fragment.app.Fragment
import com.app.SIAKAD.R

fun Fragment.replaceFragment(fragment: Fragment, addToBackStack: Boolean = true) {
    parentFragmentManager.beginTransaction().apply {
        replace(R.id.nav_host_fragment, fragment)
        if (addToBackStack) {
            addToBackStack(null)
        }
        commit()
    }
}