package com.shashankk.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.shashankk.model.Venue

/**
 * Created by Shashank Kumar on 02/01/19.
 */
class RestaurantItemVm(val restaurant: Venue, val screen: IRestaurantVM) : ViewModel() {

    val isLiked = MutableLiveData<Boolean>().apply { value = restaurant.state > 0 }

    val isDisLiked = MutableLiveData<Boolean>().apply { value = restaurant.state < 0 }

    val name = restaurant.name

    val address = restaurant.location?.address

    val rating = String.format("%.2f", restaurant.rating)

    val ratingColor = "#${restaurant.ratingColor}"

    val photoUrl = "${restaurant.bestPhoto?.prefix}300${restaurant.bestPhoto?.suffix}"

    val onRestaurantClick = {
        screen.onRestaurantClick(restaurant.id)
    }

    val onLikeClick = {
        if(isLiked.value != true) {
            setState(Venue.STATE_LIKE)
        } else {
            setState(Venue.STATE_NEUTRAL)
        }
        screen.onLikeClick(restaurant)
    }

    val onDisLikeClick = {
        if(isDisLiked.value != true) {
            setState(Venue.STATE_DISLIKE)
        } else {
            setState(Venue.STATE_NEUTRAL)
        }
        screen.onDisLikeClick(restaurant)
    }

    val onCallClick = {
        screen.onCallClick(restaurant.contact?.phone)
    }

    val onDirectionClick = {
        restaurant.location?.let {
            screen.routeToMaps(it.lat, it.lng)
        }
    }

    private fun setState(state : Int) {
        restaurant.state = state
        when (state) {
            Venue.STATE_NEUTRAL -> {
                isLiked.value = false
                isDisLiked.value = false
            }
            Venue.STATE_LIKE -> {
                isLiked.value = true
                isDisLiked.value = false
            }
            Venue.STATE_DISLIKE -> {
                isLiked.value = false
                isDisLiked.value = true
            }
        }
    }
}

interface IRestaurantVM {
    fun onRestaurantClick(id: String)
    fun onLikeClick(venue: Venue)
    fun onDisLikeClick(venue: Venue)
    fun onCallClick(phoneNumber: String?)
    fun routeToMaps(latitude: Double, longitude: Double)
}