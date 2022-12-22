package com.mydrinksapp.domain

import com.mydrinksapp.data.DataSource
import com.mydrinksapp.data.model.Drink
import com.mydrinksapp.vo.Resource

class RepoImpl(private val datasource: DataSource) : Repo {

    override suspend fun getTragosList(tragoName: String): Resource<List<Drink>> {
        return datasource.getTragoByName(tragoName)
    }
}