package com.example.architecturestudy.ui.kin

import android.util.Log
import com.example.architecturestudy.data.repository.NaverSearchRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class KinPresenter(
    val view : KinContract.View,
    private val repository: NaverSearchRepository?
    ) : KinContract.Presenter {

    private val disposable = CompositeDisposable()

    override fun taskSearch (keyword: String) {
        repository?.let {
            val searchSingle = repository?.getKin(
                keyword = keyword
            )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        view.showResult(it.items)
                    },
                    {
                        view.showErrorMessage(it.toString())
                    }
                )
            disposable.add(searchSingle)
        }
    }

    override fun getLastData() {
        repository?.let {
            val data = repository.getLastKin()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        view.showResult(it)
                    },
                    {
                        view.showErrorMessage(it.toString())
                    }
                )
            disposable.add(data)
        }
    }

    override fun onStop() {
        disposable.clear()
    }
}