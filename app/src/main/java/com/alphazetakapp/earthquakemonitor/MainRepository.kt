package com.alphazetakapp.earthquakemonitor

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MainRepository {

    suspend fun fetchEarthQuakes(): MutableList<Earthquake> {
        return withContext(Dispatchers.IO){
            val eqJsonResponse = service.getLastHourEarthQuake()
            val eqList = parseEqResult(eqJsonResponse)
            eqList
        }
    }

    private fun parseEqResult(eqJsonResponse: EqJsonResponse): MutableList<Earthquake> {
        val eqList = mutableListOf<Earthquake>()
        val featuresList = eqJsonResponse.features

        for(feature in featuresList){
            val properties = feature.properties

            val id = feature.id
            val magnitude = properties.mag
            val place = properties.place
            val time = properties.time

            val geometry = feature.geometry
            val longitude = geometry.longitude
            val latitude = geometry.latitude

            eqList.add(Earthquake(id, place, magnitude, time, longitude, latitude))
        }
        return eqList
    }
}