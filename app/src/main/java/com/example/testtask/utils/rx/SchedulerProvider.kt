package com.example.testtask.utils.rx

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class SchedulerProvider: ISchedulerProvider {

    override fun worker(): Scheduler {
        return Schedulers.io()
    }

    override fun computation(): Scheduler {
        return Schedulers.computation()
    }

    override fun main(): Scheduler {
        return AndroidSchedulers.mainThread()
    }

}