package com.amir.coinmarket.features.Coin_main

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.amir.coinmarket.R
import com.amir.coinmarket.apiManager.ALL
import com.amir.coinmarket.apiManager.ApiService
import com.amir.coinmarket.apiManager.BASE_URL
import com.amir.coinmarket.apiManager.BASE_URL_TWITTER
import com.amir.coinmarket.apiManager.HISTO_DAY
import com.amir.coinmarket.apiManager.HISTO_HOUR
import com.amir.coinmarket.apiManager.HISTO_MINUTE
import com.amir.coinmarket.apiManager.HOUR
import com.amir.coinmarket.apiManager.HOURS24
import com.amir.coinmarket.apiManager.MONTH
import com.amir.coinmarket.apiManager.MONTH3
import com.amir.coinmarket.apiManager.WEEK
import com.amir.coinmarket.apiManager.YEAR
import com.amir.coinmarket.apiManager.coin_top_json
import com.amir.coinmarket.databinding.ActivityCoinBinding
import ir.dunijet.dunipool.apiManager.model.chart_data
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Suppress("UNREACHABLE_CODE")
class CoinActivity : AppCompatActivity() {
    lateinit var binding: ActivityCoinBinding

    lateinit var data_coin: coin_top_json.Data
    lateinit var DataThisCoinAbout: Coin_About_Item


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCoinBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        try {
            val fromIntent = intent.getBundleExtra("bundel")

            if (fromIntent != null) {
                data_coin = fromIntent.getParcelable("bundel2") ?: coin_top_json.Data(
                    coinInfo = TODO(),
                    dISPLAY = TODO(),
                    rAW = TODO()
                )
                DataThisCoinAbout = fromIntent.getParcelable("bundel1") ?: Coin_About_Item()
            } else {
                throw NullPointerException("Bundle is null")
            }

        } catch (e: Exception) {
            Toast.makeText(this, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
            Log.e("CoinActivity", "Error loading intent data", e)

            // مقداردهی پیش‌فرض برای جلوگیری از کرش
            data_coin = coin_top_json.Data(
                coinInfo = TODO(),
                dISPLAY = TODO(),
                rAW = TODO()
            )
            DataThisCoinAbout = Coin_About_Item()
        }

        binding.layoutToolbar.toolbarMain.title = data_coin.coinInfo.fullName

        initUI()


    }

    @SuppressLint("SetTextI18n")
    private fun initUI() {


        var period: String = HOUR
        init_layout_chart(data_coin.coinInfo.name, period)


        binding.layoutChart.radioGroupChart.setOnCheckedChangeListener { _, checkedId ->

            when (checkedId) {


                R.id.radio_12h -> period = HOUR
                R.id.radio_1d -> period = HOURS24
                R.id.radio_1w -> period = WEEK
                R.id.radio_1m -> period = MONTH
                R.id.radio_3m -> period = MONTH3
                R.id.radio_1y -> period = YEAR
                R.id.radio_all -> period = ALL

            }

            init_layout_chart(data_coin.coinInfo.name, period)
        }
        binding.layoutChart.textViewPriceChart.text = data_coin.dISPLAY.uSD.pRICE
        binding.layoutChart.textViewChange2.text =  data_coin.rAW.uSD.cHANGEPCT24HOUR.toString().substring(0 .. 3)+ "%"
        binding.layoutChart.textViewChange1.text = data_coin.dISPLAY.uSD.cHANGE24HOUR


        if (data_coin.rAW.uSD.cHANGEPCT24HOUR > 0) {
            binding.layoutChart.textViewChange2.setTextColor( ContextCompat.getColor(this ,  R.color.colorGain))
            binding.layoutChart.upDownChart.text = "⟰"
            binding.layoutChart.upDownChart.setTextColor(ContextCompat.getColor(this , R.color.colorGain))
            binding.layoutChart.sparkViewChart.lineColor = ContextCompat.getColor(this , R.color.colorGain)


        } else if (data_coin.rAW.uSD.cHANGEPCT24HOUR < 0) {
            binding.layoutChart.textViewChange2.setTextColor(ContextCompat.getColor(this , R.color.colorLoss))
            binding.layoutChart.upDownChart.text = "⟱"
            binding.layoutChart.upDownChart.setTextColor(ContextCompat.getColor(this , R.color.colorLoss))
            binding.layoutChart.sparkViewChart.lineColor = ContextCompat.getColor(this , R.color.colorLoss)

        }



        init_layout_statistics()
        init_layout_about()


    }

