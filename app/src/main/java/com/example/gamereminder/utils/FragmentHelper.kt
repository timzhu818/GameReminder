package com.example.gamereminder.utils

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import com.example.gamereminder.R

fun Fragment.navigationLeftToRight(id: Int, bundle: Bundle? = null) {
    val options = navOptions {
        anim {
            enter = R.anim.slide_in_right
            exit = R.anim.slide_out_left
            popEnter = R.anim.slide_in_left
            popExit = R.anim.slide_out_right
        }
    }
    findNavController().navigate(id, bundle, options)
}

fun Fragment.navigationInAndOut(id: Int, bundle: Bundle? = null) {
    val options = navOptions {
        anim {
            enter = R.anim.fade_in
            exit = R.anim.fade_out
            popEnter = R.anim.fade_out
            popExit = R.anim.fade_in
        }
    }
    findNavController().navigate(id, bundle, options)
}