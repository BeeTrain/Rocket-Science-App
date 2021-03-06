package ru.chernakov.rocketscienceapp.presentation.info

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_app_info.*
import kotlinx.android.synthetic.main.toolbar_application_info.*
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import ru.chernakov.rocketscienceapp.appmonitor.R
import ru.chernakov.rocketscienceapp.data.model.OptionItem
import ru.chernakov.rocketscienceapp.extension.java.lang.formatToDateString
import ru.chernakov.rocketscienceapp.extension.util.DD_MM_YYYY_HH_MM
import ru.chernakov.rocketscienceapp.presentation.fragment.BaseFragment
import ru.chernakov.rocketscienceapp.presentation.info.adapter.OptionsAdapter
import ru.chernakov.rocketscienceapp.util.lifecycle.SafeObserver

class AppInfoFragment : BaseFragment() {
    private val appInfoViewModel: AppInfoViewModel by viewModel {
        parametersOf(requireArguments().getString(KEY_PACKAGE))
    }

    private val optionsAdapter by lazy { OptionsAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
        rvOptions.apply {
            adapter = optionsAdapter
            layoutManager = LinearLayoutManager(requireContext())
            itemAnimator = DefaultItemAnimator()
        }
        optionsAdapter.onItemClickListener = {
            onOptionSelected(it)
        }

        observeLiveData()
    }

    private fun observeLiveData() {
        appInfoViewModel.appInfoLiveData.observe(viewLifecycleOwner, Observer {
            it?.let {
                ivAppIcon.setImageDrawable(it.icon)
                toolbarTitle.text = it.name
                tvAppPackage.text = it.appPackage
                tvAppVersion.text = getString(R.string.label_app_version, it.version)
                tvAppSha.text = getString(R.string.label_app_sha, it.sha)
                tvAppInstallDate.text =
                    getString(R.string.label_app_installed, it.installDate.formatToDateString(DD_MM_YYYY_HH_MM))
                tvAppUpdateDate.text =
                    getString(R.string.label_app_updated, it.updateDate.formatToDateString(DD_MM_YYYY_HH_MM))
                tvAppDataSize.text = getString(R.string.label_app_size, appInfoViewModel.getAppSize(it.appSize))
                appInfoViewModel.prepareOptions(it)
            }
        })
        appInfoViewModel.optionsLiveData.observe(viewLifecycleOwner, SafeObserver {
            optionsAdapter.setData(it)
        })
    }

    private fun onOptionSelected(optionItem: OptionItem) {
        when (optionItem.action) {
            OptionItem.Action.OpenApp -> requireContext().startActivity(appInfoViewModel.prepareOpenAppIntent())
            OptionItem.Action.OpenMarket -> openInMarket(appInfoViewModel.appPackage)
            OptionItem.Action.DeleteApp -> requireContext().startActivity(appInfoViewModel.prepareUninstallIntent())
        }
    }

    private fun openInMarket(appPackage: String) {
        try {
            requireContext().startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(MARKET_URI + appPackage)))
        } catch (e: ActivityNotFoundException) {
            requireContext().startActivity(
                Intent(Intent.ACTION_VIEW, Uri.parse(PLAY_URI + appPackage))
            )
        }
    }

    override fun getLayout() = R.layout.fragment_app_info

    override fun obtainViewModel() = appInfoViewModel

    companion object {
        private const val KEY_PACKAGE = "PACKAGE"
        private const val MARKET_URI = "market://details?id="
        private const val PLAY_URI = "https://play.google.com/store/apps/details?id="

        fun createArgs(appPackage: String): Bundle {
            return Bundle().apply {
                putString(KEY_PACKAGE, appPackage)
            }
        }
    }
}