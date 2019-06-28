package com.parassidhu.cdlumaths.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View

import com.google.android.gms.ads.AdView
import com.parassidhu.cdlumaths.R
import com.parassidhu.cdlumaths.adapters.QueAdapter
import com.parassidhu.cdlumaths.utils.AppUtils
import com.parassidhu.cdlumaths.utils.ItemClickSupport
import com.parassidhu.cdlumaths.utils.MyApp

class sem10 : AppCompatActivity() {

    private val subject_names = arrayOf(
            "Functional Analysis",
            "Partial Differential Equations",
            "Mechanics of Solids-II",
            "Boundary Value Problem",
            "Mathematical Aspect of Seismology"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sem10)
        setupView()
        initViews()

        AppUtils.renderTheme(this)
        val adView = this.findViewById<AdView>(R.id.adView)
        AppUtils.displayAds(this, adView)
        val rcl = findViewById<RecyclerView>(R.id.card_recycler_view)
        ItemClickSupport.addTo(rcl).setOnItemClickListener { recyclerView, position, v ->
            try {
                val m = applicationContext as MyApp
                m.getClickSem10(position)
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
        inflater.inflate(R.menu.sem10, menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val m = applicationContext as MyApp
        val add = arrayOf(
                "CDLU/sem10/2016/",
                "CDLU/sem10/2017/",
                "CDLU/sem10/2019/"
        )
        when (item.itemId) {
            R.id.download -> {
                when (m.getit10()) {
                    0 -> AppUtils.startDownload("Functional Analysis (May 16).pdf",
                            add[0] + "FA%28May16%29.pdf", this)
                    1 -> AppUtils.startDownload("Partial Differential Equations-10 (May 16).pdf",
                            add[0] + "PDE%28May16%29.pdf", this)
                    2 -> AppUtils.startDownload("Mechanics of Solids-II (May 16).pdf",
                            add[0] + "MoS%28May16%29.pdf", this)
                    3 -> AppUtils.startDownload("Boundary Value Problem (May 16).pdf",
                            add[0] + "BVP%28May16%29.pdf", this)
                    4 -> AppUtils.startDownload("Mathematical Aspect of Seismology (May 16).pdf",
                            add[0] + "MaOS%28May16%29.pdf", this)
                }
                return true
            }
            R.id.may17 -> {
                downloadPaper(add[1], "7")
                return true
            }
            R.id.may19 -> {
                downloadPaper(add[2], "9")
                return true
            }
            else -> return super.onContextItemSelected(item)
        }
    }

    private fun downloadPaper(URL: String, year: String) {
        val m = applicationContext as MyApp
        when (m.getit10()) {
            0 -> AppUtils.startDownload("Functional Analysis (May 1$year).pdf",
                    URL + "FA.pdf", this)
            1 -> AppUtils.startDownload("Partial Differential Equations-10 (May 1$year).pdf",
                    URL + "PDE.pdf", this)
            2 -> AppUtils.startDownload("Mechanics of Solids-II (May 1$year).pdf",
                    URL + "MoS.pdf", this)
            3 -> AppUtils.startDownload("Boundary Value Problem (May 1$year).pdf",
                    URL + "BVP.pdf", this)
            4 -> AppUtils.startDownload("Mathematical Aspect of Seismology (May 1$year).pdf",
                    URL + "MaOS.pdf", this)
        }
    }

    fun setupView() {
        val acb = supportActionBar
        acb!!.setHomeButtonEnabled(true)
        acb.setDisplayHomeAsUpEnabled(true)
        acb.title = "Choose Subject"
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