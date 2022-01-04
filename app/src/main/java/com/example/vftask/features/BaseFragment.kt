package com.example.vftask.features

import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar

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


    fun showErrorMessage(view: View, errorMessage: String) {
        Snackbar.make(view, errorMessage, Snackbar.LENGTH_LONG).show()
    }

}