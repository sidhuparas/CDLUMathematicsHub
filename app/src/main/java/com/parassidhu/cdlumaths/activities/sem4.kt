package com.parassidhu.cdlumaths.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.ContextMenu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View

import com.google.android.gms.ads.AdView
import com.parassidhu.cdlumaths.R
import com.parassidhu.cdlumaths.adapters.QueAdapter
import com.parassidhu.cdlumaths.utils.ItemClickSupport
import com.parassidhu.cdlumaths.utils.MyApp
import com.parassidhu.cdlumaths.utils.AppUtils

import java.net.URL

class sem4 : AppCompatActivity() {

    private val subject_names = arrayOf("Sequences and Series", "Special Functions and Integral Transforms", "Numerical Analysis", "Hydrostatics", "Elementary Inference", "Operating Systems", "Data Structures Using C", "Hindi")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sem4)
        setupView()
        initViews()

        AppUtils.renderTheme(this)
        val adView = this.findViewById<AdView>(R.id.adView)
        AppUtils.displayAds(this, adView)

        val rcl = findViewById<RecyclerView>(R.id.card_recycler_view)
        ItemClickSupport.addTo(rcl).setOnItemClickListener { recyclerView, position, v ->
            try {
                val m = applicationContext as MyApp
                m.getClickSem4(position)
                registerForContextMenu(recyclerView)
                openContextMenu(v)
            } catch (ex: Exception) {
            }
        }
    }

    override fun onCreateContextMenu(menu: ContextMenu, v: View,
                                     menuInfo: ContextMenu.ContextMenuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo)
        val inflater = menuInflater
        inflater.inflate(R.menu.sem4, menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val m = applicationContext as MyApp
        val add = arrayOf("CDLU/sem4/2016/", "CDLU/sem4/2017/", "CDLU/sem4/2018/")

        when (item.itemId) {
            R.id.download    //December 2015
            -> {
                when (m.getit4()) {
                    0 -> AppUtils.startDownload("Sequences and Series (May 16).pdf",
                            add[0] + "Sequence%20And%20Series%20(Dec%2014).pdf", this)
                    1 -> AppUtils.startDownload("Special Functions and Integral Transforms (May 16).pdf",
                            add[0] + "Special%20Functions%20And%20Integral%20Transforms%20(Dec%2014).pdf", this)
                    2 -> AppUtils.startDownload("Numerical Analysis (May 16).pdf",
                            add[0] + "Numerical%20Analysis%20(Dec%2014).pdf", this)
                    3 -> AppUtils.startDownload("Hydrostatics (May 16).pdf",
                            add[0] + "Hydrostatics%20(Dec%2014).pdf", this)
                    4 -> AppUtils.startDownload("Elementary Inference (May 16).pdf",
                            add[0] + "Elementary%20Inference%20(Dec%2014).pdf", this)
                    5 -> AppUtils.startDownload("Operating Systems (May 16).pdf",
                            add[0] + "Operating%20System%20(Dec%2014).pdf", this)
                    6 -> AppUtils.startDownload("Data Structures Using C (May 16).pdf",
                            add[0] + "Data%20Structure%20Using%20C%20(Dec%2014).pdf", this)
                    7 -> AppUtils.startDownload("Hindi-II (May 16).pdf",
                            add[0] + "Hindi-II%20(Dec%2014).pdf", this)
                }
                return true
            }

            R.id.may17 -> {
                downloadPaper(add[1], "7")
                return true
            }
            R.id.may18 -> {
                downloadPaper(add[2], "8")
                return super.onContextItemSelected(item)
            }
            else -> return super.onContextItemSelected(item)
        }
    }

    private fun downloadPaper(URL: String, year: String) {
        val m = applicationContext as MyApp
        when (m.getit4()) {
            0 -> AppUtils.startDownload("Sequences and Series (May 1$year).pdf",
                    URL + "SaS.pdf", this)
            1 -> AppUtils.startDownload("Special Functions and Integral Transforms (May 1$year).pdf",
                    URL + "SFaIT.pdf", this)
            2 -> AppUtils.startDownload("Numerical Analysis (May 1$year).pdf",
                    URL + "NA.pdf", this)
            3 -> AppUtils.startDownload("Hydrostatics (May 1$year).pdf",
                    URL + "H.pdf", this)
            4 -> AppUtils.startDownload("Elementary Inference (May 1$year).pdf",
                    URL + "EI.pdf", this)
            5 -> AppUtils.startDownload("Operating Systems (May 1$year).pdf",
                    URL + "OS.pdf", this)
            6 -> AppUtils.startDownload("Data Structures Using C (May 1$year).pdf",
                    URL + "DSUC.pdf", this)
            7 -> AppUtils.startDownload("Hindi-II (May 1$year).pdf",
                    URL + "Hin.pdf", this)
        }
    }

    fun setupView() {
        val acb = supportActionBar
        acb?.setHomeButtonEnabled(true)
        acb?.setDisplayHomeAsUpEnabled(true)
        acb?.title = "Choose Subject"
    }

    private fun initViews() {
        val recyclerView = findViewById<RecyclerView>(R.id.card_recycler_view)
        recyclerView.setHasFixedSize(true)
        val layoutManager = GridLayoutManager(applicationContext, 2)
        recyclerView.layoutManager = layoutManager

        val adapter = QueAdapter(applicationContext,
                AppUtils.prepareDataForQuePap(subject_names))

        recyclerView.adapter = adapter
    }
}

