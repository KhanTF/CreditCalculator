package ru.rager.credit.presentation.util.itemdecorations

import android.graphics.Rect
import android.view.View
import androidx.annotation.DimenRes
import androidx.recyclerview.widget.RecyclerView
import ru.rager.credit.presentation.R

class LinearSpaceItemDecoration(
    @DimenRes private val start: Int = R.dimen.margin_8,
    @DimenRes private val end: Int = start,
    @DimenRes private val top: Int = R.dimen.margin_8,
    @DimenRes private val bottom: Int = top,
    @DimenRes private val space: Int = R.dimen.margin_8
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)
        val adapter = parent.adapter ?: return
        val position = parent.getChildAdapterPosition(view)
        val resources = parent.resources

        val startDimension = resources.getDimensionPixelOffset(start)
        val endDimension = resources.getDimensionPixelOffset(end)
        val topDimension = resources.getDimensionPixelOffset(top)
        val bottomDimension = resources.getDimensionPixelOffset(bottom)
        val spaceDimension = resources.getDimensionPixelOffset(space)

        val topOffset = when (position) {
            0 -> topDimension
            else -> spaceDimension
        }
        val bottomOffset = when (position) {
            adapter.itemCount - 1 -> bottomDimension
            else -> 0
        }
        outRect.set(startDimension, topOffset, endDimension, bottomOffset)
    }

}