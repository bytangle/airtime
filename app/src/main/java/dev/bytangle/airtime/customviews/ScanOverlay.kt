package dev.bytangle.airtime.customviews

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View

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

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        if (internalCanvas == null || bitmap == null ) return

        internalCanvas?.drawColor(Color.DKGRAY)
        internalCanvas?.drawRect(22F,22F,66F,66F,cutPaint)
        bitmap?.let {
            canvas?.drawBitmap(it, 0F, 0F, null)
        }
    }

}