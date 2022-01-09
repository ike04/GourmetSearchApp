package com.google.codelab.gourmetsearchapp.view.settings

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import autoCleared
import com.google.codelab.gourmetsearchapp.databinding.FragmentSettingsBinding
import com.google.codelab.gourmetsearchapp.view.onboarding.OnboardingActivity
import com.google.codelab.gourmetsearchapp.view.settings.SettingsList.*
import com.google.codelab.gourmetsearchapp.viewmodel.SettingsViewModel
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.OnItemClickListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingsFragment : Fragment() {
    private val viewModel: SettingsViewModel by viewModels()
    private var binding: FragmentSettingsBinding by autoCleared()
    private val groupAdapter = GroupAdapter<GroupieViewHolder>()

    private val onItemClickListener = OnItemClickListener { item, _ ->
        val index = groupAdapter.getAdapterPosition(item)
        SettingsList.fromPosition(index)?.let { tappedAction(it) }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerView.apply {
            adapter = groupAdapter
            layoutManager =
                GridLayoutManager(requireContext(), 1, GridLayoutManager.VERTICAL, false)
        }

        groupAdapter.update(SettingsList.values().map { SettingListItemFactory(it) })

        groupAdapter.setOnItemClickListener(onItemClickListener)
    }

    private fun tappedAction(menu: SettingsList) {
        when (menu) {
            APP_SETTING -> {
                val intent = Intent()
                intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                val uri = Uri.fromParts(
                    "package",
                    requireContext().packageName,
                    null
                )
                intent.data = uri
                startActivity(intent)
            }
            ONBOARDING -> {
                val intent = Intent(requireContext(), OnboardingActivity::class.java)
                startActivity(intent)
            }
            LICENSE -> {
                val intent = Intent(requireContext(), OssLicenseActivity::class.java)
                startActivity(intent)
            }
        }
    }
}

