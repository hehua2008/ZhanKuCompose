package com.hym.zhankucompose.ui

import android.util.TypedValue
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import com.google.android.material.button.MaterialButton
import com.hym.zhankucompose.MyApplication
import com.hym.zhankucompose.R
import com.hym.zhankucompose.util.createDefaultLayoutParams

object ButtonFactory {
    /**
     * <com.google.android.material.button.MaterialButton
     *     android:id="@+id/button_view"
     *     android:layout_width="wrap_content"
     *     android:layout_height="wrap_content"
     *     android:checkable="true"
     *     android:maxLines="1"
     *     android:minWidth="@dimen/button_min_width"
     *     android:minHeight="@dimen/button_min_height"
     *     android:paddingHorizontal="@dimen/button_padding_horizontal"
     *     android:paddingVertical="0dp"
     *     android:text="button"
     *     android:textAllCaps="false"
     *     android:textSize="@dimen/common_text_size"
     *     app:backgroundTint="@color/btn_color" />
     */
    private val mResources by lazy { MyApplication.INSTANCE.resources }

    private val mMinWidth = mResources.getDimensionPixelSize(R.dimen.button_min_width)
    private val mMinHeight = mResources.getDimensionPixelSize(R.dimen.button_min_height)

    private val mPaddingHorizontal =
        mResources.getDimensionPixelSize(R.dimen.button_padding_horizontal)
    private val mPaddingVertical =
        mResources.getDimensionPixelSize(R.dimen.button_padding_vertical)

    private val mTextSize = mResources.getDimension(R.dimen.common_text_size)

    fun create(parent: ViewGroup): MaterialButton {
        val context = parent.context
        return MaterialButton(context).apply {
            id = R.id.button_view
            isCheckable = true
            maxLines = 1
            minWidth = mMinWidth
            minimumWidth = mMinWidth
            minHeight = mMinHeight
            minimumHeight = mMinHeight
            setPadding(mPaddingHorizontal, mPaddingVertical, mPaddingHorizontal, mPaddingVertical)
            text = "button"
            isAllCaps = false
            setTextSize(TypedValue.COMPLEX_UNIT_PX, mTextSize)
            layoutParams = parent.createDefaultLayoutParams().apply {
                width = ViewGroup.LayoutParams.WRAP_CONTENT
                height = ViewGroup.LayoutParams.WRAP_CONTENT
            }
            backgroundTintList = AppCompatResources.getColorStateList(context, R.color.button_color)
        }
    }
}