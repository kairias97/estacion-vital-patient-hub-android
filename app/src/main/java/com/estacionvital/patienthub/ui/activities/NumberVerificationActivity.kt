package com.estacionvital.patienthub.ui.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.estacionvital.patienthub.R
import com.estacionvital.patienthub.ui.views.INumberVerificationView

class NumberVerificationActivity : BaseActivity(), INumberVerificationView {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_number_verification)
        supportActionBar?.hide()
    }

    override fun verifyNumber() {
        //metodo para verificar el numero
    }
}
