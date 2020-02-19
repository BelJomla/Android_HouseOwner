package com.android.belJomla

import android.os.CountDownTimer
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent

 open class LifeCycleCountdownTimer(lifecycle:Lifecycle, millisInFuture: Long, countDownInterval: Long) :
    CountDownTimer(millisInFuture, countDownInterval),LifecycleObserver {

    init {
        lifecycle.addObserver(this)
    }
    override fun onFinish() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onTick(p0: Long) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun stopTimer(){
        this.cancel()
    }



}