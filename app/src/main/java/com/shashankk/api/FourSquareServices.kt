package com.shashankk.api

import com.shashankk.model.Restaurant
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface FourSquareServices {

    @GET("https://api.foursquare.com/v2/venues/explore?ll={latitude},{longitude}")
    fun getRestaurants(@Query("latitude") latitude : Double, @Query("longitude") longitude : Double) : Observable<List<Restaurant>>

    @GET("https://api.foursquare.com/v2/venues/{VENUE_ID}")
    fun getRestaurant(@Query("VENUE_ID") venue_id : String) : Observable<Restaurant>
}