package com.estacionvital.patienthub.ui.activities

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import com.crashlytics.android.Crashlytics
import com.estacionvital.patienthub.ui.views.IBaseView
import io.fabric.sdk.android.Fabric
import android.app.ProgressDialog
import android.view.View


/**
 * Created by kevin on 22/2/2018.
 */
abstract class BaseActivity: AppCompatActivity(), IBaseView {

    override fun showProgressDialog() {
        //Show progress dialog here

    }

    override fun hideProgressDialog() {
        //Logic to hide progress dialog

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Para setear crashlytics a nivel de todas las activities
        Fabric.with(this, Crashlytics())
        //Para que todas las activities hijas sean portrait
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    }
}