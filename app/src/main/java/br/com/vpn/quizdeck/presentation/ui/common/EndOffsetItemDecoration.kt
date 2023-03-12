package br.com.vpn.quizdeck.presentation.ui.common

import android.content.res.Resources
import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlin.math.roundToInt

class EndOffsetItemDecoration(
    private val endOffset: Int
) : RecyclerView.ItemDecoration() {

    companion object {
        val ABOVE_FAB_OFFSET = (Resources.getSystem().displayMetrics.density * 96).roundToInt()
    }

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)

        val position = parent.getChildAdapterPosition(view)

        if (position == parent.adapter?.itemCount?.minus(1)) {
            outRect.bottom = endOffset
        }
    }
}