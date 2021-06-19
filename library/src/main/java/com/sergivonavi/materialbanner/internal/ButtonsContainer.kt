/*
 * Copyright 2019 Sergey Ivanov
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.sergivonavi.materialbanner.internal

import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.view.ViewGroup
import androidx.annotation.DimenRes
import androidx.annotation.IntDef
import androidx.annotation.RestrictTo
import androidx.core.view.ViewCompat
import com.google.android.material.button.MaterialButton
import com.sergivonavi.materialbanner.R
import kotlin.math.max

@RestrictTo(RestrictTo.Scope.LIBRARY)
class ButtonsContainer @JvmOverloads constructor(
	context: Context,
	attrs: AttributeSet? = null,
	defStyleAttr: Int = 0
) : ViewGroup(context, attrs, defStyleAttr) {
	@IntDef(HORIZONTAL, VERTICAL)
	@Retention(AnnotationRetention.SOURCE)
	private annotation class OrientationMode

	val leftButton: MaterialButton
	val rightButton: MaterialButton

	private val mButtonMarginEnd: Int
	private val mButtonMarginBottom: Int
	private var mOrientation: Int = 0

	override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
		var widthUsed = 0
		if (leftButton.visibility != GONE) {
			measureChildWithMargins(leftButton, widthMeasureSpec, 0, heightMeasureSpec, 0)
			widthUsed += leftButton.measuredWidth + mButtonMarginEnd
		}
		if (rightButton.visibility != GONE) {
			measureChildWithMargins(rightButton, widthMeasureSpec, 0, heightMeasureSpec, 0)
			widthUsed += measuredWidth + mButtonMarginEnd
		}

		// Allow orientation change only when the both buttons are not hidden
		if (leftButton.visibility != GONE && rightButton.visibility != GONE) {
			mOrientation = if (widthUsed > MeasureSpec.getSize(widthMeasureSpec)) {
				VERTICAL
			} else {
				HORIZONTAL
			}
		}
		if (mOrientation == VERTICAL) {
			measureVertical()
		} else {
			measureHorizontal()
		}
	}

	/**
	 * Measures the children when the orientation of this view is set to [.VERTICAL].
	 */
	private fun measureVertical() {
		var widthUsed = 0
		var heightUsed = 0
		if (leftButton.visibility != GONE) {
			widthUsed = leftButton.measuredWidth + mButtonMarginEnd
			heightUsed += leftButton.measuredHeight + mButtonMarginBottom
		}
		if (rightButton.visibility != GONE) {
			widthUsed = max(widthUsed, measuredWidth + mButtonMarginEnd)
			heightUsed += measuredHeight + mButtonMarginBottom
		}
		setMeasuredDimension(widthUsed, heightUsed)
	}

	/**
	 * Measures the children when the orientation of this view is set to [.HORIZONTAL].
	 */
	private fun measureHorizontal() {
		var widthUsed = 0
		var heightUsed = 0
		if (leftButton.visibility != GONE) {
			widthUsed += leftButton.measuredWidth + mButtonMarginEnd
			heightUsed = leftButton.measuredHeight + mButtonMarginBottom
		}
		if (rightButton.visibility != GONE) {
			widthUsed += measuredWidth + mButtonMarginEnd
			heightUsed = max(
				heightUsed,
				measuredHeight + mButtonMarginBottom
			)
		}
		setMeasuredDimension(widthUsed, heightUsed)
	}

	override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
		if (mOrientation == VERTICAL) {
			layoutVertical()
		} else {
			layoutHorizontal()
		}
	}

	/**
	 * Position the children during a layout pass if the orientation of this view is set to
	 * [.VERTICAL].
	 */
	private fun layoutVertical() {
		var top = 0
		var lBtnRight = measuredWidth - mButtonMarginEnd
		var lBtnLeft = lBtnRight - leftButton.measuredWidth
		var rBtnRight = measuredWidth - mButtonMarginEnd
		var rBtnLeft = rBtnRight - measuredWidth
		if (ViewCompat.getLayoutDirection(this) == ViewCompat.LAYOUT_DIRECTION_RTL) {
			lBtnLeft = mButtonMarginEnd
			lBtnRight = lBtnLeft + leftButton.measuredWidth
			rBtnLeft = mButtonMarginEnd
			rBtnRight = rBtnLeft + measuredWidth
		}
		if (rightButton.visibility != GONE) {
			layout(rBtnLeft, top, rBtnRight, measuredHeight)
			top += measuredHeight + mButtonMarginBottom
		}
		if (leftButton.visibility != GONE) {
			leftButton.layout(lBtnLeft, top, lBtnRight, top + leftButton.measuredHeight)
		}
	}

	/**
	 * Position the children during a layout pass if the orientation of this view is set to
	 * [.HORIZONTAL].
	 */
	private fun layoutHorizontal() {
		var lBtnRight = leftButton.measuredWidth
		var lBtnLeft = 0
		var rBtnRight = measuredWidth - mButtonMarginEnd
		var rBtnLeft = rBtnRight - measuredWidth
		if (ViewCompat.getLayoutDirection(this) == ViewCompat.LAYOUT_DIRECTION_RTL) {
			rBtnLeft = mButtonMarginEnd
			rBtnRight = rBtnLeft + measuredWidth
			lBtnRight = measuredWidth
			lBtnLeft = lBtnRight - leftButton.measuredWidth
		}
		if (leftButton.visibility != GONE) {
			leftButton.layout(lBtnLeft, 0, lBtnRight, leftButton.measuredHeight)
		}
		if (rightButton.visibility != GONE) {
			layout(rBtnLeft, 0, rBtnRight, measuredHeight)
		}
	}

	override fun checkLayoutParams(p: LayoutParams): Boolean = p is MarginLayoutParams

	override fun generateDefaultLayoutParams(): LayoutParams =
		MarginLayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)

	override fun generateLayoutParams(attrs: AttributeSet): LayoutParams =
		MarginLayoutParams(context, attrs)

	override fun generateLayoutParams(p: LayoutParams): LayoutParams =
		MarginLayoutParams(p)

	/**
	 * Returns the baseline of the left button if it's not hidden or the baseline of the right
	 * button. If both buttons hidden returns -1.
	 */
	override fun getBaseline(): Int {
		if (leftButton.visibility != GONE && leftButton.text != null) {
			return leftButton.baseline
		} else if (rightButton.visibility != GONE && rightButton.text != null) {
			return rightButton.baseline
		}
		return -1
	}

	/**
	 * Returns the current orientation.
	 *
	 * @return either [.HORIZONTAL] or [.VERTICAL]
	 */
	@Suppress("unused")
	@get:OrientationMode
	var orientation: Int
		get() = mOrientation
		/**
		 * Should the layout be a column or a row.
		 *
		 * @param orientation [.HORIZONTAL] or [.VERTICAL]. Default value is
		 * [.HORIZONTAL].
		 */
		set(@OrientationMode orientation) {
			if (mOrientation != orientation) {
				mOrientation = orientation
				requestLayout()
			}
		}

	private fun getDimen(@DimenRes dimenId: Int): Int {
		return context.resources.getDimensionPixelSize(dimenId)
	}

	companion object {
		const val HORIZONTAL = 0
		const val VERTICAL = 1
	}

	init {
		mButtonMarginEnd = getDimen(R.dimen.mb_button_margin_end)
		mButtonMarginBottom = getDimen(R.dimen.mb_button_margin_bottom)

		val layoutParams = MarginLayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
			layoutParams.marginEnd = mButtonMarginEnd
		} else {
			layoutParams.rightMargin = mButtonMarginEnd
		}

		layoutParams.bottomMargin = mButtonMarginBottom

		leftButton = MaterialButton(context, null, R.attr.borderlessButtonStyle).apply {
			id = R.id.mb_button_left
			isSingleLine = true
			maxLines = 1
			minWidth = 0
			minimumWidth = 0
			this@ButtonsContainer.layoutParams = layoutParams
			visibility = GONE
		}

		rightButton = MaterialButton(context, null, R.attr.borderlessButtonStyle).apply {
			id = R.id.mb_button_right
			isSingleLine = true
			maxLines = 1
			minWidth = 0
			minimumWidth = 0
			this@ButtonsContainer.layoutParams = layoutParams
			visibility = GONE
		}

		addView(leftButton)
		addView(rightButton)
	}
}