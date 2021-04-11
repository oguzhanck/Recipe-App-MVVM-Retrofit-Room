package com.oguzhancakmak.recipeapp.ui.activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.oguzhancakmak.recipeapp.R
import com.oguzhancakmak.recipeapp.data.local.FavoriteViewModel
import com.oguzhancakmak.recipeapp.data.models.FavoriteFoodEntity
import com.oguzhancakmak.recipeapp.data.models.FoodDetail
import com.oguzhancakmak.recipeapp.viewmodels.FoodDetailViewModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_food_detail.*

class FoodDetailActivity : AppCompatActivity() {

    private lateinit var foodDetailViewModel: FoodDetailViewModel
    lateinit var favoriteViewModel: FavoriteViewModel
    private var youtubeLink = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_food_detail)
        supportActionBar?.hide()

        val foodID: String? = intent.getStringExtra("FoodID")
        val mealName: String? = intent.getStringExtra("mealName")
        val mealThumb: String? = intent.getStringExtra("mealThumb")
        fetchFoodDetail(foodID!!)

        val favoriteFoodEntity = FavoriteFoodEntity(foodID, mealName!!, mealThumb!!)

        favoriteViewModel = ViewModelProvider(this)[FavoriteViewModel::class.java]

        favoriteButtonOnClick(favoriteFoodEntity, favoriteViewModel)
        isFavoriteButtonChecked(favoriteFoodEntity, favoriteViewModel)
        youtubeButtonOnClick()
    }

    // FETCH FOOD DETAILS FROM INTERNET //
    private fun fetchFoodDetail(foodID: String) {

        foodDetailViewModel = ViewModelProvider(this)[FoodDetailViewModel::class.java]
        foodDetailViewModel.fetchNewFoodDetailData(foodID).observe(this, Observer<FoodDetail> { foodListInfo ->

            Picasso.get().load(foodListInfo.meals[0].strMealThumb).into(imgItem)
            detail_food_name.text = foodListInfo.meals[0].strMeal
            detail_country_name.text = foodListInfo.meals[0].strArea
            detail_category_name.text = foodListInfo.meals[0].strCategory
            tvInstructions.text = foodListInfo.meals[0].strInstructions

            if (foodListInfo.meals[0].strYoutube != "") {
                youtubeLink = foodListInfo.meals[0].strYoutube
            } else {
                button_detail_youtube.visibility = View.GONE
            }

            progress_foodDetail.visibility = View.GONE
        })

    }

    private fun youtubeButtonOnClick() {
        button_detail_youtube.setOnClickListener {
            val uri = Uri.parse(youtubeLink)
            val intent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)
        }
    }

    private fun favoriteButtonOnClick(favoriteFoodEntity: FavoriteFoodEntity, mViewModel: FavoriteViewModel) {
        button_detail_add_favorite.setOnClickListener {

            if (button_detail_add_favorite.isSelected) {
                //DELETE ON FAVORITES
                button_detail_add_favorite.isSelected = false
                button_detail_add_favorite.setImageResource(R.drawable.ic_baseline_star_empty_24)

                mViewModel.deleteFavoriteFoodDetail(favoriteFoodEntity)

                Toast.makeText(this, "Removed to Favorites", Toast.LENGTH_SHORT).show()

            } else {
                //ADD TO FAVORITE
                button_detail_add_favorite.isSelected = true
                button_detail_add_favorite.setImageResource(R.drawable.ic_baseline_star_full_24)

                mViewModel.insertFavoriteFoodDetail(favoriteFoodEntity)

                Toast.makeText(this, "Added to Favorites", Toast.LENGTH_SHORT).show()
            }

        }
    }

    private fun isFavoriteButtonChecked(favoriteFoodEntity: FavoriteFoodEntity, mViewModel: FavoriteViewModel) {
        val allFoodDetails: LiveData<List<FavoriteFoodEntity>> = mViewModel.allFavoriteFoodDetail

        allFoodDetails.observe(this, { it ->

            for (foodDetail in it) {
                if (foodDetail.mealID == favoriteFoodEntity.mealID && foodDetail.strMeal == favoriteFoodEntity.strMeal) {
                    button_detail_add_favorite.setImageResource(R.drawable.ic_baseline_star_full_24)
                    button_detail_add_favorite.isSelected = true
                }
            }
        })
    }

}