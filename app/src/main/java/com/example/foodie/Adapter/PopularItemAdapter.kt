package com.example.foodie.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodie.ModelClass.PopularMeal
import com.example.foodie.databinding.PopularItemsBinding

class PopularItemAdapter ():RecyclerView.Adapter<PopularItemAdapter.PopularItemViewHolder>() {
    lateinit var  onItemClick:((PopularMeal)->Unit)
    private var mealList=ArrayList<PopularMeal>()
    fun setPopularMeal(_mealList:ArrayList<PopularMeal>){
        this.mealList=_mealList
        notifyDataSetChanged()
    }

    class PopularItemViewHolder(val binding:PopularItemsBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularItemViewHolder {
        return PopularItemViewHolder(PopularItemsBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
        return mealList.size
    }

    override fun onBindViewHolder(holder: PopularItemViewHolder, position: Int) {
        Glide.with(holder.itemView)
            .load(mealList[position].strMealThumb)
            .into(holder.binding.popularCardItem)
        holder.itemView.setOnClickListener{
            onItemClick.invoke(mealList[position])
        }
    }
}