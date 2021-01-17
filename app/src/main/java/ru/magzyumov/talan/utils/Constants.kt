package ru.magzyumov.talan.utils

interface Constants {

    interface Preferences {
        companion object {
            const val GENERAL_PREFERENCES = "general_preferences"
            const val USER_NAME = "user_name"
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