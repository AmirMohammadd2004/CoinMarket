package com.amir.coinmarket.features.Market_Main

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.amir.coinmarket.R
import com.amir.coinmarket.apiManager.ApiService
import com.amir.coinmarket.apiManager.BASE_URL
import com.amir.coinmarket.apiManager.about_coins
import com.amir.coinmarket.apiManager.coin_top_json
import com.amir.coinmarket.apiManager.news_top_json
import com.amir.coinmarket.databinding.ActivityMarketBinding
import com.amir.coinmarket.features.Coin_main.CoinActivity
import com.amir.coinmarket.features.Coin_main.Coin_About_Item
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory



class MarketActivity : AppCompatActivity(), Market_Adapter.Recycler_callback {
    lateinit var binding: ActivityMarketBinding
    lateinit var about_data_map : MutableMap <String, Coin_About_Item>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMarketBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        open_Ui()

        binding.layoutRecycler.btnMore.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.livecoinwatch.com/"))
            startActivity(intent)
        }

    }

    private fun open_Ui() {

        ui_Market_news()
        ui_Market_coins()
        getAboutDataFromAssets()


    }


    private fun ui_Market_news() {

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val api = retrofit.create(ApiService::class.java)
        val news = api.get_news_top()
        news.enqueue(object : Callback<news_top_json> {
            override fun onResponse(call: Call<news_top_json>, response: Response<news_top_json>) {

                val data = response.body()!!


                val data_news: ArrayList<Pair<String, String>> = arrayListOf()
                data.data.forEach {
                    data_news.add(Pair(it.url, it.title))
                }

                fun random_news() {

                    val random = (1..49).random()
                    binding.layoutNews.textNews.text = data_news[random].second
                    binding.layoutNews.imageNews.setOnClickListener {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(data_news[random].first))
                        startActivity(intent)
                    }
                }

                random_news()
                binding.layoutNews.textNews.setOnClickListener {
                    random_news()
                }




                binding.swipeRefreshMarket.setOnRefreshListener {
                    random_news()
                    ui_Market_coins()


                    Handler(Looper.getMainLooper()).postDelayed({

                        binding.swipeRefreshMarket.isRefreshing = false

                    }, 1500)

                }

            }

            override fun onFailure(call: Call<news_top_json>, t: Throwable) {
                Toast.makeText(this@MarketActivity, t.message, Toast.LENGTH_SHORT).show()
            }

        })
    }


    private fun ui_Market_coins() {

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()


        val api = retrofit.create(ApiService::class.java)
        val coins_top = api.get_coin_top()
        coins_top.enqueue(object : Callback<coin_top_json> {
            override fun onResponse(call: Call<coin_top_json>, response: Response<coin_top_json>) {

                val body = response.body()!!
                var amir: ArrayList<coin_top_json.Data> = ArrayList(body.data)
                ShowDataInRecycler(amir)


            }

            override fun onFailure(call: Call<coin_top_json>, t: Throwable) {

                Toast.makeText(this@MarketActivity, "error  " + t.message, Toast.LENGTH_SHORT)
                    .show()

                Log.v("test1", t.message!!)

            }

        })


    }

    private fun ShowDataInRecycler(data: ArrayList<coin_top_json.Data>) {

        val marketAdapter = Market_Adapter(ArrayList(data), this)
        binding.layoutRecycler.recyclerViewWatchList.adapter = marketAdapter
        binding.layoutRecycler.recyclerViewWatchList.layoutManager = LinearLayoutManager(this)


    }

    override fun OnCoinItemClick(coins_data: coin_top_json.Data) {


        val intent = Intent(this, CoinActivity::class.java)

        val bundle = Bundle()
        bundle.putParcelable( "bundel1" , about_data_map[coins_data.coinInfo.name] )
        bundle.putParcelable( "bundel2" ,coins_data  )

        intent.putExtra("bundel",bundle )
        startActivity(intent)

    }


    private fun getAboutDataFromAssets() {

        val file_in_String = applicationContext.assets
            .open("currencyinfo.json")
            .bufferedReader()
            .use { it.readText() }

        about_data_map = mutableMapOf < String, Coin_About_Item> ()

        val gson = Gson()
        val all_data = gson.fromJson(file_in_String, about_coins::class.java)


        all_data.forEach {

            about_data_map[ it.currencyName ] = Coin_About_Item(

                it.info.web,
                it.info.github,
                it.info.twt,
                it.info.desc,
                it.info.reddit
            )
        }
    }

}