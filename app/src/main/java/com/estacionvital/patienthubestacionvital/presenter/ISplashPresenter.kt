package com.estacionvital.patienthubestacionvital.presenter

/**
 * Created by kevin on 22/2/2018.
 */
/*Acciones de lógica de negocio aplicado por el presenter ante acciones de la UI
* en la activity son los eventos de la ui, al presenter se le manda a hacer acciones como procesar algo,
* la idea es que el presenter sea agnostico a android y no use nada de la api de android, en todo caso las
* api de android se usan en las view y quizas en algunos utils.
* */
interface ISplashPresenter {
    fun checkSession() : Unit
}