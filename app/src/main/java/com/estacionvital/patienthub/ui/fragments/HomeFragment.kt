package com.estacionvital.patienthub.ui.fragments

import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.estacionvital.patienthub.R

/**
 * Created by kevin on 15/4/2018.
 */
//Still to implement view when needed as interaction from a server
class HomeFragment: Fragment() {

    private lateinit var mListener: HomeFragmentListener
    private lateinit var mMovistarRecyclerView: RecyclerView
    private lateinit var mRecentArticlesBannerConstraintLayout: ConstraintLayout

    companion object {
        fun newInstance(listener: HomeFragmentListener): HomeFragment {
            val fragment = HomeFragment()
            fragment.mListener = listener
            return fragment
        }
    }
    interface HomeFragmentListener {
        fun onRecentArticlesBannerSelected()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Here we instantiate and execute a presenter method for loading movistar info
    }
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_home, container, false)
        mMovistarRecyclerView = view.findViewById(R.id.recycler_movistar)
        mMovistarRecyclerView.setHasFixedSize(true)
        mMovistarRecyclerView.layoutManager = LinearLayoutManager(activity)
        activity.title = getString(R.string.drawer_home)
        mRecentArticlesBannerConstraintLayout = view.findViewById(R.id.constraint_layout_view_articles)
        mRecentArticlesBannerConstraintLayout.setOnClickListener {
            this.mListener?.onRecentArticlesBannerSelected()
        }
        return view
    }

}