package com.shashankk.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.shashankk.model.Venue

/**
 * Created by Shashank Kumar on 02/01/19.
 */
class HatedRestaurantListingVM(val screen: IHatedRestaurantListingVm) : RestaurantListingVM(screen) {

    override fun onLikeClick(venue: Venue) {
        super.onLikeClick(venue)
        restaurantVMs.value?.indexOfFirst { it.restaurant == venue }?.let {
            if(it <0) return
            restaurantVMs.value?.removeAt(it)
            screen.removeItemAtIndex(it)
        }
    }
    override fun onDisLikeClick(venue: Venue) {
        super.onLikeClick(venue)
        restaurantVMs.value?.map { it.restaurant }?.indexOf(venue)?.let {
            if(it <0) return
            restaurantVMs.value?.removeAt(it)
            screen.removeItemAtIndex(it)
        }
    }
}

interface IHatedRestaurantListingVm : IRestaurantListingVm {

    fun removeItemAtIndex(Index: Int)
}