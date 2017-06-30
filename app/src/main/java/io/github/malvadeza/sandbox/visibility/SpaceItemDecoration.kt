package io.github.malvadeza.sandbox.visibility

import android.graphics.Rect
import android.support.v7.widget.RecyclerView
import android.view.View

class SpaceItemDecoration : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        outRect.set(0, 50, 0, 50)
    }

}