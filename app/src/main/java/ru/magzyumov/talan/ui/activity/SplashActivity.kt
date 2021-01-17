package ru.magzyumov.talan.ui.activity

import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import dagger.android.support.DaggerAppCompatActivity
import ru.magzyumov.talan.utils.Constants.Preferences.Companion.USER_NAME
import ru.magzyumov.talan.utils.PreferenceHelper
import javax.inject.Inject

class SplashActivity: DaggerAppCompatActivity() {

    @Inject
    lateinit var preferenceHelper: PreferenceHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        if (isAuthorized()){
            openMainActivity()
        } else {
            openLoginActivity()
        }
    }

    private fun isAuthorized(): Boolean {
        return preferenceHelper.getStringPreference(USER_NAME) != null
    }

    private fun openMainActivity(){
        startActivity(Intent(this@SplashActivity, MainActivity::class.java))
        finish()
    }

    private fun openLoginActivity(){
        startActivity(Intent(this@SplashActivity, AuthActivity::class.java))
        finish()
    }
}