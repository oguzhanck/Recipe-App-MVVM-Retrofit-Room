package com.oguzhancakmak.recipeapp.ui.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.oguzhancakmak.recipeapp.R
import com.oguzhancakmak.recipeapp.data.models.Category
import com.oguzhancakmak.recipeapp.data.models.CategoryEntity
import com.oguzhancakmak.recipeapp.data.models.Country
import com.oguzhancakmak.recipeapp.data.models.CountryEntity
import com.oguzhancakmak.recipeapp.ui.adapters.CategoryAdapter
import com.oguzhancakmak.recipeapp.ui.adapters.CountryAdapter
import com.oguzhancakmak.recipeapp.viewmodels.CategoryViewModel
import com.oguzhancakmak.recipeapp.viewmodels.CountryViewModel
import kotlinx.android.synthetic.main.activity_homepage.*


class HomePageActivity : AppCompatActivity() {

    private lateinit var categoryViewModel: CategoryViewModel
    private lateinit var countryViewModel: CountryViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_homepage)


        fetchCategory()
        fetchCountry()
        bottomNavBar()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        this.finishAffinity()
    }

    // FETCH CATEGORIES FROM INTERNET BY RETROFIT //
    private fun fetchCategory() {
        categoryViewModel = ViewModelProvider(this)[CategoryViewModel::class.java]

        categoryViewModel.fetchNewCategoryData().observe(this, Observer<Category> { categoryInfo ->

            val categoryList: List<CategoryEntity> = categoryInfo.categories
            categories_recycleview.apply {
                isNestedScrollingEnabled = false
                setHasFixedSize(true)
                layoutManager = GridLayoutManager(this@HomePageActivity, 3)
                adapter = CategoryAdapter(categoryList, object : CategoryAdapter.OnClickListenerCategory {

                    override fun onItemClick(categoryName: String) {
                        val intent = Intent(this@HomePageActivity, FoodListActivity::class.java)
                        val bundle = Bundle()
                        bundle.putString("categoryName", categoryName)
                        intent.putExtras(bundle)
                        startActivity(intent)
                    }
                })
                progress_home_page.visibility = View.GONE
                categories_recycleview.visibility = View.VISIBLE

            }
        })
    }

    // FETCH COUNTRY LIST FROM INTERNET //
    private fun fetchCountry() {
        countryViewModel = ViewModelProvider(this)[CountryViewModel::class.java]
        countryViewModel.fetchNewCountryData().observe(this, Observer<Country> { countryInfo ->

            val countryList: List<CountryEntity> = countryInfo.meals
            country_recyclerview.apply {
                setHasFixedSize(true)
                layoutManager = LinearLayoutManager(this@HomePageActivity, LinearLayoutManager.HORIZONTAL, false)
                adapter = CountryAdapter(countryList, object : CountryAdapter.OnClickListenerCountry {

                    override fun onItemClick(countryName: String) {
                        val intent = Intent(this@HomePageActivity, FoodListActivity::class.java)
                        val bundle = Bundle()
                        bundle.putString("countryName", countryName) //Your id
                        intent.putExtras(bundle)
                        startActivity(intent)
                    }
                })
                country_recyclerview.visibility = View.VISIBLE
            }
        })
    }

    // BOTTOM NAVIGATION BAR //
    private fun bottomNavBar() {
        bottom_navigation_bar.selectedItemId = R.id.navigation_homePage
        bottom_navigation_bar.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.navigation_homePage -> {
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.navigation_favorites -> {
                    val intent = Intent(this@HomePageActivity, FavoritesActivity::class.java)
                    startActivity(intent)
                    overridePendingTransition(0, 0)
                    return@setOnNavigationItemSelectedListener true
                }
                else -> {
                    return@setOnNavigationItemSelectedListener false
                }
            }
        }
    }

}