package com.estacionvital.patienthub.ui.views

import com.estacionvital.patienthub.model.EVSpecialtiesResponse

/**
 * Created by dusti on 15/03/2018.
 */
interface ISpecialtySelectionView {
    fun showProgressDialog()
    fun showAvailabilityProgressDialog()
    fun hideLoading()
    fun showErrorLoading()
    fun setSpecialtiesData(data: EVSpecialtiesResponse)
    fun getDoctorAvailability(data: Boolean)
}