package com.oguzhancakmak.recipeapp.ui.activities

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.oguzhancakmak.recipeapp.R
import com.oguzhancakmak.recipeapp.data.local.FavoriteViewModel
import com.oguzhancakmak.recipeapp.data.models.FavoriteFoodEntity
import com.oguzhancakmak.recipeapp.ui.adapters.FavoriteAdapter
import kotlinx.android.synthetic.main.activity_favorites.*

class FavoritesActivity : AppCompatActivity() {

    private lateinit var favoriteViewModel: FavoriteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorites)
        favoriteViewModel = ViewModelProvider(this)[FavoriteViewModel::class.java]
        supportActionBar?.hide()

        bottomNavBar()
        recycleAdapter()

    }

    // For go to the home page when pressed the back button
    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this@FavoritesActivity, HomePageActivity::class.java)
        startActivity(intent)
        overridePendingTransition(0, 0)
    }

    // Clicking functions under the adapter, respectively, works for:
    // onItemClick - > to go to the food details
    // onItemClickFavorite -> to add to the favorites
    // onItemClickFavoriteIsSelected -> for is favorite star should be checked or not be checked
    private fun recycleAdapter() {
        favoriteFoodList_recycleView.apply {
            setHasFixedSize(true)
            layoutManager = GridLayoutManager(this@FavoritesActivity, 2)

            favoriteViewModel.allFavoriteFoodDetail.observe(
                this@FavoritesActivity,
                { favoriteFoodDetail ->

                    adapter = FavoriteAdapter(
                        favoriteFoodDetail, object : FavoriteAdapter.OnClickListenerFoodList {

                            override fun onItemClick(
                                foodID: String,
                                mealName: String,
                                mealThumb: String
                            ) {
                                val intent =
                                    Intent(this@FavoritesActivity, FoodDetailActivity::class.java)
                                val bundle = Bundle()
                                bundle.putString("FoodID", foodID)
                                bundle.putString("mealName", mealName)
                                bundle.putString("mealThumb", mealThumb)
                                intent.putExtras(bundle)
                                startActivity(intent)
                            }

                        },
                        object : FavoriteAdapter.OnClickListenerFoodListFavorite {
                            override fun onItemClickFavorite(
                                favoriteFoodEntity: FavoriteFoodEntity,
                                view: ImageButton
                            ) {

                                if (view.isSelected) {
                                    //DELETE ON FAVORITES
                                    view.isSelected = false
                                    view.setImageResource(R.drawable.ic_baseline_star_empty_24)

                                    favoriteViewModel.deleteFavoriteFoodDetail(favoriteFoodEntity)

                                    Toast.makeText(
                                        view.context,
                                        "Removed to Favorites",
                                        Toast.LENGTH_SHORT
                                    ).show()

                                } else {
                                    //ADD TO FAVORITE
                                    view.isSelected = true
                                    view.setImageResource(R.drawable.ic_baseline_star_full_24)

                                    favoriteViewModel.insertFavoriteFoodDetail(favoriteFoodEntity)

                                    Toast.makeText(
                                        view.context,
                                        "Added to Favorites",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }


                            }

                            override fun onItemClickFavoriteIsSelected(
                                favoriteFoodEntity: FavoriteFoodEntity,
                                view: ImageButton
                            ) {

                                val allFoodDetails: LiveData<List<FavoriteFoodEntity>> =
                                    favoriteViewModel.allFavoriteFoodDetail
                                allFoodDetails.observe(this@FavoritesActivity, { it ->

                                    for (foodDetail in it) {
                                        if (foodDetail.mealID == favoriteFoodEntity.mealID && foodDetail.strMeal == favoriteFoodEntity.strMeal) {
                                            view.setImageResource(R.drawable.ic_baseline_star_full_24)
                                            view.isSelected = true
                                        }
                                    }
                                })
                            }

                        })


                })
        }
    }

    // BOTTOM NAVIGATION BAR //
    private fun bottomNavBar() {
        bottom_navigation_bar.selectedItemId = R.id.navigation_favorites
        bottom_navigation_bar.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.navigation_homePage -> {
                    val intent = Intent(this@FavoritesActivity, HomePageActivity::class.java)
                    startActivity(intent)
                    overridePendingTransition(0, 0)
                    return@setOnNavigationItemSelectedListener true
                }

                R.id.navigation_favorites -> {
                    return@setOnNavigationItemSelectedListener true
                }
                else -> {
                    return@setOnNavigationItemSelectedListener false
                }
            }
        }
    }
}