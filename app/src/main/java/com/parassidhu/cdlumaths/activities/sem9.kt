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
import com.parassidhu.cdlumaths.utils.AppUtils
import com.parassidhu.cdlumaths.utils.ItemClickSupport
import com.parassidhu.cdlumaths.utils.MyApp

class sem9 : AppCompatActivity() {

    private val subject_names = arrayOf(
            "Topology",
            "Fluid Mechanics",
            "Integral Equations",
            "Mathematical Statistics",
            "Advanced Mechanics Of Solids"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sem9)
        setupView()
        initViews()

        AppUtils.renderTheme(this)
        val adView = this.findViewById<AdView>(R.id.adView)
        AppUtils.displayAds(this, adView)

        val rcl = findViewById<RecyclerView>(R.id.card_recycler_view)
        ItemClickSupport.addTo(rcl).setOnItemClickListener { recyclerView, position, v ->
            try {
                val m = applicationContext as MyApp
                m.getClickSem9(position)
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
        inflater.inflate(R.menu.sem9, menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.dec18    //December 2018
            -> {
                downloadPaper("8")
                return true
            }
            R.id.may19 -> {
                downloadExclusiveMayPaper("9")
                return true
            }
            else -> return super.onContextItemSelected(item)
        }
    }

    private fun downloadPaper(year: String) {
        val m = applicationContext as MyApp
        val add = "CDLU/sem9/201$year/"
        val yr = "$year).pdf"
        when (m.getit9()) {
            0 -> AppUtils.startDownload("Topology (Dec 1$yr",
                    add + "T.pdf", this)
            1 -> AppUtils.startDownload("Fluid Mechanics (Dec 1$yr",
                    add + "FM.pdf", this)
            2 -> AppUtils.startDownload("Integral Equations (Dec 1$yr",
                    add + "IE.pdf", this)
            3 -> AppUtils.startDownload("Mathematical Statistics (Dec 1$yr",
                    add + "MS.pdf", this)
            4 -> AppUtils.startDownload("Advanced Mechanics Of Solids (Dec 1$yr",
                    add + "AMoS.pdf", this)
        }
    }

    private fun downloadExclusiveMayPaper(year: String) {
        val m = applicationContext as MyApp
        val add = "CDLU/sem9/201${year}M/"
        val yr = "$year).pdf"
        when (m.getit9()) {
            0 -> AppUtils.startDownload("Topology (May 1$yr",
                    add + "T.pdf", this)
            1 -> AppUtils.startDownload("Fluid Mechanics (May 1$yr",
                    add + "FM.pdf", this)
            2 -> AppUtils.startDownload("Integral Equations (May 1$yr",
                    add + "IE.pdf", this)
            3 -> AppUtils.startDownload("Mathematical Statistics (May 1$yr",
                    add + "MS.pdf", this)
            4 -> AppUtils.startDownload("Advanced Mechanics Of Solids (May 1$yr",
                    add + "AMoS.pdf", this)
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
