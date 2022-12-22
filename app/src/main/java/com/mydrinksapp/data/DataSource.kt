package com.mydrinksapp.data

import com.mydrinksapp.data.model.Drink
import com.mydrinksapp.vo.Resource

class DataSource {

    val genetateTragosList = listOf<Drink>(
        Drink("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTbIRRnJQa2w6KsMZJCb6L6dwZNHiXv4Qajweg4V6zKhhOy_d_zF0knnAi_iZSM9KMkSHU&usqp=CAU", "Margarita", "Con azucar, vodka y nueces"),
        Drink("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTEOmZmPf1iVmxG-NLTRf7QFWzN9W6M52BjKA&usqp=CAU", "Fernet", "Fernet con coca y 2 hielos"),
        Drink("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRvAi06y7OYoi0Kuu38MTirxx91SFdAGyagKA&usqp=CAU", "Toro", "Toro con pritty"),
        Drink("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcT5RX_paurNbo2GBay_t6TEPayK4Qtdq2_Jdg&usqp=CAU", "Gancia", "Gancia con sprite"),
    )

    fun getTragosList(): Resource<List<Drink>> {
        return Resource.Success(genetateTragosList)
    }
}