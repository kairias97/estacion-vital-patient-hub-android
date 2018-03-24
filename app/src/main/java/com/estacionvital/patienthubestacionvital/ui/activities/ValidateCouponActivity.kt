package com.estacionvital.patienthubestacionvital.ui.activities

import android.os.Bundle
import com.estacionvital.patienthubestacionvital.R
import com.estacionvital.patienthubestacionvital.ui.views.IValidateCouponView
import com.estacionvital.patienthubestacionvital.util.toast

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
