package com.estacionvital.patienthubestacionvital.util

/**
 * Created by dusti on 24/02/2018.
 */

//Endpoints API
const val MAIN_URL: String = "http://app.netmovil.co/appestacionvital/"
const val EV_BLOG_HOST_URL = "https://blog.estacionvital.com/api/core/"
const val EV_MAIN_URL: String = "https://dev.estacionvital.com/api/"
const val URL_VERIFY_NUMBER: String = "pin/number_validate"
const val NETMOBILE_AUTH_CREDENTIAL: String = "bm1Fc3RhY2lvblZpdGFsOjIwMTdFViRWMQ=="
const val EV_AUTH_CREDENTIAL: String = "RSR0QGMxbyRhcHAjOjIwMThFdkBwMSM="
const val URL_SEND_SMS: String = "pin/send"
const val URL_VALIDATE_PIN: String = "pin/validate"
const val URL_RETRIEVE_SUBSCRIPTION_CATALOG = "pin/service"
const val URL_RETRIEVE_SUBSCRIPTION_LIMIT = "pin/limite_subscripciones_ev"
const val URL_RETRIEVE_SUBSCRIPTION_ACTIVE = "pin/servicios_activos_ev"
const val URL_NEW_CLUB_SUBSCRIPTION = "pin/alta"
const val URL_EV_NEW_REGISTRATION = "auth/register"
const val URL_EV_RETRIEVE_PROFILE = "user"
const val URL_EV_UPDATE_PROFILE = "user/update"
const val URL_EV_BLOG_GET_CATEGORIES = "get_category_index/"
const val URL_EV_BLOG_GET_ARTICLES_BY_CATEGORY = "get_category_posts/"
const val URL_EV_RETRIEVE_SPECIALTIES = "doctors/specialities/"
const val URL_EV_RETRIEVE_EXAMINATIONS = "user/examinations/"
const val URL_EV_RETRIEVE_DOCTORS_AVAILABILITY = "doctors/availability/"
const val URL_EV_CREATE_NEW_EXAMINATION = "examination/new/"
const val URL_EV_VALIDATE_COUPON = "coupon/validate/"
const val URL_EV_GET_DOCUMENTS = "user/documents"

//Endpoints API EV
const val URL_EV_LOGIN: String = "auth/sign-in"
const val URL_EV_LOGOUT: String = "auth/sign-out"


//Constantes para el tipo de chat
const val CHAT_FREE: String = "free"
const val CHAT_PREMIUM: String = "premium"

//Constantes para el tipo de examinacion
const val EXAMINATION_TYPE_CHAT = "chat"
const val EXAMINATION_TYPE_VIDEO = "video"
//Pagination
const val PAGE_SIZE_BLOG_ARTICLES = 3

const val REGISTRATION_COMPLETE = "registrationComplete"
