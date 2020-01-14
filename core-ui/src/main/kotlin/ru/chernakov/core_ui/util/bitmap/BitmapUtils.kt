package ru.chernakov.core_ui.util.bitmap

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.BitmapShader
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.graphics.Rect
import android.graphics.RectF
import android.graphics.Shader
import android.net.Uri
import timber.log.Timber
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

object BitmapUtils {
    const val JPEG_QUALITY = 80

    /**
     * After creation of new bitmap the old one will be recycled [Bitmap.recycle]
     */
    fun scaleBitmap(
        source: Bitmap,
        @androidx.annotation.IntRange(from = 1) newHeight: Int,
        @androidx.annotation.IntRange(from = 1) newWidth: Int
    ): Bitmap {
        val sourceWidth = source.width
        val sourceHeight = source.height

        if (sourceWidth == newWidth && sourceHeight == newHeight) {
            return source
        }
        synchronized(BitmapUtils::class.java) {
            val dest: Bitmap
            val config = if (source.config == null) Bitmap.Config.ARGB_8888 else source.config
            dest = Bitmap.createBitmap(newWidth, newHeight, config)
            val canvas = Canvas(dest)
            val xScale = newWidth.toFloat() / sourceWidth
            val yScale = newHeight.toFloat() / sourceHeight
            val scale = Math.max(xScale, yScale)

            val scaledWidth = scale * sourceWidth
            val scaledHeight = scale * sourceHeight

            val dst = RectF(0f, 0f, scaledWidth, scaledHeight)
            canvas.drawBitmap(source, null, dst, null)

            source.recycle()
            return dest
        }
    }

    fun scaleCenterCrop(source: Bitmap, newHeight: Int, newWidth: Int): Bitmap {
        val sourceWidth = source.width
        val sourceHeight = source.height

        val xScale = newWidth.toFloat() / sourceWidth
        val yScale = newHeight.toFloat() / sourceHeight
        val scale = Math.max(xScale, yScale)

        val scaledWidth = scale * sourceWidth
        val scaledHeight = scale * sourceHeight

        val left = (newWidth - scaledWidth) / 2
        val top = (newHeight - scaledHeight) / 2

        val targetRect = RectF(left, top, left + scaledWidth, top + scaledHeight)

        var dest: Bitmap
        synchronized(BitmapUtils::class.java) {
            dest = Bitmap.createBitmap(newWidth, newHeight, source.config)
            val canvas = Canvas(dest)
            canvas.drawBitmap(source, null, targetRect, null)
            source.recycle()
            return dest
        }
    }

    @Synchronized
    fun getRounded(src: Bitmap, @androidx.annotation.FloatRange(from = 1.0) radius: Float): Bitmap {
        val output = getRoundedWithOutRecycledSource(src, radius)
        src.recycle()
        return output
    }

    fun getRoundedWithOutRecycledSource(
        src: Bitmap,
        @androidx.annotation.FloatRange(from = 1.0) radius: Float
    ): Bitmap {
        val width = src.width
        val height = src.height

        val output = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(output)

        val shader = BitmapShader(src, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)

        val paint = Paint()
        paint.isAntiAlias = true
        paint.shader = shader

        val rect = RectF(0.0f, 0.0f, width.toFloat(), height.toFloat())
        canvas.drawRoundRect(rect, radius, radius, paint)
        return output
    }

