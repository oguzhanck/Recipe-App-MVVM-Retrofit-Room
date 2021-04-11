package com.oguzhancakmak.recipeapp.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.oguzhancakmak.recipeapp.R
import com.oguzhancakmak.recipeapp.data.models.FavoriteFoodEntity
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.row_design_food_list.view.*

class FavoriteAdapter(
    private val favoriteDetailList: List<FavoriteFoodEntity>, private val onClickListenerFoodList: OnClickListenerFoodList,
    private val onClickListenerFoodListFavorite: OnClickListenerFoodListFavorite
) : RecyclerView.Adapter<FavoriteAdapter.ViewHolder>() {

    // For the go food details
    interface OnClickListenerFoodList {
        fun onItemClick(foodID: String, mealName: String, mealThumb: String)
    }

    // For the favorite operations
    interface OnClickListenerFoodListFavorite {
        fun onItemClickFavorite(favoriteFoodEntity: FavoriteFoodEntity, view: ImageButton)
        fun onItemClickFavoriteIsSelected(favoriteFoodEntity: FavoriteFoodEntity, view: ImageButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.row_design_food_list, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return favoriteDetailList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.setOnClickListener {
            onClickListenerFoodList.onItemClick(
                favoriteDetailList[position].mealID,
                favoriteDetailList[position].strMeal,
                favoriteDetailList[position].strMealThumb
            )
        }

        val favoriteFoodEntityList =
            FavoriteFoodEntity(favoriteDetailList[position].mealID, favoriteDetailList[position].strMeal, favoriteDetailList[position].strMealThumb)
        holder.itemView.rv_favorite_button.setOnClickListener {
            onClickListenerFoodListFavorite.onItemClickFavorite(favoriteFoodEntityList, holder.itemView.rv_favorite_button)
        }
        onClickListenerFoodListFavorite.onItemClickFavoriteIsSelected(favoriteFoodEntityList, holder.itemView.rv_favorite_button)

        return holder.bind(favoriteDetailList[position])
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private var foodListImage = itemView.findViewById<ImageView>(R.id.foodList_image)
        private var foodListName = itemView.findViewById<TextView>(R.id.foodList_name)
        fun bind(foodListBind: FavoriteFoodEntity) {
            foodListName.text = foodListBind.strMeal
            Picasso.get().load(foodListBind.strMealThumb).into(foodListImage);
        }
    }
}