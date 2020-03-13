package ru.chernakov.rocketscienceapp.util.glide

import android.graphics.drawable.Drawable
import androidx.fragment.app.Fragment
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target

class TransitionRequestListener(private val fragment: Fragment) : RequestListener<Drawable> {
    override fun onLoadFailed(
        e: GlideException?,
        model: Any?,
        target: Target<Drawable>?,
        isFirstResource: Boolean
    ) = startAnimation()

    override fun onResourceReady(
        resource: Drawable?,
        model: Any?,
        target: Target<Drawable>?,
        dataSource: DataSource?,
        isFirstResource: Boolean
    ) = startAnimation()

    private fun startAnimation(): Boolean {
        fragment.startPostponedEnterTransition()
        return false
    }
}