package com.estacionvital.patienthub.ui.activities

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import com.crashlytics.android.Crashlytics
import com.estacionvital.patienthub.ui.views.IBaseView
import io.fabric.sdk.android.Fabric
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.View
import com.estacionvital.patienthub.R
import android.app.ProgressDialog




/**
 * Created by kevin on 22/2/2018.
 */
abstract class BaseActivity: AppCompatActivity(), IBaseView {

    protected var mProgressDialog: ProgressDialog? = null
    override fun showProgressDialog() {
        //Show progress dialog here
        if (mProgressDialog == null) {
            mProgressDialog = ProgressDialog(this)
        }
        mProgressDialog?.setMessage("Validando numero Movistar")
        mProgressDialog?.isIndeterminate = true
        mProgressDialog?.setProgressStyle(ProgressDialog.STYLE_SPINNER)
        mProgressDialog?.setCancelable(false)
        mProgressDialog?.show()


    }

    override fun hideProgressDialog() {
        //Logic to hide progress dialog
        if (mProgressDialog != null) {
            mProgressDialog?.dismiss()
            mProgressDialog = null
        }

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Fabric.with(this, Crashlytics())
        //Activity setup orientation and toolbar
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.mipmap.ic_toolbar_home_logo)


    }
}