package com.estacionvital.patienthub.ui.activities

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import com.crashlytics.android.Crashlytics
import com.estacionvital.patienthub.ui.views.IBaseView
import io.fabric.sdk.android.Fabric
import com.estacionvital.patienthub.R
import android.app.ProgressDialog
import android.content.DialogInterface
import android.content.Intent
import com.estacionvital.patienthub.util.toast


/**
 * Created by kevin on 22/2/2018.
 */
abstract class BaseActivity: AppCompatActivity(), IBaseView {

    protected var mProgressDialog: ProgressDialog? = null

    //Mostrar mensaje de progreso
    override fun showProgressDialog(msg:String) {
        //Show progress dialog here
        if (mProgressDialog == null) {
            mProgressDialog = ProgressDialog(this)
        }
        mProgressDialog?.setMessage(msg)
        mProgressDialog?.isIndeterminate = true
        mProgressDialog?.setProgressStyle(ProgressDialog.STYLE_SPINNER)
        mProgressDialog?.setCancelable(false)
        mProgressDialog?.show()


    }
    //Esconder dialog de progreso
    override fun hideProgressDialog() {
        //Logic to hide progress dialog
        if (mProgressDialog != null) {
            mProgressDialog?.dismiss()
            mProgressDialog = null
        }

    }
    //Mostrar mensaje de sesion caducada
    override fun showExpirationMessage() {
        this.toast(R.string.session_expired_msg)

        val startIntent = Intent(this, NumberVerificationActivity::class.java)
        startIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(startIntent)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Fabric.with(this, Crashlytics())
        //Activity setup orientation and toolbar
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.mipmap.ic_toolbar_home_logo)


    }
    //Para mostrar el dialog de activities
    protected fun showConfirmDialog(titleResId: Int, iconResId: Int, messageResId: Int,
                                    positiveBtnResId: Int, negativeBtnResId: Int,
                                    positiveListener: DialogInterface.OnClickListener,
                                    negativeListener: DialogInterface.OnClickListener
                                    ){
        AlertDialog.Builder(this)
                .setIcon(iconResId)
                .setTitle(titleResId)
                .setMessage(messageResId)
                .setPositiveButton(positiveBtnResId, positiveListener)
                .setNegativeButton(negativeBtnResId, negativeListener)
                .create()
                .show()
    }
    //Para mostrar un dialog de confirmacion
    protected fun showSingleConfirmDialog(titleResId: Int, iconResId: Int, messageResId: Int,
                                          positiveBtnResId: Int,
                                          positiveListener: DialogInterface.OnClickListener){
        AlertDialog.Builder(this)
                .setIcon(iconResId)
                .setTitle(titleResId)
                .setMessage(messageResId)
                .setPositiveButton(positiveBtnResId, positiveListener)
                .create()
                .show()
    }
}