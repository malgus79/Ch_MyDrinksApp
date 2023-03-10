package com.mydrinksapp.accessdata

import com.google.gson.Gson
import com.mydrinksapp.model.data.CategoriesList
import com.mydrinksapp.model.data.CocktailByCategoryList
import com.mydrinksapp.model.data.CocktailList
import com.mydrinksapp.model.data.IngredientList
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