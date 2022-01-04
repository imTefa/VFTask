package com.example.vftask.features

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

open class BaseFragment : Fragment() {

    fun setupSupportActionBar(
        title: String = "",
        displayHomeAsUpEnabled: Boolean = false
    ) {
        val activity = requireActivity()
        if (activity is AppCompatActivity) {
            activity.supportActionBar?.let { actionBar ->
                actionBar.title = title
                actionBar.setDisplayHomeAsUpEnabled(displayHomeAsUpEnabled)
            }
        }
    }

}