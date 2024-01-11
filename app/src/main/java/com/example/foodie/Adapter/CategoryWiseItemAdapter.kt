package com.example.foodie.Adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodie.ModelClass.PopularMeal
import com.example.foodie.databinding.ItemViewCategoryBinding
import com.example.foodie.databinding.PopularItemsBinding

class CategoryWiseItemAdapter(): RecyclerView.Adapter<CategoryWiseItemAdapter.CategoryWiseItemViewHolder>() {
    var _itemList= ArrayList<PopularMeal>()
    lateinit var  onItemClick:((PopularMeal)->Unit)
    class CategoryWiseItemViewHolder(val binding: ItemViewCategoryBinding):RecyclerView.ViewHolder(binding.root)

    fun setCategoryWiseItem(itemList: ArrayList<PopularMeal>){
        this._itemList=itemList
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryWiseItemViewHolder {
        return CategoryWiseItemViewHolder(ItemViewCategoryBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
        return _itemList.size
    }

    override fun onBindViewHolder(holder: CategoryWiseItemViewHolder, position: Int) {
         Glide.with(holder.itemView)
             .load(_itemList[position].strMealThumb)
             .into(holder.binding.categoryItemImageView)
        holder.binding.itemNameTextView.text=_itemList[position].strMeal
        holder.itemView.setOnClickListener{
            onItemClick.invoke(_itemList[position])
        }
    }
}