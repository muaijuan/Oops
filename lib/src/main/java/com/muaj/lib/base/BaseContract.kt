package com.muaj.lib.base

/**
 * Created by muaj on 2018/6/6
 *
 */

class BaseContract {
     interface BasePresenter<T> {

        fun attachView(view: T)

        fun detachView()
    }

     interface BaseView
}
