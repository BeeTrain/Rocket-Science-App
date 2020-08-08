@file:Suppress("NOTHING_TO_INLINE")

package ru.chernakov.rocketscienceapp.extension.android.content

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.text.TextUtils
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import timber.log.Timber

private const val APP_MARKET_URI = "market://details?id="
private const val WEB_MARKET_URI = "https://play.google.com/store/apps/details?id="

fun Context.hideKeyboard(view: View) {
    val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
    imm?.hideSoftInputFromWindow(view.windowToken, 0)
}

fun Context.showKeyboard(view: View) {
    (getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).apply {
        showSoftInput(view, 0)
    }
}

fun Context.openPlayMarket() {
    val appPackageName = packageName
    try {
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(APP_MARKET_URI + appPackageName)))
    } catch (e: ActivityNotFoundException) {
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(WEB_MARKET_URI + appPackageName)))
        Timber.e(e, "Failed to open market")
    }
}

fun Context.sendEmail(subject: String?, message: String?, vararg recipients: String): Boolean {
    val email = Intent(Intent.ACTION_SENDTO)
    if (!TextUtils.isEmpty(subject)) {
        email.putExtra(Intent.EXTRA_SUBJECT, subject)
    }
    if (!TextUtils.isEmpty(message)) {
        email.putExtra(Intent.EXTRA_TEXT, message)
    }
    if (recipients.isNotEmpty()) {
        email.putExtra(Intent.EXTRA_EMAIL, recipients)
    }
    // need this to prompts email client only
    email.type = "message/rfc822"
    email.data = Uri.parse("mailto:")
    val infos = packageManager.queryIntentActivities(email, 0)
    if (infos.size > 0) {
        startActivity(email)
        return true
    } else {
        return false
    }
}

fun Context.getColorKtx(@ColorRes id: Int): Int {
    return ContextCompat.getColor(this, id)
}

fun Context.dip(value: Int): Int = (value * resources.displayMetrics.density).toInt()

fun Context.dip(value: Float): Int = (value * resources.displayMetrics.density).toInt()