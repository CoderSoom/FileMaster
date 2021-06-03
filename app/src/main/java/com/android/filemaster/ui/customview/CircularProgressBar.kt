package com.android.filemaster.ui.customview

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import android.view.animation.DecelerateInterpolator
import com.android.filemaster.R

class CircularProgressBar(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) :
    View(context, attrs, defStyleAttr) {
    private var mViewWidth = 0
    private var mViewHeight = 0
    private val mStartAngle = -90f
    private var mSweepAngleBackground = 0f
    private var mSweepAngleProcess = 0f
    private val mMaxSweepAngle = 360f
    private var mStrokeWidth = 0
    private val mAnimationDuration = 400
    private val mMaxProgress = 100
    private var mRoundedCorners = true
    private var mProgressColorBackground: Int = Color.parseColor("#257DF3")
    private var mProgressColor: Int = Color.parseColor("#E0E0E0")
    private val mPaintBackground: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val mPaintProcess = Paint(Paint.ANTI_ALIAS_FLAG)

    constructor(context: Context?) : this(context, null) {

    }

    private fun getAllAttFromAttributeSet(attrs: AttributeSet) {
        val typeAttrs =
            context.obtainStyledAttributes(
                attrs,
                R.styleable.CircularProgressBar
            )
        mStrokeWidth =
            typeAttrs.getDimensionPixelSize(R.styleable.CircularProgressBar_strokeWidth, 3)
        typeAttrs.recycle()
    }

    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0) {
        getAllAttFromAttributeSet(attrs!!)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        initMeasurments()
        drawOutlineArc(canvas)
    }


    private fun initMeasurments() {
        mViewWidth = width
        mViewHeight = height
    }

    private fun drawOutlineArc(canvas: Canvas) {
        val diameter = Math.min(mViewWidth, mViewHeight) - mStrokeWidth * 2
        val outerOval = RectF(
            mStrokeWidth.toFloat(),
            mStrokeWidth.toFloat(), diameter.toFloat(), diameter.toFloat()
        )
        //DrawBackground Progress

        mPaintProcess.color = mProgressColor
        mPaintProcess.strokeWidth = mStrokeWidth.toFloat()
        mPaintProcess.isAntiAlias = true
        mPaintProcess.strokeCap = if (mRoundedCorners) Paint.Cap.ROUND else Paint.Cap.BUTT
        mPaintProcess.style = Paint.Style.STROKE

        ///DrawOutLine

        mPaintBackground.color = mProgressColorBackground
        mPaintBackground.strokeWidth = mStrokeWidth.toFloat()
        mPaintBackground.isAntiAlias = true
        mPaintBackground.strokeCap = if (mRoundedCorners) Paint.Cap.ROUND else Paint.Cap.BUTT
        mPaintBackground.style = Paint.Style.STROKE

        canvas.drawArc(outerOval, mStartAngle, mSweepAngleProcess, false, mPaintProcess)
        canvas.drawArc(outerOval, mStartAngle, mSweepAngleBackground, false, mPaintBackground)

    }


    private fun calcSweepAngleFromProgress(progress: Int): Float {
        return mMaxSweepAngle / mMaxProgress * progress
    }

    fun setProgress(progress: Int) {
        val animator =
            ValueAnimator.ofFloat(mSweepAngleBackground, calcSweepAngleFromProgress(progress))
        animator.interpolator = DecelerateInterpolator()
        animator.duration = mAnimationDuration.toLong()
        animator.addUpdateListener { valueAnimator ->
            mSweepAngleBackground = valueAnimator.animatedValue as Float
            invalidate()
        }
        animator.start()
    }

    fun setProcessBig(maxProgress: Int) {
        val animator =
            ValueAnimator.ofFloat(mSweepAngleProcess, calcSweepAngleFromProgress(maxProgress))
        animator.interpolator = DecelerateInterpolator()
        animator.duration = mAnimationDuration.toLong()
        animator.addUpdateListener { valueAnimator ->
            mSweepAngleProcess = valueAnimator.animatedValue as Float
            invalidate()
        }
        animator.start()
    }


}