package ru.chernakov.rocketscienceapp.domain

import ru.chernakov.rocketscienceapp.R
import ru.chernakov.rocketscienceapp.data.model.ApplicationItem
import ru.chernakov.rocketscienceapp.data.model.OptionItem
import ru.chernakov.rocketscienceapp.data.repository.ApplicationsRepository

class PrepareOptionsInteractor(private val applicationsRepository: ApplicationsRepository) {

    fun prepareOptionsList(applicationItem: ApplicationItem): List<OptionItem> {
        val options = mutableListOf<OptionItem>()
        options.apply {
            add(OptionItem(OptionItem.Action.OpenApp, R.string.title_open_app, R.drawable.ic_open_app))
//            add(OptionItem(OptionItem.Action.SaveApk(true), R.string.title_save_apk, R.drawable.ic_save_apk))
            if (applicationItem.fromPlayMarket) {
                add(OptionItem(OptionItem.Action.OpenMarket, R.string.title_open_market, R.drawable.ic_market))
            }
            add(OptionItem(OptionItem.Action.DeleteApp, R.string.title_delete_app, R.drawable.ic_delete_app))
//            add(OptionItem(OptionItem.Action.ShareApk, R.string.title_share_app, R.drawable.ic_share))
        }

        return options
    }

    fun prepareOpenAppIntent(appPackage: String) = applicationsRepository.getOpenAppIntent(appPackage)

    fun prepareUninstallIntent(appPackage: String) = applicationsRepository.prepareUninstallAppIntent(appPackage)
}