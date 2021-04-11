package com.oguzhancakmak.recipeapp.ui.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.oguzhancakmak.recipeapp.R
import com.oguzhancakmak.recipeapp.data.local.FavoriteViewModel
import com.oguzhancakmak.recipeapp.data.models.*
import com.oguzhancakmak.recipeapp.ui.adapters.FoodListAdapter
import com.oguzhancakmak.recipeapp.viewmodels.FoodListViewModel
import kotlinx.android.synthetic.main.activity_food_detail.*
import kotlinx.android.synthetic.main.activity_food_list.*
import kotlinx.android.synthetic.main.activity_homepage.*
import kotlinx.android.synthetic.main.row_design_food_list.view.*

class FoodListActivity : AppCompatActivity() {

    private lateinit var foodListViewModel: FoodListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_food_list)
        supportActionBar?.hide()

        val countryName: String? = intent.getStringExtra("countryName")
        val categoryName: String? = intent.getStringExtra("categoryName")

        // CHECKING WHERE WE CAME FROM, CATEGORY OR COUNTRY //
        if (countryName != null) {
            fetchFoodList(path1 = countryName)
            actionbar_text_foodList.text = countryName
        } else if (categoryName != null) {
            fetchFoodList(path2 = categoryName)
            actionbar_text_foodList.text = categoryName
        }
    }

    // FETCH FOODS FROM INTERNET BY RETROFIT //
    // Clicking functions under the adapter, respectively, works for:
    // onItemClick - > to go to the food details
    // onItemClickFavorite -> to add to the favorites
    // onItemClickFavoriteIsSelected -> for checking is favorite star should be checked or not checked
    private fun fetchFoodList(path1: String? = null, path2: String? = null) {
        foodListViewModel = ViewModelProvider(this)[FoodListViewModel::class.java]
        val favoriteViewModel = ViewModelProvider(this)[FavoriteViewModel::class.java]

        foodListViewModel.fetchNewFoodListData(path1 = path1, path2 = path2)
            .observe(this, Observer<FoodList> { foodListInfo ->

                val foodList: List<FoodListEntity> = foodListInfo.meals

                foodList_recycleView.apply {
                    isNestedScrollingEnabled = false
                    setHasFixedSize(true)
                    layoutManager = GridLayoutManager(this@FoodListActivity, 2)

                    adapter =
                        FoodListAdapter(foodList, object : FoodListAdapter.OnClickListenerFoodList {

                            override fun onItemClick(
                                foodID: String,
                                mealName: String,
                                mealThumb: String
                            ) {
                                val intent =
                                    Intent(this@FoodListActivity, FoodDetailActivity::class.java)
                                val bundle = Bundle()
                                bundle.putString("FoodID", foodID)
                                bundle.putString("mealName", mealName)
                                bundle.putString("mealThumb", mealThumb)
                                intent.putExtras(bundle)
                                startActivity(intent)
                            }

                        }, object : FoodListAdapter.OnClickListenerFoodListFavorite {

                            override fun onItemClickFavorite(favoriteFoodEntity: FavoriteFoodEntity, view: ImageButton) {
                                if (view.isSelected) {
                                    //DELETE ON FAVORITES
                                    view.isSelected = false
                                    view.setImageResource(R.drawable.ic_baseline_star_empty_24)

                                    favoriteViewModel.deleteFavoriteFoodDetail(favoriteFoodEntity)

                                    Toast.makeText(view.context, "Removed to Favorites", Toast.LENGTH_SHORT).show()

                                } else {
                                    //ADD TO FAVORITE
                                    view.isSelected = true
                                    view.setImageResource(R.drawable.ic_baseline_star_full_24)

                                    favoriteViewModel.insertFavoriteFoodDetail(favoriteFoodEntity)

                                    Toast.makeText(view.context, "Added to Favorites", Toast.LENGTH_SHORT).show()
                                }
                            }

                            override fun onItemClickFavoriteIsSelected(favoriteFoodEntity: FavoriteFoodEntity, view: ImageButton) {
                                val allFoodDetails: LiveData<List<FavoriteFoodEntity>> = favoriteViewModel.allFavoriteFoodDetail
                                allFoodDetails.observe(this@FoodListActivity, { it ->

                                    for (foodDetail in it) {
                                        if (foodDetail.mealID == favoriteFoodEntity.mealID && foodDetail.strMeal == favoriteFoodEntity.strMeal) {
                                            view.setImageResource(R.drawable.ic_baseline_star_full_24)
                                            view.isSelected = true
                                        }
                                    }
                                })
                            }
                        })

                    progress_foodList.visibility = View.GONE
                    foodList_recycleView.visibility = View.VISIBLE
                }

            })
    }

}