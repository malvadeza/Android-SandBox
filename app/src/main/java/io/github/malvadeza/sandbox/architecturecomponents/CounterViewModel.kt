package io.github.malvadeza.sandbox.architecturecomponents

import android.arch.lifecycle.ViewModel
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import timber.log.Timber

class CounterViewModel : ViewModel() {

    private var counter = 0

    private val subject = BehaviorSubject.create<Int>()

    fun increase() {
        counter += 1

        subject.onNext(counter)
    }

    fun get(): Observable<Int> {
        return subject
    }

    override fun onCleared() {
        super.onCleared()
        Timber.d("onCleared")
    }

}