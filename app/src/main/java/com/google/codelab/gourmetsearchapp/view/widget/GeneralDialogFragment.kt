package com.google.codelab.gourmetsearchapp.view.widget

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentResultListener
import androidx.fragment.app.setFragmentResult
import androidx.lifecycle.LifecycleOwner
import com.google.codelab.gourmetsearchapp.databinding.FragmentGeneralDialogBinding

class GeneralDialogFragment : DialogFragment() {

    private val title: Int?
        get() = arguments?.getInt(ARG_TITLE)

    private val message: Int?
        get() = arguments?.getInt(ARG_MESSAGE)

    private val positiveButton: Int?
        get() = arguments?.getInt(ARG_POSITIVE_LABEL)

    private val negativeButton: Int?
        get() = arguments?.getInt(ARG_NEGATIVE_LABEL)

    companion object {
        private const val ARG_TITLE = "title"
        private const val ARG_MESSAGE = "message"
        private const val ARG_POSITIVE_LABEL = "positive_label"
        private const val ARG_NEGATIVE_LABEL = "negative_label"
        private const val ARG_POSITIVE_LISTENER = "positive_listener"
        private const val ARG_NEGATIVE_LISTENER = "negative_listener"
        private const val ARG_DISMISS_LISTENER = "dismiss_listener"
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val binding = FragmentGeneralDialogBinding.inflate(LayoutInflater.from(requireContext()))

        binding.apply {
            if (title == 0 || title == null) {
                generalDialogTitle.visibility = View.GONE
            } else {
                title?.let { generalDialogTitle.text = requireContext().getString(it) }
            }

            if (message == 0 || message == null) {
                generalDialogTitle.visibility = View.GONE
            } else {
                message?.let { generalDialogMessage.text = requireContext().getString(it) }
            }

            if (positiveButton == 0 || positiveButton == null) {
                okButton.visibility = View.GONE
            } else {
                positiveButton?.let { okButton.text = requireContext().getString(it) }
                okButton.setOnClickListener {
                    dismiss()
                    setFragmentResult(ARG_POSITIVE_LISTENER, bundleOf())
                }
            }

            if (negativeButton == 0 || negativeButton == null) {
                cancelButton.visibility = View.GONE
            } else {
                cancelButton.text = negativeButton?.let { requireContext().getString(it) }
                cancelButton.setOnClickListener {
                    dismiss()
                    setFragmentResult(ARG_NEGATIVE_LISTENER, bundleOf())
                }
            }
        }

        return AlertDialog.Builder(requireContext())
            .setView(binding.root)
            .create()
    }

    override fun dismiss() {
        super.dismiss()
        setFragmentResult(ARG_DISMISS_LISTENER, bundleOf())
    }

    class Builder private constructor(
        private val fragment: Fragment? = null,
        private val activity: AppCompatActivity? = null
    ) {
        companion object {
            fun from(fragment: Fragment): Builder = Builder(fragment = fragment)
            fun from(activity: AppCompatActivity): Builder = Builder(activity = activity)
        }

        private val bundle = Bundle()

        private val fragmentManager: FragmentManager =
            fragment?.childFragmentManager ?: activity?.supportFragmentManager
            ?: throw IllegalStateException("Required value was null.")

        private val lifecycleOwner: LifecycleOwner =
            fragment?.viewLifecycleOwner ?: activity
            ?: throw IllegalStateException("Required value was null.")

        private fun setResourceId(key: String, @StringRes resourceId: Int): Builder {
            return this.apply {
                bundle.putInt(key, resourceId)
            }
        }

        fun setTitle(@StringRes titleId: Int): Builder {
            return setResourceId(ARG_TITLE, titleId)
        }

        fun setMessage(@StringRes messageId: Int): Builder {
            return setResourceId(ARG_MESSAGE, messageId)
        }

        fun setPositiveButton(
            @StringRes messageId: Int,
            listener: (() -> Unit)? = null
        ): Builder {
            fragmentManager.setFragmentResultListener(
                ARG_POSITIVE_LISTENER, lifecycleOwner,
                FragmentResultListener { _, _ -> listener?.invoke() })
            return setResourceId(ARG_POSITIVE_LABEL, messageId)
        }

        fun setNegativeButton(
            @StringRes messageId: Int,
            listener: (() -> Unit)? = null
        ): Builder {
            fragmentManager.setFragmentResultListener(
                ARG_NEGATIVE_LISTENER, lifecycleOwner,
                FragmentResultListener { _, _ -> listener?.invoke() })
            return setResourceId(ARG_NEGATIVE_LABEL, messageId)
        }

        fun setDismissAction(
            listener: (() -> Unit)? = null
        ): Builder {
            fragmentManager.setFragmentResultListener(
                ARG_DISMISS_LISTENER, lifecycleOwner,
                FragmentResultListener { _, _ -> listener?.invoke() })
            return this
        }

        fun build(): GeneralDialogFragment {
            return GeneralDialogFragment().apply {
                arguments = bundle
            }
        }
    }
}
