package com.mydrinksapp.accessdata

import com.google.gson.Gson
import com.mydrinksapp.data.model.CategoriesList
import com.mydrinksapp.data.model.CocktailByCategoryList
import com.mydrinksapp.data.model.CocktailList
import com.mydrinksapp.data.model.IngredientList
import java.io.InputStreamReader

class JSONFileLoader {
    private var jsonStr: String? = null

    fun loadJSONString(file: String): String? {
        val loader = InputStreamReader(this.javaClass.classLoader?.getResourceAsStream(file))
        jsonStr = loader.readText()
        loader.close()
        return jsonStr
    }

    fun loadCocktailByCategory(file: String): CocktailByCategoryList? {
        val loader = InputStreamReader(this.javaClass.classLoader?.getResourceAsStream(file))
        jsonStr = loader.readText()
        loader.close()
        return Gson().fromJson(jsonStr, CocktailByCategoryList::class.java)
    }

    fun loadCocktailList(file: String): CategoriesList? {
        val loader = InputStreamReader(this.javaClass.classLoader?.getResourceAsStream(file))
        jsonStr = loader.readText()
        loader.close()
        return Gson().fromJson(jsonStr, CategoriesList::class.java)
    }

    fun loadCocktail(file: String): CocktailList? {
        val loader = InputStreamReader(this.javaClass.classLoader?.getResourceAsStream(file))
        jsonStr = loader.readText()
        loader.close()
        return Gson().fromJson(jsonStr, CocktailList::class.java)
    }

    fun loadIngredient(file: String): IngredientList? {
        val loader = InputStreamReader(this.javaClass.classLoader?.getResourceAsStream(file))
        jsonStr = loader.readText()
        loader.close()
        return Gson().fromJson(jsonStr, IngredientList::class.java)
    }
}