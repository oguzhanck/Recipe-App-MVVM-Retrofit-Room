package com.oguzhancakmak.recipeapp.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.oguzhancakmak.recipeapp.R
import com.oguzhancakmak.recipeapp.data.models.CountryEntity

class CountryAdapter(
    private val countryList: List<CountryEntity>,
    private val onClickListenerCountry: OnClickListenerCountry
) : RecyclerView.Adapter<CountryAdapter.ViewHolder>() {

    interface OnClickListenerCountry {
        fun onItemClick(countryName: String)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.row_design_country, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return countryList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.countryButton.setOnClickListener {
            onClickListenerCountry.onItemClick(countryList[position].strArea)
        }
        return holder.bind(countryList[position])
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var countryButton: Button = itemView.findViewById(R.id.button_country)
        fun bind(countryBind: CountryEntity) {
            countryButton.text = countryBind.strArea
        }
    }

}