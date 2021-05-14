package ru.rager.credit.presentation.util.itemdecorations

import android.graphics.Rect
import android.view.View
import androidx.annotation.DimenRes
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.rager.credit.presentation.R

class GridSpaceItemDecoration(
    @DimenRes private val start: Int,
    @DimenRes private val end: Int = start,
    @DimenRes private val top: Int,
    @DimenRes private val bottom: Int = top,
    @DimenRes private val space: Int
) : RecyclerView.ItemDecoration() {

    companion object {
        fun getDefault() = GridSpaceItemDecoration(
            start = R.dimen.margin_8,
            top = R.dimen.margin_8,
            space = R.dimen.margin_8
        )
    }

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)
        val layoutManager = parent.layoutManager as? GridLayoutManager ?: return
        val adapter = parent.adapter ?: return
        val spanCount = layoutManager.spanCount
        val position = parent.getChildAdapterPosition(view)
        val resources = parent.resources

        val startDimension = resources.getDimensionPixelOffset(start)
        val endDimension = resources.getDimensionPixelOffset(end)
        val topDimension = resources.getDimensionPixelOffset(top)
        val bottomDimension = resources.getDimensionPixelOffset(bottom)
        val spaceDimension = resources.getDimensionPixelOffset(space)

        val startOffset = when {
            position % spanCount == 0 -> startDimension
            else -> spaceDimension
        }
        val rightOffset = when {
            position % spanCount == spanCount - 1 -> endDimension
            else -> 0
        }
        val topOffset = when (position) {
            in 0 until spanCount -> topDimension
            else -> spaceDimension
        }
        val bottomOffset = when (position) {
            in (adapter.itemCount - position % spanCount)..adapter.itemCount -> bottomDimension
            else -> 0
        }
        outRect.set(startOffset, topOffset, rightOffset, bottomOffset)
    }

}