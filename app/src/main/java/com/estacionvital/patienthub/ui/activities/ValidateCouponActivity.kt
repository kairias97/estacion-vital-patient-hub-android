package com.estacionvital.patienthub.ui.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.estacionvital.patienthub.R
import com.estacionvital.patienthub.ui.views.IValidateCouponView
import com.estacionvital.patienthub.util.toast

class ValidateCouponActivity : BaseActivity(), IValidateCouponView {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_validate_coupon)
    }

    override fun showValidateLoading() {
        this.showProgressDialog(getString(R.string.message_validating_coupon))
    }

    override fun hideLoading() {
        this.hideProgressDialog()
    }

    override fun showErrorLoading() {
        this.toast(getString(R.string.generic_500_error))
    }

    override fun isValid(isValid: Boolean) {
        this.toast("trae la validez del cupon")
    }
}
