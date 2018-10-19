package com.bigfriedicecream.overcooked.observables

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager

import com.bigfriedicecream.overcooked.BuildConfig
import com.bigfriedicecream.overcooked.models.RecipeModel
import com.koushikdutta.ion.Ion

import java.util.Observable
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken


class RecipeRepository private constructor() : Observable() {

    private object Holder {
        val INSTANCE = RecipeRepository()
    }

    companion object {
        val instance:RecipeRepository by lazy { Holder.INSTANCE }
        var model:String = ""
    }

    fun load(context:Context) {
        val sharedPreferences:SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        val overcookedDb:String = sharedPreferences.getString("overcooked_database", "")

        if (overcookedDb != "") {
            model = overcookedDb
            setChanged()
            notifyObservers()

        } else {
            Ion
                    .with(context)
                    .load(BuildConfig.DATABASE_URL)
                    .asString()
                    .setCallback { _, result ->
                        val editor:SharedPreferences.Editor = sharedPreferences.edit()
                        editor.putString("overcooked_database", result)
                        editor.apply()

                        model = result
                        setChanged()
                        notifyObservers()
                    }
        }
    }

    fun getRecipeList():HashMap<String, RecipeModel> {
        val overcooked:JsonObject = Gson().fromJson(model, JsonObject::class.java)
        val recipesJson:JsonObject = overcooked.getAsJsonObject("recipes")

        val listType = object : TypeToken<HashMap<String, RecipeModel>>() { }.type
        val recipeList = GsonBuilder().create().fromJson<HashMap<String, RecipeModel>>(recipesJson, listType)

        for ((_, recipe) in recipeList) {
            recipe.imageURL = "${BuildConfig.BASE_URL}/recipes%2F${recipe.id}%2Fhero.jpg?alt=media"
        }

        return recipeList
    }

    fun getRecipeById(id:String?):RecipeModel {
        return RecipeModel()
    }
}