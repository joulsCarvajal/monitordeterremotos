package com.alphazetakapp.earthquakemonitor

import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET


interface EqApiService {
    @GET("all_hour.geojson")
    suspend fun getLastHourEarthQuake(): EqJsonResponse
}

private var retrofit = Retrofit.Builder()
    .baseUrl("https://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/")
    .addConverterFactory(MoshiConverterFactory.create())
    .build()

var service: EqApiService = retrofit.create(EqApiService::class.java)