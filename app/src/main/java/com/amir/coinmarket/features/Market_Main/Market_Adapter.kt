package com.amir.coinmarket.features.Market_Main

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.amir.coinmarket.R
import com.amir.coinmarket.apiManager.BASE_URL_IMAGE
import com.amir.coinmarket.apiManager.coin_top_json
import com.amir.coinmarket.databinding.ItemRecyclerMarketBinding
import com.bumptech.glide.Glide

class Market_Adapter(
    private val data: ArrayList<coin_top_json.Data>,
    val recyclerCallback: Recycler_callback,
) :
    RecyclerView.Adapter<Market_Adapter.market_viewHolder>() {

    lateinit var binding: ItemRecyclerMarketBinding

    inner class market_viewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        @SuppressLint("SetTextI18n")
        fun bind_views(coins_data: coin_top_json.Data) {

            binding.textViewBtc.text = coins_data.coinInfo.fullName
            //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            binding.textViewPrice.text = "$" + coins_data.rAW.uSD.pRICE.toString().substring(0..9)

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

            val market =coins_data.rAW.uSD.mKTCAP / 1000000000
            val mark = market.toString().indexOf(".")

            binding.textviewMarketCap.text = "$" + market.toString().substring( 0 , mark + 4) + " B"

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

            if (coins_data.rAW.uSD.cHANGEPCT24HOUR > 0) {

                 binding.textviewTaghir.setTextColor(ContextCompat.getColor(itemView.context, R.color.colorGain))
                binding.textviewTaghir.text = coins_data.rAW.uSD.cHANGEPCT24HOUR.toString().substring(0 .. 4)+ "%"

            } else if (coins_data.rAW.uSD.cHANGEPCT24HOUR < 0) {

                binding.textviewTaghir.setTextColor(ContextCompat.getColor(itemView.context, R.color.colorLoss))
                binding.textviewTaghir.text = coins_data.rAW.uSD.cHANGEPCT24HOUR.toString().substring(0..5) + "%"
            }
            else {
                binding.textviewTaghir.text = "0%"
            }
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            Glide.with(itemView)
                .load(BASE_URL_IMAGE + coins_data.coinInfo.imageUrl)
                .into(binding.imageViewRecycler)

            itemView.setOnClickListener {

                recyclerCallback.OnCoinItemClick(coins_data)
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): market_viewHolder {

        binding =
            ItemRecyclerMarketBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return market_viewHolder(binding.root)

    }

    override fun getItemCount(): Int {
        return data.size

    }
    override fun onBindViewHolder(holder: market_viewHolder, position: Int) {

        holder.bind_views(data[position])
    }

    interface Recycler_callback {

        fun OnCoinItemClick(coins_data: coin_top_json.Data)
    }
}