package com.estacionvital.patienthub.data.remote.Callbacks

import com.twilio.chat.ChannelDescriptor
import com.twilio.chat.Paginator

/**
 * Created by dusti on 17/03/2018.
 */
interface IEVTwilioCallPublicChannelsCallBack {
    fun onSuccess(channels: Paginator<ChannelDescriptor>)
    fun onFailure()
}