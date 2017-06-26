package io.github.malvadeza.sandbox

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

operator fun CompositeDisposable.plusAssign(d: Disposable) {
    add(d)
}