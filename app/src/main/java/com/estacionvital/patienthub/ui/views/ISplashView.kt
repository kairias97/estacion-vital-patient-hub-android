package com.estacionvital.patienthub.ui.views

/**
 * Created by kevin on 22/2/2018.
 */
/*Interfaz que define los comportamientos de UI dentro de modelo model view presenter
*   esto es para seguir el patró de diseóo MVP, en donde en las activities solo se define lógica
*   de UI y no de negocio, la de negocio va en forma de interfaz de presenter
* */
interface ISplashView {
    fun navigateToNumberVerification() : Unit
    fun navigateToMain() : Unit
}