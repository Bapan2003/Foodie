package com.example.foodie.DB

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.foodie.ModelClass.Meal

@Database(entities = [Meal::class], version = 1, exportSchema = false)
@TypeConverters(MealTypeConverter::class)
abstract class MealDatabase():RoomDatabase() {
     abstract fun mealDao():MealDAO

     companion object{
         private var INSTANCE:MealDatabase ? = null
         fun getInstance(context:Context):MealDatabase{
             if(INSTANCE==null){
                 @Synchronized
                 INSTANCE=Room.databaseBuilder(
                     context.applicationContext,
                     MealDatabase::class.java,
                     "MealDB"
                 ).build()
             }
             return INSTANCE!!
         }
     }



}