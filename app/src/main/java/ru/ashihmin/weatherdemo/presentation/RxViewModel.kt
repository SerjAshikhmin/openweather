package ru.ashihmin.weatherdemo.presentation

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class RxViewModel : ViewModel() {

    protected var disposables = CompositeDisposable()

    fun addToDisposable(disposable: Disposable?): Disposable {
        this.disposables.add(disposable!!)
        return disposable
    }

    override fun onCleared() {
        disposables.dispose()
        super.onCleared()
    }

}
