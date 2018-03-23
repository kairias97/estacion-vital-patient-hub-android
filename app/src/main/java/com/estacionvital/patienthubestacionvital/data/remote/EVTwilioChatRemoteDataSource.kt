package com.estacionvital.patienthubestacionvital.data.remote

import android.content.Context
import com.estacionvital.patienthubestacionvital.data.remote.Callbacks.*
import com.estacionvital.patienthubestacionvital.model.EVChatSession
import com.twilio.chat.*

/**
 * Created by dusti on 17/03/2018.
 */
class EVTwilioChatRemoteDataSource {
    fun setupTwilioClient(twilio_token: String, context: Context, callback: IEVTwilioClientCallback){
        if(EVChatSession.instance.isChatClientCreated){
            callback.onSuccess()
        }
        else{
            val props: ChatClient.Properties = ChatClient.Properties.Builder().createProperties()
            ChatClient.create(context, twilio_token,
                    props, object: CallbackListener<ChatClient>(){
                override fun onSuccess(p0: ChatClient?) {
                    EVChatSession.instance.chatClient = p0!!
                    EVChatSession.instance.chatClient.setListener(object: ChatClientListener {
                        override fun onChannelDeleted(p0: Channel?) {

                        }

                        override fun onClientSynchronization(p0: ChatClient.SynchronizationStatus?) {
                            //aca
                            if(p0!! == ChatClient.SynchronizationStatus.COMPLETED || p0!! == ChatClient.SynchronizationStatus.CHANNELS_COMPLETED){
                                EVChatSession.instance.isChatClientCreated = true
                                callback.onSuccess()
                            }
                        }

                        override fun onNotificationSubscribed() {

                        }

                        override fun onUserSubscribed(p0: User?) {

                        }

                        override fun onChannelUpdated(p0: Channel?, p1: Channel.UpdateReason?) {

                        }

                        override fun onNotificationFailed(p0: ErrorInfo?) {

                        }

                        override fun onChannelJoined(p0: Channel?) {

                        }

                        override fun onChannelAdded(p0: Channel?) {

                        }

                        override fun onChannelSynchronizationChange(p0: Channel?) {

                        }

                        override fun onNotification(p0: String?, p1: String?) {

                        }

                        override fun onUserUnsubscribed(p0: User?) {

                        }

                        override fun onChannelInvited(p0: Channel?) {

                        }

                        override fun onConnectionStateChange(p0: ChatClient.ConnectionState?) {

                        }

                        override fun onError(p0: ErrorInfo?) {

                        }

                        override fun onUserUpdated(p0: User?, p1: User.UpdateReason?) {

                        }

                    })
                }

                override fun onError(errorInfo: ErrorInfo?) {
                    super.onError(errorInfo)
                }
            })
        }
    }
    fun callSubscribedChannels(callback: IEVTwilioCallSubscribedChannelsCallBack){
        val channels: List<Channel> = EVChatSession.instance.chatClient.channels.subscribedChannels
        if(channels.count()==0){
            callback.onFailure()
        }
        else if(channels.count()>0){
            callback.onSuccess(channels)
        }
    }
    fun callPublicChannels(callback: IEVTwilioCallPublicChannelsCallBack){
        EVChatSession.instance.chatClient.channels.getPublicChannelsList(object: CallbackListener<Paginator<ChannelDescriptor>>(){
            override fun onSuccess(p0: Paginator<ChannelDescriptor>?) {
                callback.onSuccess(p0!!)
            }
            override fun onError(errorInfo: ErrorInfo?) {
                super.onError(errorInfo)
                callback.onFailure()
            }
        })
    }
    fun findChannelByID(channel_id: String, callback:IEVTwilioFindChannelByIDCallback){
        EVChatSession.instance.chatClient.channels.getChannel(channel_id,
                object: CallbackListener<Channel>(){
                    override fun onSuccess(p0: Channel?) {
                        callback.onSuccess(p0!!)
                    }

                    override fun onError(errorInfo: ErrorInfo?) {
                        super.onError(errorInfo)
                        callback.onFailure()
                    }

                })
    }
    fun joinChannel(channel: Channel, callback: IEVTwilioJoinChannelCallBack){
        channel.join(object: StatusListener(){
            override fun onSuccess() {
                callback.onSuccess()
                /*Log.i("channel", p0!!.createdBy)\

                Log.i("channelList", p0!!.members.membersList.toString())*/
            }

            override fun onError(errorInfo: ErrorInfo?) {
                super.onError(errorInfo)
                callback.onFailure()
            }

        })
    }
    fun getLastMessagesFromChannel(channel: Channel, callback: IEVTwilioGetLastMessagesFromChannelCalBack){
        channel.messages.getLastMessages(50, object: CallbackListener<List<Message>>(){
            override fun onSuccess(p0: List<Message>?) {
                callback.onSuccess(p0!!)
            }

            override fun onError(errorInfo: ErrorInfo?) {
                super.onError(errorInfo)
                callback.onFailure()
            }
        })
    }
    fun subscribeToAddedMessages(channel: Channel, callback: IEVTwilioMessageAddedCallBack){
        channel.addListener(object: ChannelListener{
            override fun onMemberDeleted(p0: Member?) {

            }

            override fun onTypingEnded(p0: Member?) {

            }

            override fun onMessageDeleted(p0: Message?) {

            }

            override fun onMemberAdded(p0: Member?) {

            }

            override fun onTypingStarted(p0: Member?) {

            }

            override fun onSynchronizationChanged(p0: Channel?) {

            }

            override fun onMessageUpdated(p0: Message?, p1: Message.UpdateReason?) {

            }

            override fun onMemberUpdated(p0: Member?, p1: Member.UpdateReason?) {

            }

            override fun onMessageAdded(p0: Message?) {
                callback.onSuccess(p0!!)
            }
        })
    }
    fun sendMesage(channel: Channel, body: String, callback: IEVTwilioSendMessageCallBack){
        channel.messages.sendMessage(Message.options().withBody(body),object: CallbackListener<Message>(){
            override fun onSuccess(p0: Message?) {
                callback.onSuccess()
            }

        })
    }
    companion object {
        val instance: EVTwilioChatRemoteDataSource by lazy { EVTwilioChatRemoteDataSource() }
    }
}