    @SuppressLint("SetTextI18n")
    private fun init_layout_about() {

        binding.layoutAbout.txtWebsite.text = DataThisCoinAbout.coin_Website
        binding.layoutAbout.txtReddit.text = DataThisCoinAbout.coin_Readit
        binding.layoutAbout.txtTwitter.text = DataThisCoinAbout.coin_Twiiter
        binding.layoutAbout.txtGithub.text = DataThisCoinAbout.coin_Github
        binding.layoutAbout.txtAboutCoin.text = DataThisCoinAbout.coin_Desc

        binding.layoutAbout.txtWebsite.setOnClickListener {

            if (DataThisCoinAbout.coin_Website != "No Data" && DataThisCoinAbout.coin_Website != "" && DataThisCoinAbout.coin_Website != null) {

                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(DataThisCoinAbout.coin_Website))
                startActivity(intent)
            }
        }
        binding.layoutAbout.txtReddit.setOnClickListener {

            if (DataThisCoinAbout.coin_Readit != "No Data" && DataThisCoinAbout.coin_Readit != "" && DataThisCoinAbout.coin_Readit != null) {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(DataThisCoinAbout.coin_Readit))
                startActivity(intent)
            }
        }
        binding.layoutAbout.txtTwitter.setOnClickListener {

            if (DataThisCoinAbout.coin_Twiiter != "No Data" && DataThisCoinAbout.coin_Twiiter != "" && DataThisCoinAbout.coin_Twiiter != null) {
                val intent = Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse(BASE_URL_TWITTER + DataThisCoinAbout.coin_Twiiter)
                )
                startActivity(intent)
            }
        }
        binding.layoutAbout.txtGithub.setOnClickListener {

            if (DataThisCoinAbout.coin_Github != "No Data" && DataThisCoinAbout.coin_Github != "" && DataThisCoinAbout.coin_Github != null) {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(DataThisCoinAbout.coin_Github))
                startActivity(intent)
            }

        }
    }

    private fun init_layout_statistics() {

        binding.layoutStatistics.tvOpen.text = data_coin.dISPLAY.uSD.oPEN24HOUR
        binding.layoutStatistics.tvTodaysHigh.text = data_coin.dISPLAY.uSD.hIGH24HOUR
        binding.layoutStatistics.tvTodayslow.text = data_coin.dISPLAY.uSD.lOW24HOUR
        binding.layoutStatistics.tvChangeTodays.text = data_coin.dISPLAY.uSD.cHANGE24HOUR
        binding.layoutStatistics.tvAlgorithm.text = data_coin.coinInfo.algorithm
        binding.layoutStatistics.tvTotalVolume.text = data_coin.dISPLAY.uSD.tOTALVOLUME24H
        binding.layoutStatistics.tvMarketCap.text = data_coin.dISPLAY.uSD.mKTCAP
        binding.layoutStatistics.tvSupply.text = data_coin.dISPLAY.uSD.sUPPLY
    }

    @SuppressLint("SetTextI18n")
    private fun init_layout_chart(fsym: String, period: String) {


        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val data = retrofit.create(ApiService::class.java)


        var histoperiod = ""
        var limit = 30
        var aggregate = 1

        when (period) {

            HOUR -> {
                histoperiod = HISTO_MINUTE
                limit = 60
                aggregate = 12
            }

            HOURS24 -> {
                histoperiod = HISTO_HOUR
                limit = 24

            }

            WEEK -> {
                histoperiod = HISTO_HOUR
                aggregate = 6

            }

            MONTH -> {
                histoperiod = HISTO_DAY
                limit = 30
            }

            MONTH3 -> {
                histoperiod = HISTO_DAY
                limit = 90
            }

            YEAR -> {
                histoperiod = HISTO_DAY
                aggregate = 13
            }

            ALL -> {
                histoperiod = HISTO_DAY
                aggregate = 30
                limit = 2000
            }


        }


        val chart = data.get_Chart_Data(histoperiod, fsym, limit, aggregate)

        chart.enqueue(object : Callback<chart_data> {
            override fun onResponse(call: Call<chart_data>, response: Response<chart_data>) {
                val chart_data_full = response.body()!!

                val data1 = chart_data_full.data

                val data2 = chart_data_full.data.maxByOrNull {
                    it.close.toFloat()
                }

                val chartadapter = Chart_Adapter(data1, data2?.open.toString())

                binding.layoutChart.sparkViewChart.adapter = chartadapter


            }

            override fun onFailure(call: Call<chart_data>, t: Throwable) {

                Toast.makeText(this@CoinActivity, t.message, Toast.LENGTH_SHORT).show()
                Log.v("amir", t.message.toString())

            }
        })


        binding.layoutChart.sparkViewChart.setScrubListener {

            if (it == null){
                binding.layoutChart.textViewPriceChart.text = data_coin.dISPLAY.uSD.pRICE

            }else{
                binding.layoutChart.textViewPriceChart.text = "$ "+ (it as chart_data.Data ).close.toString()


            }

        }

    }

}