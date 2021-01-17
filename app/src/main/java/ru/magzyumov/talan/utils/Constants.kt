package ru.magzyumov.talan.utils

interface Constants {

    interface Preferences {
        companion object {
            const val GENERAL_PREFERENCES = "general_preferences"
            const val JWM_TOKEN = "jwm_token"
        }
    }

    interface RequestCode {
        companion object {
            const val REQUEST_ATTACH_IMAGE = 9960
        }
    }

    interface StringPattern {
        companion object {
            const val TIMESTAMP_PATTERN = "dd MMM yyyy HH:mm"
        }
    }
}