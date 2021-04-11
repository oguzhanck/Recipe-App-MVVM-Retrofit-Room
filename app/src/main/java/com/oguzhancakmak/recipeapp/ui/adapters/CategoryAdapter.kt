package com.oguzhancakmak.recipeapp.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.oguzhancakmak.recipeapp.R
import com.oguzhancakmak.recipeapp.data.models.CategoryEntity
import com.squareup.picasso.Picasso

class CategoryAdapter(
    private val categoryList: List<CategoryEntity>,
    private val onClickListenerCategory: OnClickListenerCategory
) : RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {

    interface OnClickListenerCategory {
        fun onItemClick(categoryName: String)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.row_design_category, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return categoryList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.setOnClickListener {
            onClickListenerCategory.onItemClick(categoryList[position].strCategory)
        }
        return holder.bind(categoryList[position])
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private var categoryImage = itemView.findViewById<ImageView>(R.id.category_image)
        private var categoryName = itemView.findViewById<TextView>(R.id.category_name)
        fun bind(categoryBind: CategoryEntity) {

            categoryName.text = categoryBind.strCategory
            Picasso.get().load(categoryBind.strCategoryThumb).into(categoryImage)
        }

    }
}
















