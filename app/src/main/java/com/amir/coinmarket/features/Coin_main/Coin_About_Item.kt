package com.amir.coinmarket.features.Coin_main

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Coin_About_Item (

    var coin_Website : String? =  "No Data",
    var coin_Github :String? =  "No Data",
    var coin_Twiiter : String? =  "No Data",
    var coin_Desc :String? =  "No Data",
    var coin_Readit : String? =  "No Data",

):Parcelable