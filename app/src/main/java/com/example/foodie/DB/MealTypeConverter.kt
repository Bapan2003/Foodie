package com.example.foodie.DB

import androidx.room.TypeConverter
import androidx.room.TypeConverters
import java.util.jar.Attributes

@TypeConverters
class MealTypeConverter {
    @TypeConverter
    fun anyToString(attributes: Any):String{
        if(attributes==null)
            return ""
        return attributes.toString()
    }
    @TypeConverter
    fun stringToAny(attributes: String):Any{
        if(attributes==null)
            return ""
        return attributes as  Any
    }
}