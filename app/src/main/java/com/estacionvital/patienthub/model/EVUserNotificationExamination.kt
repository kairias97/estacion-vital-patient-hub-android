package com.estacionvital.patienthub.model

import com.google.gson.annotations.SerializedName

/**
 * Created by kevin on 11/4/2018.
 */
class EVUserNotificationExamination(@SerializedName("twilio_channel_name") val channel_name: String,
                                    @SerializedName("finished") val finished: Boolean,
                                    @SerializedName("specialty") val specialty: String,
                                    @SerializedName("examination_type") val examinationType: String,
                                    @SerializedName("twilio_channel_sid") val twilioChannel: String,
                                    @SerializedName("service_type") val serviceType: String,
                                    @SerializedName("doctor") val doctorName: String,
                                    @SerializedName("twillio_token") val twilioToken: String
                                    )