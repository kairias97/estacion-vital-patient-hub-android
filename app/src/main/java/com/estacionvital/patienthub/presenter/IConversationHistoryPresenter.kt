package com.estacionvital.patienthub.presenter

import android.content.Context
import com.estacionvital.patienthub.model.EVChannel
import com.estacionvital.patienthub.model.EVUserExamination

/**
 * Created by dusti on 16/03/2018.
 */
interface IConversationHistoryPresenter : IBasePresenter{
    fun retrieveConversationHistory(context: Context)
    fun setUpTwilioClient(context: Context, data: List<EVUserExamination>)
    fun callSubscribedChannels(data: List<EVUserExamination>)
    fun filterByTypeChat(data: MutableList<EVChannel>, type: String): MutableList<EVChannel>
}