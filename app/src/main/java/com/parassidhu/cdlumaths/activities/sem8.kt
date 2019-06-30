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
import com.parassidhu.cdlumaths.utils.ItemClickSupport
import com.parassidhu.cdlumaths.utils.MyApp
import com.parassidhu.cdlumaths.utils.AppUtils

class sem8 : AppCompatActivity() {

    private val subject_names = arrayOf(
            "Advanced Abstract Algebra",
            "Measure and Integration Theory",
            "Mechanics of Solids",
            "System Of Differential Equations",
            "Computer Programming in Fortran 90 & 95",
            "Methods of Applied Mathematics"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sem8)
        setupView()
        initViews()

        AppUtils.renderTheme(this)
        val adView = this.findViewById<AdView>(R.id.adView)
        AppUtils.displayAds(this, adView)

        val rcl = findViewById<RecyclerView>(R.id.card_recycler_view)
        ItemClickSupport.addTo(rcl).setOnItemClickListener { recyclerView, position, v ->
            try {
                val m = applicationContext as MyApp
                m.getClickSem8(position)
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
        inflater.inflate(R.menu.sem8, menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.download -> {
                downloadPaper("8")
                true
            }
            R.id.may19 -> {
                downloadPaper("9")
                true
            }
            else -> super.onContextItemSelected(item)
        }
    }

    private fun downloadPaper(year: String) {
        val m = applicationContext as MyApp
        val URL = "CDLU/sem8/201$year/"

        when (m.getit8()) {
            0 -> AppUtils.startDownload("Advanced Abstract Algebra (May 1$year).pdf",
                    URL + "AAA.pdf", this)
            1 -> AppUtils.startDownload("Measure and Integration Theory (May 1$year).pdf",
                    URL + "MaIT.pdf", this)
            2 -> AppUtils.startDownload("Mechanics of Solids (May 1$year).pdf",
                    URL + "MoS.pdf", this)
            3 -> AppUtils.startDownload("System of Differential Equations (May 1$year).pdf",
                    URL + "SoDE.pdf", this)
            4 -> AppUtils.startDownload("Computer Programming in FORTRAN 90 & 95 (May 1$year).pdf",
                    URL + "CPiF.pdf", this)
            5 -> AppUtils.startDownload("Methods of Applied Mathematics (May 1$year).pdf",
                    URL + "MoAM.pdf", this)
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
