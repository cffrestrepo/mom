package com.functional.mom.commons

class Constants {

    companion object {
        // DATA BASE
        const val VERSION_DATA_BASE = 3
        const val TABLE_NAME_PRODUCT = "product"
        const val NAME_DATA_BASE = "mom_database"

        // RETROFIT
        const val BASE_URL = "https://api.mercadolibre.com/"
        const val HTTP = "http://"
        const val HTTPS = "https://"

        // COLOR
        const val GREEN_BACK = "#09AF00"
        const val RED_BACK = "#B00020"

        // RED
        const val BAD_REQUEST = 400
        const val INTERNAL_SERVER = 500
        const val UNAUTHORIZED = 401
        const val NOT_FOUND = 404
        const val NET_WORK_CONNECTION = 10004
        const val UNEXPECTED = 10005
        const val UNKNOWN = 10006

        // MESSAGES
        const val UNEXPECTED_MESSAGE = "Ups, ocurrio un error inesperado, código: $UNEXPECTED"
        const val UNKNOWN_MESSAGE_SERVER = "El servidor no entrego resultados, código: $UNKNOWN"

        // BUNDLE
        const val PRODUCT = "product"
    }
}