    @Synchronized
    fun getCircled(src: Bitmap): Bitmap {
        val size = Math.min(src.width, src.height)
        val dst = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888)
        cicledWithOutRecycled(src, dst, size)
        src.recycle()
        return dst
    }

    fun cicledWithOutRecycled(src: Bitmap, dst: Bitmap, size: Int) {
        dst.setHasAlpha(true)

        val canvas = Canvas(dst)

        val color = -0xbdbdbe
        val paint = Paint()
        val rect = Rect(0, 0, size, size)

        canvas.drawColor(Color.TRANSPARENT)
        paint.isAntiAlias = true
        canvas.drawARGB(0, 0, 0, 0)
        paint.color = color

        val bounds = size.toFloat() / 2

        canvas.drawCircle(bounds, bounds, bounds, paint)
        paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
        canvas.drawBitmap(src, rect, rect, paint)
    }

    /**
     * https://redmine.e-legion.com/issues/72294
     *
     *
     * Picasso sometimes fails to load files in this case we use this function.
     * Should be fixed in Picasso version > 2.5.2
     */
    @Synchronized
    fun scaleImageFile(imageFileUri: Uri, newWidth: Int, newHeight: Int): Bitmap? {
        if (!isFileUri(imageFileUri)) {
            return null
        }

        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeFile(imageFileUri.path, options)
        options.inSampleSize = calculateInSampleSize(options, newWidth, newHeight)
        options.inJustDecodeBounds = false
        return BitmapFactory.decodeFile(imageFileUri.path, options)
    }

    fun isFileUri(uri: Uri?): Boolean {
        return uri != null && "file".equals(uri.scheme, ignoreCase = true)
    }

    fun calculateInSampleSize(options: BitmapFactory.Options, reqWidth: Int, reqHeight: Int): Int {
        val height = options.outHeight
        val width = options.outWidth
        var inSampleSize = 1

        if (height > reqHeight || width > reqWidth) {
            val halfHeight = height / 2
            val halfWidth = width / 2
            while (halfHeight / inSampleSize >= reqHeight && halfWidth / inSampleSize >= reqWidth) {
                inSampleSize *= 2
            }
        }
        return inSampleSize
    }

    fun compressBitmap(filePath: String, bitmap: Bitmap) {
        try {
            val file = File(filePath)
            val outputStream = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.JPEG, JPEG_QUALITY, outputStream)
            outputStream.close()
        } catch (e: IOException) {
            Timber.e(e, "Compress bitmap error")
        }
    }

    @Synchronized
    fun decodeResourceAsRgb565Bitmap(resources: Resources, resourceId: Int): Bitmap {
        val opts = BitmapFactory.Options()
        opts.inPreferredConfig = Bitmap.Config.RGB_565
        return BitmapFactory.decodeResource(resources, resourceId, opts)
    }

    /**
     * @param config если null то используется [Bitmap.Config.ARGB_8888]
     */
    fun getMutableBitmap(src: Bitmap, config: Bitmap.Config?): Bitmap? {
        if (src.isMutable) {
            return src
        }

        synchronized(BitmapUtils::class.java) {
            val copy = src.copy(config ?: Bitmap.Config.ARGB_8888, true) ?: return null
            if (copy != src) {
                src.recycle()
            }
            return copy
        }
    }

    fun blur(source: Bitmap, @androidx.annotation.IntRange(from = 1) radius: Int): Bitmap? {
        val blurredBitmap = getMutableBitmap(source, Bitmap.Config.ARGB_8888) ?: return null

        return fastBlur(blurredBitmap, radius)
    }

    // fast blur for all versions if needed
    @Suppress("LongMethod", "ComplexMethod", "MagicNumber")
    private fun fastBlur(bitmap: Bitmap, radius: Int): Bitmap? {
        // Stack Blur Algorithm by Mario Klingemann <mario@quasimondo.com>
        if (radius < 1) {
            return null
        }

        val w = bitmap.width
        val h = bitmap.height

        val pix = IntArray(w * h)
        bitmap.getPixels(pix, 0, w, 0, 0, w, h)

        val wm = w - 1
        val hm = h - 1
        val wh = w * h
        val div = radius + radius + 1

        val r = IntArray(wh)
        val g = IntArray(wh)
        val b = IntArray(wh)
        var rsum: Int
        var gsum: Int
        var bsum: Int
        var x: Int
        var y: Int
        var i: Int
        var p: Int
        var yp: Int
        var yi: Int
        var yw: Int
        val vmin = IntArray(Math.max(w, h))

        var divsum = div + 1 shr 1
        divsum *= divsum
        val dv = IntArray(256 * divsum)
        i = 0
        while (i < 256 * divsum) {
            dv[i] = i / divsum
            i++
        }

        yi = 0
        yw = yi

        val stack = Array(div) { IntArray(3) }
        var stackpointer: Int
        var stackstart: Int
        var sir: IntArray
        var rbs: Int
        val r1 = radius + 1
        var routsum: Int
        var goutsum: Int
        var boutsum: Int
        var rinsum: Int
        var ginsum: Int
        var binsum: Int

        y = 0
        while (y < h) {
            bsum = 0
            gsum = bsum
            rsum = gsum
            boutsum = rsum
            goutsum = boutsum
            routsum = goutsum
            binsum = routsum
            ginsum = binsum
            rinsum = ginsum
            i = -radius
            while (i <= radius) {
                p = pix[yi + Math.min(wm, Math.max(i, 0))]
                sir = stack[i + radius]
                sir[0] = p and 0xff0000 shr 16
                sir[1] = p and 0x00ff00 shr 8
                sir[2] = p and 0x0000ff
                rbs = r1 - Math.abs(i)
                rsum += sir[0] * rbs
                gsum += sir[1] * rbs
                bsum += sir[2] * rbs
                if (i > 0) {
                    rinsum += sir[0]
                    ginsum += sir[1]
                    binsum += sir[2]
                } else {
                    routsum += sir[0]
                    goutsum += sir[1]
                    boutsum += sir[2]
                }
                i++
            }
            stackpointer = radius

            x = 0
            while (x < w) {

                r[yi] = dv[rsum]
                g[yi] = dv[gsum]
                b[yi] = dv[bsum]

                rsum -= routsum
                gsum -= goutsum
                bsum -= boutsum

                stackstart = stackpointer - radius + div
                sir = stack[stackstart % div]

                routsum -= sir[0]
                goutsum -= sir[1]
                boutsum -= sir[2]

                if (y == 0) {
                    vmin[x] = Math.min(x + radius + 1, wm)
                }
                p = pix[yw + vmin[x]]

                sir[0] = p and 0xff0000 shr 16
                sir[1] = p and 0x00ff00 shr 8
                sir[2] = p and 0x0000ff

                rinsum += sir[0]
                ginsum += sir[1]
                binsum += sir[2]

                rsum += rinsum
                gsum += ginsum
                bsum += binsum

                stackpointer = (stackpointer + 1) % div
                sir = stack[stackpointer % div]

                routsum += sir[0]
                goutsum += sir[1]
                boutsum += sir[2]

                rinsum -= sir[0]
                ginsum -= sir[1]
                binsum -= sir[2]

                yi++
                x++
            }
            yw += w
            y++
        }
        x = 0
        while (x < w) {
            bsum = 0
            gsum = bsum
            rsum = gsum
            boutsum = rsum
            goutsum = boutsum
            routsum = goutsum
            binsum = routsum
            ginsum = binsum
            rinsum = ginsum
            yp = -radius * w
            i = -radius
            while (i <= radius) {
                yi = Math.max(0, yp) + x

                sir = stack[i + radius]

                sir[0] = r[yi]
                sir[1] = g[yi]
                sir[2] = b[yi]

                rbs = r1 - Math.abs(i)

                rsum += r[yi] * rbs
                gsum += g[yi] * rbs
                bsum += b[yi] * rbs

                if (i > 0) {
                    rinsum += sir[0]
                    ginsum += sir[1]
                    binsum += sir[2]
                } else {
                    routsum += sir[0]
                    goutsum += sir[1]
                    boutsum += sir[2]
                }

                if (i < hm) {
                    yp += w
                }
                i++
            }
            yi = x
            stackpointer = radius
            y = 0
            while (y < h) {
                // Preserve alpha channel: ( 0xff000000 & pix[yi] )
                pix[yi] = -0x1000000 and pix[yi] or (dv[rsum] shl 16) or (dv[gsum] shl 8) or dv[bsum]

                rsum -= routsum
                gsum -= goutsum
                bsum -= boutsum

                stackstart = stackpointer - radius + div
                sir = stack[stackstart % div]

                routsum -= sir[0]
                goutsum -= sir[1]
                boutsum -= sir[2]

                if (x == 0) {
                    vmin[y] = Math.min(y + r1, hm) * w
                }
                p = x + vmin[y]

                sir[0] = r[p]
                sir[1] = g[p]
                sir[2] = b[p]

                rinsum += sir[0]
                ginsum += sir[1]
                binsum += sir[2]

                rsum += rinsum
                gsum += ginsum
                bsum += binsum

                stackpointer = (stackpointer + 1) % div
                sir = stack[stackpointer]

                routsum += sir[0]
                goutsum += sir[1]
                boutsum += sir[2]

                rinsum -= sir[0]
                ginsum -= sir[1]
                binsum -= sir[2]

                yi += w
                y++
            }
            x++
        }

        bitmap.setPixels(pix, 0, w, 0, 0, w, h)

        return bitmap
    }
}