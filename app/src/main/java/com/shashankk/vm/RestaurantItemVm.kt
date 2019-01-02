package com.shashankk.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.shashankk.model.Restaurant
import com.shashankk.model.Venue

/**
 * Created by Shashank Kumar on 02/01/19.
 */
class RestaurantItemVm(val restaurant: Venue, val screen: IRestaurantVM) : ViewModel() {

    val isLiked = MutableLiveData<Boolean>().apply { value = false }

    val isDisLiked = MutableLiveData<Boolean>().apply { value = !(isLiked.value ?: false) && false }

    val name = restaurant.name

    val address = restaurant.location.address

    val rating = String.format("%.2f", restaurant.rating)

    val ratingColor = "#${restaurant.ratingColor}"

    val onRestaurantClick = {
        screen.onRestaurantClick(restaurant.id)
    }

    val onLikeClick = {
        screen.onLikeClick(restaurant.id)
    }

    val onDisLikeClick = {
        screen.onDisLikeClick(restaurant.id)
    }

    val onCallClick = {
        restaurant.contact?.phone?.let {
            screen.onCallClick(it)
        }
    }

    val onDirectionClick = {
        restaurant.location.let {
            screen.routeToMaps(it.lat, it.lng)
        }
    }
}

interface IRestaurantVM {
    fun onRestaurantClick(id: String)
    fun onLikeClick(id: String)
    fun onDisLikeClick(id: String)
    fun onCallClick(phoneNumber: String)
    fun routeToMaps(latitude: Double, longitude: Double)
}