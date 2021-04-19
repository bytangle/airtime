package dev.bytangle.airtime.customviews

import android.content.Context
import android.content.res.Resources
import android.graphics.*
import android.os.Build
import android.renderscript.Allocation
import android.renderscript.Element
import android.renderscript.RenderScript
import android.renderscript.ScriptIntrinsicBlur
import android.util.AttributeSet
import android.view.View
import androidx.annotation.RequiresApi
import dev.bytangle.airtime.R

class ScanOverlay : View {
    private val cutPaint = Paint(Paint.ANTI_ALIAS_FLAG) // responsible for the cutting
    private var bitmap : Bitmap? = null
    private var internalCanvas : Canvas? = null

    constructor(context: Context, attrSet: AttributeSet) : super(context, attrSet) {
        init()
    }
    constructor(context : Context, attrSet : AttributeSet, defStyle : Int) : super(context, attrSet, defStyle) {
        init()
    }
    constructor(context : Context) : super(context) {
        init()
    }

    private fun init() {
        cutPaint.xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR)
        cutPaint.strokeWidth = 59F
        cutPaint.strokeCap = Paint.Cap.ROUND
        cutPaint.strokeJoin = Paint.Join.ROUND
    }


    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        if (internalCanvas != null) {
            internalCanvas?.setBitmap(null)
            internalCanvas = null
        }

        if (bitmap != null) {
            bitmap?.recycle()
            bitmap = null
        }
        // re-initialize bitmap and internalCanvas
        bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
        internalCanvas = Canvas(bitmap!!)

    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        if (internalCanvas == null || bitmap == null ) return

        internalCanvas?.drawColor(resources.getColor(R.color.faded_black, resources.newTheme()))
        internalCanvas?.drawRect(
            (width / 16).toFloat(),
            ((height / 2)).toFloat(),
            (width - (width / 16)).toFloat(),
            ((height / 2) + (height / 3.2)).toFloat(),
            cutPaint)
        bitmap?.let {
            canvas?.drawBitmap(it, 0F, 0F, null)
        }
    }

    private fun blurBitmap(bitmap : Bitmap) : Bitmap {
        val outBitmap = Bitmap.createBitmap(bitmap.width, bitmap.height, Bitmap.Config.ARGB_8888)

        val rs = RenderScript.create(context)
        val blurScript = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs))

        // allocations
        val allIn = Allocation.createFromBitmap(rs, bitmap)
        val allOut = Allocation.createFromBitmap(rs, outBitmap)

        blurScript.setRadius(25f)
        blurScript.setInput(allIn)
        blurScript.forEach(allOut)

        allOut.copyTo(outBitmap)

        bitmap.recycle()
        rs.destroy()

        return outBitmap
    }

}