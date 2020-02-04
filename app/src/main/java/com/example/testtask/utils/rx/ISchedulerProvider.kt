package com.example.testtask.utils.rx

import io.reactivex.Scheduler

interface ISchedulerProvider {

    fun worker(): Scheduler

    fun computation(): Scheduler

    fun main(): Scheduler
}
