package com.estacionvital.patienthubestacionvital.presenter

import android.content.Context
import com.estacionvital.patienthubestacionvital.model.EVUserExamination

/**
 * Created by dusti on 16/03/2018.
 */
interface IConversationHistoryPresenter : IBasePresenter{
    fun retrieveConversationHistory(context: Context)
    fun setUpTwilioClient(context: Context, data: List<EVUserExamination>)
    fun callSubscribedChannels(data: List<EVUserExamination>)
}