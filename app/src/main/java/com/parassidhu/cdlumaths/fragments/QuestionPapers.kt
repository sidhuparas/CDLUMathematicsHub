package com.parassidhu.cdlumaths.fragments

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.google.firebase.FirebaseApp
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.parassidhu.cdlumaths.R
import com.parassidhu.cdlumaths.activities.*
import com.parassidhu.cdlumaths.adapters.HomeAdapter
import com.parassidhu.cdlumaths.utils.AppUtils
import com.parassidhu.cdlumaths.utils.ItemClickSupport
import kotlinx.android.synthetic.main.course_list.*
import kotlinx.android.synthetic.main.fragment_question_papers.*

class QuestionPapers : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_question_papers, container, false)
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        setListener5Years()
        setListener2Years()
        fetchDeveloperMessage()
    }

    private fun initViews() {
        app_promo.setOnClickListener {
            AppUtils.openWebPage((activity as AppCompatActivity?),
                    "https://play.google.com/store/apps/details?id=com.parassidhu.pdfpin")
        }

        five_years_rv.setHasFixedSize(true)
        five_years_rv.layoutManager = GridLayoutManager(activity?.applicationContext, 4)
        val adapter = HomeAdapter(activity, AppUtils.prepareDataFor5Years())
        five_years_rv.adapter = adapter
        AppUtils.setFastScrolling(five_years_rv)


        two_years_rv.setHasFixedSize(true)
        two_years_rv.layoutManager = GridLayoutManager(activity?.applicationContext, 4)

        val adapter2Years = HomeAdapter(activity, AppUtils.prepareDataFor2Years())
        two_years_rv.adapter = adapter2Years

        //Theme for Choose
        choose_2Years.setTextColor(Color.rgb(Home.r, Home.g, Home.b))

        //Theme for Choose
        choose_5Years.setTextColor(Color.rgb(Home.r, Home.g, Home.b))

        activity?.title = "Question Papers"
        setHasOptionsMenu(true)
    }

    private fun fetchDeveloperMessage(){
        FirebaseApp.initializeApp(activity)
        val remoteConfig = FirebaseRemoteConfig.getInstance()

        val defaultValues = mapOf(
                "dev_msg" to "Thank you for using CDLU Mathematics Hub. I am happy to serve you."
        )

        remoteConfig.setDefaults(defaultValues)

        remoteConfig.fetch().addOnCompleteListener((activity as AppCompatActivity)) { task ->
            if (task.isSuccessful){
                remoteConfig.activateFetched()
                dev_msg.text = remoteConfig.getString("dev_msg")
            }
        }
    }

    private fun setListener5Years() {
        ItemClickSupport.addTo(five_years_rv).setOnItemClickListener { recyclerView, position, v ->
            when (position) {
                0 -> {
                    val i = Intent(activity, sem1::class.java)
                    activity?.startActivity(i)
                }
                1 -> {
                    val a = Intent(activity, sem2::class.java)
                    activity?.startActivity(a)
                }
                2 -> {
                    val b = Intent(activity, sem3::class.java)
                    activity?.startActivity(b)
                }
                3 -> {
                    val c = Intent(activity, sem4::class.java)
                    activity?.startActivity(c)
                }
                4 -> {
                    val d = Intent(activity, sem5::class.java)
                    activity?.startActivity(d)
                }
                5 -> {
                    val e = Intent(activity, sem6::class.java)
                    activity?.startActivity(e)
                }
                6 -> {
                    val f = Intent(activity, sem7::class.java)
                    activity?.startActivity(f)
                }
                7 -> {
                    val g = Intent(activity, sem8::class.java)
                    activity?.startActivity(g)
                }
                8 -> {
                    val h = Intent(activity, sem9::class.java)
                    activity?.startActivity(h)
                }
                9 -> {
                    val o = Intent(activity, sem10::class.java)
                    activity?.startActivity(o)
                }
            }
        }
    }

    private fun setListener2Years() {
        ItemClickSupport.addTo(two_years_rv).setOnItemClickListener { recyclerView, position, v ->
            when (position) {
                0 -> {
                    val i = Intent(activity, tsem1::class.java)
                    startActivity(i)
                }
                1 -> {
                    val a = Intent(activity, tsem2::class.java)
                    startActivity(a)
                }
                2 -> {
                    val b = Intent(activity, tsem3::class.java)
                    startActivity(b)
                }
                3 -> {
                    val c = Intent(activity, tsem4::class.java)
                    startActivity(c)
                }
            }
        }
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        val item = menu.findItem(R.id.sort)
        AppUtils.setOptVisibility(menu, false, true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        return super.onOptionsItemSelected(item)
    }
}