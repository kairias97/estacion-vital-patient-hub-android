package com.estacionvital.patienthub.ui.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.estacionvital.patienthub.R
import com.estacionvital.patienthub.model.EVClub
import com.estacionvital.patienthub.ui.Adapters.EVClubAdapter

class ClubSuscriptionActivity : AppCompatActivity() {

    public var adapter: EVClubAdapter = EVClubAdapter(generateListf())
    public lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_club_suscription)
        recyclerView = findViewById<RecyclerView>(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = adapter
    }

    fun generateListf(): List<EVClub>{
        var lista: MutableList<EVClub> = ArrayList<EVClub>()
        for (i in 1..10){
            lista.add(EVClub("1","prueba",false, true))
        }
        return lista
    }
}
