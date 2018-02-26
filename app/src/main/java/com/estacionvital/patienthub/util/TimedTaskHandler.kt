package com.estacionvital.patienthub.util

import android.os.Handler

/**
 * Created by kevin on 22/2/2018.
 */
class TimedTaskHandler: Handler() {
    fun executeTimedTask(task: ()->Unit, time: Long){
        this.postDelayed(task, time)
    }
}