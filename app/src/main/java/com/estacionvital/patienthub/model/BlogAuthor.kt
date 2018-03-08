package com.estacionvital.patienthub.model

import com.google.gson.annotations.SerializedName

/**
 * Created by kevin on 7/3/2018.
 */
data class BlogAuthor(@SerializedName("id") val id: Int,
                      @SerializedName("name") val name:String,
                      @SerializedName("nickname") val nickname:String
                      )