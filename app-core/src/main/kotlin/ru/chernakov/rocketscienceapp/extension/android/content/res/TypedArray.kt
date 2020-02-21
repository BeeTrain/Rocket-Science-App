package ru.chernakov.rocketscienceapp.extension.android.content.res

import android.content.Context
import android.content.res.TypedArray
import android.graphics.drawable.Drawable
import android.os.Build
import androidx.appcompat.content.res.AppCompatResources

fun TypedArray.getDrawable(context: Context, styleIndex: Int): Drawable? {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        getDrawable(styleIndex)
    } else {
        val resId = getResourceId(styleIndex, -1)
        if (resId == -1) null else AppCompatResources.getDrawable(context, resId)
    }
}