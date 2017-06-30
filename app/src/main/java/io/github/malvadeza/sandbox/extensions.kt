package io.github.malvadeza.sandbox

import android.support.v7.widget.RecyclerView
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

operator fun CompositeDisposable.plusAssign(d: Disposable) {
    add(d)
}