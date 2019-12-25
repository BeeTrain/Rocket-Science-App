package ru.chernakov.core_ui.util.bitmap.picasso

import android.graphics.Bitmap
import com.squareup.picasso.Transformation
import ru.chernakov.core_ui.util.bitmap.BitmapUtils

class RoundCornersTransformation(
    @androidx.annotation.FloatRange(from = 1.0) private val radius: Float
) : Transformation {
    override fun transform(source: Bitmap): Bitmap {
        return BitmapUtils.getRounded(source, radius)
    }

    override fun key(): String {
        return "RoundCornersTransformation"
    }
}
