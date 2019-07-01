package com.parassidhu.cdlumaths.activities

import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.ads.AdView
import com.parassidhu.cdlumaths.R
import com.parassidhu.cdlumaths.adapters.QueAdapter
import com.parassidhu.cdlumaths.utils.AppUtils
import com.parassidhu.cdlumaths.utils.ItemClickSupport
import com.parassidhu.cdlumaths.utils.MyApp

class sem2 : AppCompatActivity() {

    private val subject_names = arrayOf(
            "Number Theory and Trigonometry",
            "Ordinary Differential Equations",
            "Vector Calculus",
            "Discrete Mathematics",
            "Regression Analysis And Probability",
            "Digital Logic and Computer Design",
            "Problem Solving Through C",
            "English",
            "Download All"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sem2)

        val adView = this.findViewById<AdView>(R.id.adView)
        AppUtils.displayAds(this, adView)

        setupView()
        initViews()
        val rcl = findViewById<RecyclerView>(R.id.card_recycler_view)
        ItemClickSupport.addTo(rcl).setOnItemClickListener { recyclerView, position, v ->
            try {
                val m = applicationContext as MyApp
                m.getClickSem2(position)
                registerForContextMenu(recyclerView)
                openContextMenu(v)
            } catch (ex: Exception) {
            }
        }
        AppUtils.renderTheme(this)
    }

    override fun onCreateContextMenu(menu: ContextMenu?, v: View?,
                                     menuInfo: ContextMenu.ContextMenuInfo?) {
        super.onCreateContextMenu(menu, v, menuInfo)
        val inflater = menuInflater
        inflater.inflate(R.menu.sem2, menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val m = applicationContext as MyApp
        val add = "CDLU/sem2/2015/"
        val add2 = "CDLU/sem2/2016/"
        val n = "CDLU/sem2/2017/"
        when (item.itemId) {
            R.id.download     //May 2015
            -> {
                when (m.getit2()) {
                    0 -> AppUtils.startDownload("Number Theory and Trigonometry (May 15).pdf",
                            add + "Number%20Theory%20And%20Trigonometry%20(Dec%2014).pdf", this)
                    1 -> AppUtils.startDownload("Ordinary Differential Equations (May 15).pdf",
                            add + "Ordinary%20Differential%20Equations%20(Dec%2014).pdf", this)
                    2 -> AppUtils.startDownload("Vector Calculus (May 15).pdf",
                            add + "Vector%20Calculus%20(Dec%2014).pdf", this)
                    3 -> AppUtils.startDownload("Discrete Mathematics-II (May 15).pdf",
                            add + "Discrete%20Mathematics-II%20(Dec%2014).pdf", this)
                    4 -> AppUtils.startDownload("Regression Analysis And Probability (May 15).pdf",
                            add + "Regression%20Analysis%20And%20Probability%20(Dec%2014).pdf", this)
                    5 -> AppUtils.startDownload("Digital Logic and Computer Design (May 15).pdf",
                            add + "Digital%20Logic%20And%20Computer%20Design%20(Dec%2014).pdf", this)
                    6 -> AppUtils.startDownload("Problem Solving Through C (May 15).pdf",
                            add + "Problem%20Solving%20Through%20C%20(Dec%2014).pdf", this)
                    7 -> AppUtils.startDownload("English-II (May 15).pdf",
                            add + "English-II%20(Dec%2014).pdf", this)
                    8 -> AppUtils.startDownload("Complete Sem 2 (May 15).pdf",
                            add + "Complete%20Sem%202%20(Dec%2014).pdf", this)
                }
                return true
            }
            R.id.download2    //May 2016
            -> {
                when (m.getit2()) {
                    0 -> AppUtils.startDownload("Number Theory and Trigonometry (May 16).pdf",
                            add2 + "Number%20Theory%20And%20Trigonometry.pdf", this)
                    1 -> AppUtils.startDownload("Ordinary Differential Equations (May 16).pdf",
                            add2 + "Ordinary%20Differential%20Equations.pdf", this)
                    2 -> AppUtils.startDownload("Vector Calculus (May 16).pdf",
                            add2 + "Vector%20Calculus.pdf", this)
                    3 -> AppUtils.startDownload("Discrete Mathematics-II (May 16).pdf",
                            add2 + "Discrete%20Mathematics-II.pdf", this)
                    4 -> AppUtils.startDownload("Regression Analysis And Probability (May 16).pdf",
                            add2 + "Regression%20Analysis%20And%20Probability.pdf", this)
                    5 -> AppUtils.startDownload("Digital Logic and Computer Design (May 16).pdf",
                            add2 + "Digital%20Logic%20And%20Computer%20Design.pdf", this)
                    6 -> AppUtils.startDownload("Problem Solving Through C (May 16).pdf",
                            add2 + "Problem%20Solving%20Through%20C.pdf", this)
                    7 -> AppUtils.startDownload("English-II (May 16).pdf",
                            add2 + "English-II.pdf", this)
                    8 -> AppUtils.startDownload("MSc Maths 5-Year 2nd Sem (May 16).pdf",
                            add2 + "MSc%20Maths%202nd%20Sem%20%28May%202016%29.pdf", this)
                }
                return true
            }

            R.id.download3 -> {
                when (m.getit2()) {
                    0 -> AppUtils.startDownload("Number Theory and Trigonometry (May 17).pdf",
                            n + "NTT.pdf", this)
                    1 -> AppUtils.startDownload("Ordinary Differential Equations (May 17).pdf",
                            n + "ODE.pdf", this)
                    2 -> AppUtils.startDownload("Vector Calculus (May 17).pdf",
                            n + "VC.pdf", this)
                    3 -> AppUtils.startDownload("Discrete Mathematics-II (May 17).pdf",
                            n + "DM.pdf", this)
                    4 -> AppUtils.startDownload("Regression Analysis And Probability (May 17).pdf",
                            n + "RAP.pdf", this)
                    5 -> AppUtils.startDownload("Digital Logic and Computer Design (May 17).pdf",
                            n + "DLCD.pdf", this)
                    6 -> AppUtils.startDownload("Problem Solving Through C (May 17).pdf",
                            n + "PSTC.pdf", this)
                    7 -> AppUtils.startDownload("English-II (May 17).pdf",
                            n + "E.pdf", this)
                    8 -> AppUtils.startDownload("MSc Maths 5-Year 2nd Sem (May 17).pdf",
                            n + "ALL.pdf", this)
                }
                return true
            }
            else -> return super.onContextItemSelected(item)
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
