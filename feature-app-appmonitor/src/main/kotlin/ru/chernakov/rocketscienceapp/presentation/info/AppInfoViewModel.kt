package ru.chernakov.rocketscienceapp.presentation.info

import androidx.lifecycle.MutableLiveData
import ru.chernakov.rocketscienceapp.data.model.ApplicationItem
import ru.chernakov.rocketscienceapp.data.model.OptionItem
import ru.chernakov.rocketscienceapp.domain.LoadAppInfoInteractor
import ru.chernakov.rocketscienceapp.domain.PrepareOptionsInteractor
import ru.chernakov.rocketscienceapp.presentation.viewmodel.BaseViewModel
import java.math.BigDecimal

class AppInfoViewModel(
    val appPackage: String,
    private val loadAppInfoInteractor: LoadAppInfoInteractor,
    private val prepareOptionsInteractor: PrepareOptionsInteractor
) : BaseViewModel() {
    val appInfoLiveData = MutableLiveData<ApplicationItem?>()
    val optionsLiveData = MutableLiveData<List<OptionItem>>()

    init {
        loading.value = true
        loadAppInfo()
    }

    private fun loadAppInfo() {
        launchLoadingErrorJob {
            appInfoLiveData.postValue(loadAppInfoInteractor.loadAppInfo(appPackage))
        }
    }

    private fun convertBytesToMB(bytes: Long) = bytes.toDouble() / MB

    fun prepareOptions(applicationItem: ApplicationItem) {
        launchLoadingErrorJob {
            optionsLiveData.postValue(prepareOptionsInteractor.prepareOptionsList(applicationItem))
        }
    }

    fun getAppSize(bytes: Long): String {
        val bd = BigDecimal(convertBytesToMB(bytes))
        val rounded = bd.setScale(2, BigDecimal.ROUND_CEILING)
        return rounded.toString()
    }

    fun prepareOpenAppIntent() = prepareOptionsInteractor.prepareOpenAppIntent(appPackage)

    fun prepareUninstallIntent() = prepareOptionsInteractor.prepareUninstallIntent(appPackage)

    companion object {
        private const val KB: Long = 1024
        private const val MB: Long = KB * 1024
    }
}