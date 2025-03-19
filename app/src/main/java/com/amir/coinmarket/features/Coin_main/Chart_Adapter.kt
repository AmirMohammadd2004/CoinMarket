package com.amir.coinmarket.features.Coin_main

import com.robinhood.spark.SparkAdapter
import ir.dunijet.dunipool.apiManager.model.chart_data

class Chart_Adapter(

    private val hostorycalldata: List<chart_data.Data>,
    private val baseLine : String?


    ) :SparkAdapter() {
    override fun getCount(): Int {
       return hostorycalldata.size
    }

    override fun getItem(index: Int): chart_data.Data {
        return hostorycalldata[index]
    }

    override fun getY(index: Int): Float {
      return  hostorycalldata[index].close.toFloat()
    }

    override fun hasBaseLine(): Boolean {
     return   true
    }

    override fun getBaseLine(): Float {
        return  baseLine?.toFloat() ?:super.getBaseLine()
    }


}