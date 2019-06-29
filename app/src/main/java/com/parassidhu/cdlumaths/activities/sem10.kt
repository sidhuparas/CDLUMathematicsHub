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
            "Mathematical Aspect of Seismology",
            "Operations Research",
            "Boundary Value Problems"
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

    override fun onCreateContextMenu(menu: ContextMenu?, v: View?,
                                     menuInfo: ContextMenu.ContextMenuInfo?) {
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
        return when (item.itemId) {
            R.id.may19 -> {
                downloadPaper(add[2], "9")
                true
            }
            else -> super.onContextItemSelected(item)
        }
    }

    private fun downloadPaper(URL: String, year: String) {
        val m = applicationContext as MyApp
        when (m.getit10()) {
            0 -> AppUtils.startDownload("Functional Analysis (May 1$year).pdf",
                    URL + "FA.pdf", this)
            1 -> AppUtils.startDownload("Partial Differential Equations-10 (May 1$year).pdf",
                    URL + "PDE.pdf", this)
            2 -> AppUtils.startDownload("Mathematical Aspect of Seismology (May 1$year).pdf",
                    URL + "MaOS.pdf", this)
            3 -> AppUtils.startDownload("Operations Research (May 1$year).pdf",
                    URL + "OR.pdf", this)
            4 -> AppUtils.startDownload("Boundary Value Problems (May 1$year).pdf",
                    URL + "BVP.pdf", this)
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