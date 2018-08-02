package com.estacionvital.patienthub.data.remote

import android.content.Context
import com.estacionvital.patienthub.data.remote.Callbacks.*
import com.estacionvital.patienthub.model.EVChatSession
import com.twilio.chat.*
import java.io.IOException

/**
 * Created by dusti on 17/03/2018.
 */
//Clase que se comunica con retrofit para operaciones de API de twilio para hacer operaciones sobre twilio
class EVTwilioChatRemoteDataSource {
    //Setup de twilio client con twilio token
    fun setupTwilioClient(twilio_token: String, context: Context, callback: IEVTwilioClientCallback){
        if(EVChatSession.instance.isChatClientCreated){
            callback.onSuccess()
        }
        else{
            val props: ChatClient.Properties = ChatClient.Properties.Builder().createProperties()
            ChatClient.create(context, twilio_token,
                    props, object: CallbackListener<ChatClient>(){
                override fun onSuccess(p0: ChatClient?) {
                    //
                    //Se mantiene vivo el objeto en un singleton de sesion
                    EVChatSession.instance.chatClient = p0!!
                    EVChatSession.instance.chatClient?.setListener(object: ChatClientListener {
                        override fun onChannelDeleted(p0: Channel?) {

                        }

                        override fun onClientSynchronization(p0: ChatClient.SynchronizationStatus?) {
                            //aca es para capturar y setear si el cliente está creado ya una vez inicializado
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
    //obtener lista de canales a los que esta suscrito este usuario de twilio
    fun callSubscribedChannels(callback: IEVTwilioCallSubscribedChannelsCallBack){
        val channels: List<Channel>? = EVChatSession.instance.chatClient?.channels?.subscribedChannels
        callback.onSuccess(if (channels == null) ArrayList<Channel>() else channels!!)
    }
    /* Se uso solo para pruebas iniciales con twilio
    fun callPublicChannels(callback: IEVTwilioCallPublicChannelsCallBack){
        EVChatSession.instance.chatClient?.channels?.getPublicChannelsList(object: CallbackListener<Paginator<ChannelDescriptor>>(){
            override fun onSuccess(p0: Paginator<ChannelDescriptor>?) {
                callback.onSuccess(p0!!)
            }
            override fun onError(errorInfo: ErrorInfo?) {
                super.onError(errorInfo)
                callback.onFailure()
            }
        })
    }*/
    //obtener twilio channel por id de canal
    fun findChannelByID(channel_id: String, callback:IEVTwilioFindChannelByIDCallback){
        EVChatSession.instance.chatClient?.channels?.getChannel(channel_id,
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
    //unirse a canal de twilio
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
    //obtener ultimos mensajes de un canal de chat de twilio
    fun getLastMessagesFromChannel(channel: Channel, callback: IEVTwilioGetLastMessagesFromChannelCalBack){
        channel?.messages?.getLastMessages(50, object: CallbackListener<List<Message>>(){
            override fun onSuccess(p0: List<Message>?) {
                callback.onSuccess(p0!!)
            }

            override fun onError(errorInfo: ErrorInfo?) {
                super.onError(errorInfo)
                callback.onFailure()
            }
        })
    }
    //suscribir a listener de un canal de twilio a fin de manejar ciertos eventos
    fun subscribeToAddedMessages(channel: Channel, callback: IEVTwilioMessageAddedCallBack, callbackMemberAdded: IEVMemberAddedCallBack, callbackMemberDeleted: IEVMemberDeletedCallBack){
        channel.addListener(object: ChannelListener{
            //Detectar si un miembro se sale del canal
            override fun onMemberDeleted(p0: Member?) {
                try{
                    callbackMemberDeleted.onSuccess()
                }
                catch(e: IOException){

                }
            }

            override fun onTypingEnded(p0: Member?) {

            }


            override fun onMessageDeleted(p0: Message?) {

            }
            //Para cuando el doctor se une
            override fun onMemberAdded(p0: Member?) {
                p0!!.getUserDescriptor(object: CallbackListener<UserDescriptor>(){
                    override fun onSuccess(p0: UserDescriptor?) {
                        callbackMemberAdded.onSuccess(p0!!.friendlyName)
                    }

                    override fun onError(errorInfo: ErrorInfo?) {
                        super.onError(errorInfo)
                    }
                })
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
    //Enviar mensaje de twilio con su sdk
    fun sendMessage(channel: Channel, body: String, callback: IEVTwilioSendMessageCallBack){
        channel.messages.sendMessage(Message.options().withBody(body),object: CallbackListener<Message>(){
            override fun onSuccess(p0: Message?) {
                callback.onSuccess()
            }

        })
    }
    //Singleton de esta clase
    companion object {
        val instance: EVTwilioChatRemoteDataSource by lazy { EVTwilioChatRemoteDataSource() }
    }
}