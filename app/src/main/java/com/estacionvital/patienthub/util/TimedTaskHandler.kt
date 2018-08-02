package com.estacionvital.patienthub.util

import android.os.Handler

/**
 * Created by kevin on 22/2/2018.
 */
//Clase wrapped de un handler de android para ejecutar una tarea con delay, usado en el Splash
class TimedTaskHandler: Handler() {
    fun executeTimedTask(task: ()->Unit, time: Long){
        this.postDelayed(task, time)
    }
}