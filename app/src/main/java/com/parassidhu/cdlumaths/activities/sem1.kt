package com.parassidhu.cdlumaths.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.ContextMenu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View

import com.google.android.gms.ads.AdView
import com.parassidhu.cdlumaths.R
import com.parassidhu.cdlumaths.adapters.QueAdapter
import com.parassidhu.cdlumaths.utils.AppUtils
import com.parassidhu.cdlumaths.utils.ItemClickSupport
import com.parassidhu.cdlumaths.utils.MyApp

class sem1 : AppCompatActivity() {

    private val subject_names = arrayOf(
            "Algebra",
            "Calculus",
            "Solid Geometry",
            "Discrete Mathematics",
            "Descriptive Statistics",
            "Computer Fundamentals",
            "Introduction To Windows Software",
            "English",
            "Download All"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sem1)

        val adView = this.findViewById<AdView>(R.id.adView)

        AppUtils.displayAds(this, adView)
        setupView()
        initViews()
        val rcl = findViewById<RecyclerView>(R.id.card_recycler_view)
        ItemClickSupport.addTo(rcl).setOnItemClickListener { recyclerView, position, v ->
            try {
                val m = applicationContext as MyApp
                m.getClickSem1(position)
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
        inflater.inflate(R.menu.sem1, menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val m = applicationContext as MyApp
        item.isEnabled = false
        val add = arrayOf("CDLU/sem1/2014/", "CDLU/sem1/2015/", "CDLU/sem1/2016/", "CDLU/sem1/2017/")

        when (item.itemId) {
            R.id.download     //December 2014
            -> {
                when (m.getit1()) {
                    0 -> AppUtils.startDownload("Algebra (Dec 14).pdf",
                            add[0] + "Algebra%20(Dec%2014).pdf", this)
                    1 -> AppUtils.startDownload("Calculus (Dec 14).pdf",
                            add[0] + "Calculus%20(Dec%2014).pdf", this)
                    2 -> AppUtils.startDownload("Solid Geometry (Dec 14).pdf",
                            add[0] + "Solid%20Geometry%20(Dec%2014).pdf", this)
                    3 -> AppUtils.startDownload("Discrete Mathematics-I (Dec 14).pdf",
                            add[0] + "Discrete%20Mathematics-I%20(Dec%2014).pdf", this)
                    4 -> AppUtils.startDownload("Descriptive Statistics (Dec 14).pdf",
                            add[0] + "Descriptive%20Statistics%20(Dec%2014).pdf", this)
                    5 -> AppUtils.startDownload("Computer Fundamentals (Dec 14).pdf",
                            add[0] + "Computer%20Fundamentals%20(Dec%2014).pdf", this)
                    6 -> AppUtils.startDownload("Introduction To Window Softwares (Dec 14).pdf",
                            add[0] + "Introduction%20To%20Window%20Softwares%20(Dec%2014).pdf", this)
                    7 -> AppUtils.startDownload("English-I (Dec 14).pdf",
                            add[0] + "English-I%20(Dec%2014).pdf", this)
                    8 -> AppUtils.startDownload("Complete Sem 1 (Dec 14).pdf",
                            add[0] + "Complete%20Sem%201%20(Dec%2014).pdf", this)
                }
                return true
            }
            R.id.download2//December 2015
            -> {
                when (m.getit1()) {
                    0 -> AppUtils.startDownload("Algebra (Dec 15).pdf",
                            add[1] + "Al%28Dec15%29.pdf", this)
                    1 -> AppUtils.startDownload("Calculus (Dec 15).pdf",
                            add[1] + "Ca%28Dec15%29.pdf", this)
                    2 -> AppUtils.startDownload("Solid Geometry (Dec 15).pdf",
                            add[1] + "SG%28Dec15%29.pdf", this)
                    3 -> AppUtils.startDownload("Discrete Mathematics-I (Dec 15).pdf",
                            add[1] + "DM1%28Dec15%29.pdf", this)
                    4 -> AppUtils.startDownload("Descriptive Statistics (Dec 15).pdf",
                            add[1] + "DS%28Dec15%29.pdf", this)
                    5 -> AppUtils.startDownload("Computer Fundamentals (Dec 15).pdf",
                            add[1] + "CF%28Dec15%29.pdf", this)
                    6 -> AppUtils.startDownload("Introduction To Window Softwares (Dec 15).pdf",
                            add[1] + "ItWS%28Dec15%29.pdf", this)
                    7 -> AppUtils.startDownload("English-I (Dec 15).pdf",
                            add[1] + "E%28Dec15%29.pdf", this)
                    8 -> AppUtils.startDownload("Complete Sem 1 (Dec 15).pdf",
                            add[1] + "Complete%20Sem%201%20%28Dec15%29.pdf", this)
                }
                return true
            }

            R.id.download3 -> {
                when (m.getit1()) {
                    0 -> AppUtils.startDownload("Algebra (Dec 16).pdf",
                            add[2] + "A.pdf", this)
                    1 -> AppUtils.startDownload("Calculus (Dec 16).pdf",
                            add[2] + "C.pdf", this)
                    2 -> AppUtils.startDownload("Solid Geometry (Dec 16).pdf",
                            add[2] + "SG.pdf", this)
                    3 -> AppUtils.startDownload("Discrete Mathematics-I (Dec 16).pdf",
                            add[2] + "DM.pdf", this)
                    4 -> AppUtils.startDownload("Descriptive Statistics (Dec 16).pdf",
                            add[2] + "DS.pdf", this)
                    5 -> AppUtils.startDownload("Computer Fundamentals (Dec 16).pdf",
                            add[2] + "CF.pdf", this)
                    6 -> AppUtils.startDownload("Introduction To Window Softwares (Dec 16).pdf",
                            add[2] + "IWS.pdf", this)
                    7 -> AppUtils.startDownload("English-I (Dec 16).pdf",
                            add[2] + "E.pdf", this)
                    8 -> AppUtils.startDownload("Complete Sem 1 (Dec 16).pdf",
                            add[2] + "ALL.pdf", this)
                }
                return true
            }

            R.id.dec17 -> {
                when (m.getit1()) {
                    0 -> AppUtils.startDownload("Algebra (Dec 17).pdf",
                            add[3] + "A.pdf", this)
                    1 -> AppUtils.startDownload("Calculus (Dec 17).pdf",
                            add[3] + "C.pdf", this)
                    2 -> AppUtils.startDownload("Solid Geometry (Dec 17).pdf",
                            add[3] + "SG.pdf", this)
                    3 -> AppUtils.startDownload("Discrete Mathematics-I (Dec 17).pdf",
                            add[3] + "DM.pdf", this)
                    4 -> AppUtils.startDownload("Descriptive Statistics (Dec 17).pdf",
                            add[3] + "DS.pdf", this)
                    5 -> AppUtils.startDownload("Computer Fundamentals (Dec 17).pdf",
                            add[3] + "CF.pdf", this)
                    6 -> AppUtils.startDownload("Introduction To Window Softwares (Dec 17).pdf",
                            add[3] + "ItWS.pdf", this)
                    7 -> AppUtils.startDownload("English-I (Dec 17).pdf",
                            add[3] + "E.pdf", this)
                    8 -> AppUtils.startDownload("Complete Sem 1 (Dec 17).pdf",
                            add[3] + "ALL.pdf", this)
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

    companion object {

        val MESSAGE_PROGRESS = "message_progress"
        private val PERMISSION_REQUEST_CODE = 1
    }
}
