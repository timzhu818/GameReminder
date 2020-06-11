package com.example.gamereminder.fragments

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.gamereminder.MainActivity
import com.example.gamereminder.R

abstract class BaseFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(getContainerId(), container, false)
    }

    protected abstract fun getContainerId(): Int

    fun handleError(errorMessage: String) {
        AlertDialog.Builder(context).setTitle("Oops! There's some error...")
            .setMessage(errorMessage)
            .setNegativeButton(R.string.dismiss_text, null)
            .setIcon(android.R.drawable.stat_notify_error)
            .show()
    }

    fun handleAction(
        title: String,
        message: String,
        positiveText: String,
        positiveAction: () -> Unit
    ) {
        AlertDialog.Builder(context).setTitle(title)
            .setMessage(message)
            .setPositiveButton(positiveText) { _, _ ->
                positiveAction.invoke()
            }
            .setNegativeButton(
                R.string.cancel_text, null
            )
            .setIcon(android.R.drawable.ic_menu_edit)
            .show()
    }

    fun showHideProgressBar(show: Boolean) {
        (activity as? MainActivity)?.showHideProgressBar(show)
    }

}