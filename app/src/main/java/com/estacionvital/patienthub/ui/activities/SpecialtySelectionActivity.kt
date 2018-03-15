package com.estacionvital.patienthub.ui.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.NavUtils
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.estacionvital.patienthub.R
import com.estacionvital.patienthub.data.remote.EstacionVitalRemoteDataSource
import com.estacionvital.patienthub.model.EVSpecialtiesResponse
import com.estacionvital.patienthub.presenter.ISpecialtySelectionPresenter
import com.estacionvital.patienthub.presenter.implementations.SpecialtySelectionPresenterImpl
import com.estacionvital.patienthub.ui.views.ISpecialtySelectionView
import com.estacionvital.patienthub.util.toast

class SpecialtySelectionActivity : BaseActivity(), ISpecialtySelectionView {

    private lateinit var mSpecialtySelectionPresenter: ISpecialtySelectionPresenter
    private lateinit var mSpinner: Spinner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_specialty_selection)

        supportActionBar!!.setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        supportActionBar!!.title = "Seleccione una especialidad"

        mSpinner = findViewById<Spinner>(R.id.spinner_specialties)

        mSpecialtySelectionPresenter = SpecialtySelectionPresenterImpl(this, EstacionVitalRemoteDataSource.INSTANCE)

        mSpecialtySelectionPresenter.retrieveSpecialtiesChat()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item!!.itemId){
            android.R.id.home -> {
                returnTop()
            }
        }
        return true
    }
    private fun returnTop(){
        NavUtils.navigateUpFromSameTask(this)
    }

    override fun showProgressDialog() {
        this.showProgressDialog(getString(R.string.loading_specialties_msg))
    }

    override fun showErrorLoading() {
        this.toast(getString(R.string.generic_500_error))
    }

    override fun hideLoading() {
        this.hideProgressDialog()
    }
    override fun setSpecialtiesData(data: EVSpecialtiesResponse) {
        //settear el recycler
        val adapter: ArrayAdapter<String> = ArrayAdapter<String>(this, R.layout.spinner_item, data.specialtes)
        adapter.setDropDownViewResource(R.layout.spinner_item)
        mSpinner.adapter = adapter
    }
}
