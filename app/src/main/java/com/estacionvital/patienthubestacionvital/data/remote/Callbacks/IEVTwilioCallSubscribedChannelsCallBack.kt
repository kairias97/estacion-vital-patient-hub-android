package com.estacionvital.patienthubestacionvital.data.remote.Callbacks

import com.twilio.chat.Channel
/**
 * Created by dusti on 17/03/2018.
 */
interface IEVTwilioCallSubscribedChannelsCallBack {
    fun onSuccess(channels: List<Channel>)
    fun onFailure()
}