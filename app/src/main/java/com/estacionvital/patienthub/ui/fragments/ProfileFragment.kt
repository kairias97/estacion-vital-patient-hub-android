package com.estacionvital.patienthub.ui.fragments

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import android.support.design.widget.TextInputEditText
import android.support.v4.app.Fragment
import android.view.*
import android.widget.TextView
import android.widget.Toast
import com.estacionvital.patienthub.R
import com.estacionvital.patienthub.data.remote.EstacionVitalRemoteDataSource
import com.estacionvital.patienthub.model.EVUserProfile
import com.estacionvital.patienthub.model.EVUserSession
import com.estacionvital.patienthub.presenter.implementations.ProfilePresenterImpl
import com.estacionvital.patienthub.ui.activities.EditProfileActivity
import com.estacionvital.patienthub.ui.activities.MainActivityDrawer
import com.estacionvital.patienthub.ui.fragmentViews.IProfileFragmentView


/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [ProfileFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [ProfileFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ProfileFragment : Fragment(), IProfileFragmentView {
    private var mListener: OnFragmentInteractionListener? = null
    private lateinit var mTextName: TextInputEditText
    private lateinit var mTextLastName: TextInputEditText
    private lateinit var mTextEmail: TextInputEditText

    private lateinit var mProfilePresenterImpl: ProfilePresenterImpl

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mProfilePresenterImpl = ProfilePresenterImpl(this, EstacionVitalRemoteDataSource.INSTANCE)

        mProfilePresenterImpl.retrieveEVUserProfile()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        activity.invalidateOptionsMenu()
        setHasOptionsMenu(true)
    }

    override fun onResume() {
        super.onResume()

    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view =inflater!!.inflate(R.layout.fragment_profile, container, false)
        mTextName = view.findViewById<TextInputEditText>(R.id.edit_text_name_profile)
        mTextLastName = view.findViewById<TextInputEditText>(R.id.edit_text_last_name_profile)
        mTextEmail = view.findViewById<TextInputEditText>(R.id.edit_text_email_profile)

        mTextName.isEnabled = false
        mTextLastName.isEnabled = false
        mTextEmail.isEnabled = false

        mTextName.isFocusable = false
        mTextLastName.isFocusable = false
        mTextEmail.isFocusable = false

        return view
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater!!.inflate(R.menu.menu_profile,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item!!.itemId){
            R.id.action_nav_edit_profile -> {
                navToEditProfile()
            }
        }
        return true
    }
    // TODO: Rename method, update argument and hook method into UI event
    fun onButtonPressed(uri: Uri) {
        if (mListener != null) {
            mListener!!.onFragmentInteraction(uri)
        }
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        activity.title="Perfil"
        if (context is OnFragmentInteractionListener) {
            mListener = context
        } else {
            throw RuntimeException(context!!.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        mListener = null
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments](http://developer.android.com/training/basics/fragments/communicating.html) for more information.
     */
    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(uri: Uri)
    }

    override fun showLoadingProgress() {

    }

    override fun showErrorLoading() {

    }

    override fun navToEditProfile() {
        val editIntent = Intent(activity.applicationContext, EditProfileActivity::class.java)
        startActivity(editIntent)
    }

    override fun getProfileData(data: EVUserProfile) {
        EVUserSession.instance.userProfile = data

        mTextName.setText(EVUserSession.instance.userProfile.name)
        mTextLastName.setText(EVUserSession.instance.userProfile.last_name)
        mTextEmail.setText(EVUserSession.instance.userProfile.email)
    }

    override fun hideLoadingProgress() {

    }

    companion object {
        // TODO: Rename parameter arguments, choose names that match
        // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
        private val ARG_PARAM1 = "param1"
        private val ARG_PARAM2 = "param2"

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ProfileFragment.
         */
        // TODO: Rename and change types and number of parameters
        fun newInstance(): ProfileFragment {
            val fragment = ProfileFragment()
            val args = Bundle()
            return fragment
        }
    }
}// Required empty public constructor
