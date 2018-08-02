package com.estacionvital.patienthub.util

/**
 * Created by dusti on 24/02/2018.
 */

//Endpoints API
//URL de api netmobile
const val MAIN_URL: String = "http://app.netmovil.co/appestacionvital/"
//URL de api ev para el blog
const val EV_BLOG_HOST_URL = "https://blog.estacionvital.com/api/core/"
//URL de api para main
const val EV_MAIN_URL: String = "https://dev.estacionvital.com/api/"
//URL de donde se parte para formar la URL para los documentos
const val EV_MAIN_DOCS_URL: String = "https://dev.estacionvital.com"
const val URL_VERIFY_NUMBER: String = "pin/number_validate"
const val NETMOBILE_AUTH_CREDENTIAL: String = "bm1Fc3RhY2lvblZpdGFsOjIwMTdFViRWMQ=="
//AUTH Credential de header para el api de estacion vital
const val EV_AUTH_CREDENTIAL: String = "RSR0QGMxbyRhcHAjOjIwMThFdkBwMSM="
//ENDPOINTS de API Netmobile
const val URL_SEND_SMS: String = "pin/send"
const val URL_VALIDATE_PIN: String = "pin/validate"
const val URL_RETRIEVE_SUBSCRIPTION_CATALOG = "pin/service"
const val URL_RETRIEVE_SUBSCRIPTION_LIMIT = "pin/limite_subscripciones_ev"
const val URL_RETRIEVE_SUBSCRIPTION_ACTIVE = "pin/servicios_activos_ev"
const val URL_RETRIEVE_SUBSCRIPTION_TOTAL = "pin/servicios_activos"
const val URL_NEW_CLUB_SUBSCRIPTION = "pin/alta"
//ENDPOINTS DEL API DE EV
const val URL_EV_NEW_REGISTRATION = "auth/register"
const val URL_EV_RETRIEVE_PROFILE = "user"
const val URL_EV_UPDATE_PROFILE = "user/update"
const val URL_EV_BLOG_GET_CATEGORIES = "get_category_index/"
const val URL_EV_BLOG_GET_ARTICLES_BY_CATEGORY = "get_category_posts/"
const val URL_EV_BLOG_GET_RECENT_ARTICLES = "get_recent_posts/"
const val URL_EV_RETRIEVE_SPECIALTIES = "doctors/specialities/"
const val URL_EV_RETRIEVE_EXAMINATIONS = "user/examinations/"
const val URL_EV_RETRIEVE_DOCTORS_AVAILABILITY = "doctors/availability/"
const val URL_EV_CREATE_NEW_EXAMINATION = "examination/new/"
const val URL_EV_VALIDATE_COUPON = "coupon/validate/"
const val URL_EV_GET_DOCUMENTS = "user/documents"
const val URL_EV_PAYMENT_CREDIT_CARD = "examination/exec_payment"
const val URL_EV_GET_CHANNEL_BY_ID = "examination/get_by_channel"

//Endpoints API EV
const val URL_EV_LOGIN: String = "auth/sign-in"
const val URL_EV_LOGOUT: String = "auth/sign-out"

//Constantes de validacion dee netmobile de suscripciones
const val MAX_TOTAL_SUSCRIPTIONS = 5

//Constantes para el tipo de chat
const val CHAT_FREE: String = "free"
const val CHAT_PREMIUM: String = "paid"
const val CHAT_PAID: String = "paid"

//Constantes para el tipo de examinacion
const val EXAMINATION_TYPE_CHAT = "chat"
const val EXAMINATION_TYPE_VIDEO = "video"
//Paginacion para definir cantidad de items por pagina para el blog
const val PAGE_SIZE_BLOG_ARTICLES = 3
//Page size para la pantalla de artículos recientes
const val RECENT_ARTICLES_PAGE_SIZE = 10

const val REGISTRATION_COMPLETE = "registrationComplete"
//Constante para diferenciar tipo de pago por cupón o por tarjeta de crédito
const val PAYMENT_TYPE_COUPON = "coupon"
const val PAYMENT_TYPE_CREDIT_CARD = "order"

//Las URL que se muestran en Inicio para las tres imágenes, siendo la segunda para el chat
const val URL_MOVISTAR_IMG_1 = "https://blog.estacionvital.com/wp-content/themes/better-health/assets/ICATEGORIAS/BANNER_INICIO_1.jpg"
const val URL_MOVISTAR_IMG_2 = "https://blog.estacionvital.com/wp-content/themes/better-health/assets/ICATEGORIAS/BANNER_INICIO_2.jpg"
const val URL_MOVISTAR_IMG_3 = "https://blog.estacionvital.com/wp-content/themes/better-health/assets/ICATEGORIAS/BANNER_INICIO_3.jpg"

const val CONSULTANCE_PRICE = "$6.99"
