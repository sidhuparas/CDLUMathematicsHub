package com.parassidhu.cdlumaths.activities

import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.ads.AdView
import com.parassidhu.cdlumaths.R
import com.parassidhu.cdlumaths.adapters.QueAdapter
import com.parassidhu.cdlumaths.utils.AppUtils
import com.parassidhu.cdlumaths.utils.ItemClickSupport
import com.parassidhu.cdlumaths.utils.MyApp

class sem3 : AppCompatActivity() {

    private val subject_names = arrayOf(
            "Advanced Calculus",
            "Partial Differential Equations",
            "Statics",
            "Differential Geometry",
            "Probability Distributions",
            "Structured System Analysis and Design",
            "Internet And Web Designing",
            "Hindi"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sem3)
        setupView()
        initViews()

        AppUtils.renderTheme(this)
        val adView = this.findViewById<AdView>(R.id.adView)
        AppUtils.displayAds(this, adView)

        val rcl = findViewById<RecyclerView>(R.id.card_recycler_view)
        ItemClickSupport.addTo(rcl).setOnItemClickListener { recyclerView, position, v ->
            try {
                val m = applicationContext as MyApp
                m.getClickSem3(position)
                registerForContextMenu(recyclerView)
                openContextMenu(v)
            } catch (ex: Exception) {
            }
        }
    }

    override fun onCreateContextMenu(menu: ContextMenu?, v: View?,
                                     menuInfo: ContextMenu.ContextMenuInfo?) {
        super.onCreateContextMenu(menu, v, menuInfo)
        val inflater = menuInflater
        inflater.inflate(R.menu.sem3, menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val m = applicationContext as MyApp
        val add = "CDLU/sem3/2015/"

        when (item.itemId) {
            R.id.download    //December 2015
            -> {
                when (m.getit3()) {
                    0 -> AppUtils.startDownload("Advanced Calculus (Dec 15).pdf",
                            add + "AdvancedCalculus(Dec14).pdf", this)
                    1 -> AppUtils.startDownload("Partial Differential Equations (Dec 15).pdf",
                            add + "Partial%20Differential%20Equations%20(Dec%2014).pdf", this)
                    2 -> AppUtils.startDownload("Statics (Dec 15).pdf", add + "Statics%20(Dec%2014).pdf", this)
                    3 -> AppUtils.startDownload("Differential Geometry (Dec 15).pdf",
                            add + "Differential%20Geometry%20(Dec%2014).pdf", this)
                    4 -> AppUtils.startDownload("Probability Distributions (Dec 15).pdf",
                            add + "Probability%20Distributions%20(Dec%2014).pdf", this)
                    5 -> AppUtils.startDownload("Structured System Analysis and Design (Dec 15).pdf",
                            add + "Structured%20System%20Analysis%20And%20Design%20(Dec%2014).pdf", this)
                    6 -> AppUtils.startDownload("Internet And Web Designing (Dec 15).pdf",
                            add + "Internet%20And%20Web%20Designing%20(Dec%2014).pdf", this)
                    7 -> AppUtils.startDownload("Hindi-I (Dec 15).pdf",
                            add + "Hindi-I%20(Dec%2014).pdf", this)
                    8 -> AppUtils.startDownload("Complete Sem 3 (Dec 15).pdf",
                            add + "Complete%20Sem%203%20(Dec%2014).pdf", this)
                }
                return true
            }
            R.id.download2 -> {
                downloadQuestionPapers("6")
                return true
            }
            R.id.download3 -> {
                downloadQuestionPapers("7")
                return true
            }
            R.id.dec18 -> {
                downloadQuestionPapers("8")
                return true
            }

            else -> return super.onContextItemSelected(item)
        }
    }

    private fun downloadQuestionPapers(y: String) {
        val m = applicationContext as MyApp
        val URL = "CDLU/sem3/201$y/"

        when (m.getit3()) {
            0 -> AppUtils.startDownload("Advanced Calculus (Dec 1$y).pdf", URL + "AC.pdf", this)
            1 -> AppUtils.startDownload("Partial Differential Equations (Dec 1$y).pdf", URL + "PDE.pdf", this)
            2 -> AppUtils.startDownload("Statics (Dec 1$y).pdf", URL + "S.pdf", this)
            3 -> AppUtils.startDownload("Differential Geometry (Dec 1$y).pdf", URL + "DG.pdf", this)
            4 -> AppUtils.startDownload("Probability Distributions (Dec 1$y).pdf", URL + "PD.pdf", this)
            5 -> AppUtils.startDownload("Structured System Analysis and Design (Dec 1$y).pdf", URL + "SSAD.pdf", this)
            6 -> AppUtils.startDownload("Internet And Web Designing (Dec 1$y).pdf", URL + "IWD.pdf", this)
            7 -> AppUtils.startDownload("Hindi-I (Dec 1$y).pdf", URL + "H.pdf", this)
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
        val download = findViewById<Button>(R.id.downloadLiveHTML)
        download.setOnClickListener {
            AppUtils.openWebPage(this@sem3,
                    "http://www.downloadinformer.com/2016/07/live-html-makes-html-coding-easier.html")
        }

        recyclerView.setHasFixedSize(true)
        val layoutManager = GridLayoutManager(applicationContext, 2)
        recyclerView.layoutManager = layoutManager

        val adapter = QueAdapter(applicationContext,
                AppUtils.prepareDataForQuePap(subject_names))

        recyclerView.adapter = adapter
    }
}
