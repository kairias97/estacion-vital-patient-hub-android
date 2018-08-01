package com.estacionvital.patienthub.ui.fragments

import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ScrollView
import com.estacionvital.patienthub.R
import com.estacionvital.patienthub.util.URL_MOVISTAR_IMG_1
import com.estacionvital.patienthub.util.URL_MOVISTAR_IMG_2
import com.estacionvital.patienthub.util.URL_MOVISTAR_IMG_3
import com.squareup.picasso.NetworkPolicy
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.app_bar_main_activity_drawer.*

/**
 * Created by kevin on 15/4/2018.
 */
//Still to implement view when needed as interaction from a server
class HomeFragment: Fragment() {

    private lateinit var mListener: HomeFragmentListener
    private lateinit var mMovistarRecyclerView: LinearLayout
    private lateinit var mRecentArticlesBannerConstraintLayout: ConstraintLayout

    private lateinit var mImg1: ImageView
    private lateinit var mImg2: ImageView
    private lateinit var mImg3: ImageView

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
        mImg1 = view.findViewById(R.id.img_movistar1)
        mImg2 = view.findViewById(R.id.img_movistar2)
        mImg3 = view.findViewById(R.id.img_movistar3)
        activity.title = getString(R.string.drawer_home)
        mRecentArticlesBannerConstraintLayout = view.findViewById(R.id.constraint_layout_view_articles)
        mRecentArticlesBannerConstraintLayout.setOnClickListener {
            this.mListener?.onRecentArticlesBannerSelected()
        }
        Picasso.get()
                .load(URL_MOVISTAR_IMG_1)
                .error(R.drawable.img_logo)
                .fit()
                .into(mImg1)

        Picasso.get()
                .load(URL_MOVISTAR_IMG_2)
                .fit()
                .error(R.drawable.img_logo)
                .into(mImg2)
        Picasso.get()
                .load(URL_MOVISTAR_IMG_3)
                .fit()
                .error(R.drawable.img_logo)
                .into(mImg3)

        mImg2.setOnClickListener {
            activity.fab.open(true)
        }
        return view
    }

}