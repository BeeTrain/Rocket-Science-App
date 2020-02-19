package ru.chernakov.feature_app_appmonitor.presentation.info

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_app_info.*
import kotlinx.android.synthetic.main.toolbar_application_info.*
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import ru.chernakov.core_base.extension.java.lang.formatToDateString
import ru.chernakov.core_base.extension.java.util.DD_MM_YYYY_HH_MM
import ru.chernakov.core_base.util.lifecycle.SafeObserver
import ru.chernakov.core_ui.presentation.fragment.BaseFragment
import ru.chernakov.feature_app_appmonitor.R
import ru.chernakov.feature_app_appmonitor.data.model.OptionItem
import ru.chernakov.feature_app_appmonitor.presentation.info.adapter.OptionsAdapter

class AppInfoFragment : BaseFragment() {
    private val appInfoViewModel: AppInfoViewModel by viewModel {
        parametersOf(requireArguments().getString(KEY_PACKAGE))
    }

    private val optionsAdapter by lazy { OptionsAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toolbar.setNavigationOnClickListener {
            requireActivity().onBackPressed()
        }

        appInfoViewModel.appInfoLiveData.observe(viewLifecycleOwner, Observer {
            if (it != null) {
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
            initOptionsList(it)
        })
    }

    private fun initOptionsList(options: List<OptionItem>) {
        rvOptions.apply {
            adapter = optionsAdapter
            layoutManager = LinearLayoutManager(requireContext())
            itemAnimator = DefaultItemAnimator()
        }
        optionsAdapter.apply {
            onItemClickListener = {
                onOptionSelected(it)
            }
            setData(options)
        }
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
    }
}