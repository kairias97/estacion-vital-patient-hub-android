package com.estacionvital.patienthub.presenter

import android.content.Context
import com.estacionvital.patienthub.model.EVUserExamination
import com.estacionvital.patienthub.model.EVUserExaminationData

/**
 * Created by dusti on 16/03/2018.
 */
interface IConversationHistoryPresenter {
    fun retrieveConversationHistory(context: Context)
    fun setUpTwilioClient(context: Context, data: List<EVUserExamination>)
    fun callSubscribedChannels(data: List<EVUserExamination>)
}