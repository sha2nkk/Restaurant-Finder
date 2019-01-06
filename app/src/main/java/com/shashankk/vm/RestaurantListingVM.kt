package com.shashankk.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.shashankk.model.Venue

/**
 * Created by Shashank Kumar on 02/01/19.
 */
open class RestaurantListingVM(private val screen: IRestaurantListingVm) : ViewModel(), IRestaurantVM {

    val loaderVisibility = MutableLiveData<Boolean>().apply { value = true }
    val errorVisibility = MutableLiveData<Boolean>().apply { value = false }
    val contentVisibility = MutableLiveData<Boolean>().apply { value = false }

    var isLoadingMore = false
    var isLastPage = false

    var pageNo = 0

    val pageSize = 10

    protected val restaurantVMs = MutableLiveData<ArrayList<RestaurantItemVm>>()

    fun onData(items: List<Venue>) {
        isLoadingMore = false
        screen.setListLoader(false)
        if (pageSize > items.size) {
            isLastPage = true
        }
        restaurantVMs.value = (restaurantVMs.value ?: ArrayList()).let { list ->
            (list as ArrayList).addAll(items.map { RestaurantItemVm(it, this) })
            screen.bindData(list)
            list
        }
        loaderVisibility.value = false
        errorVisibility.value = false
        contentVisibility.value = true
    }

    fun getItemAtIndex(venue:Venue) {}

    fun onError() {
        loaderVisibility.value = false
        if (restaurantVMs.value?.size == 0) {
            errorVisibility.value = true
            contentVisibility.value = false
        } else {
            errorVisibility.value = false
            contentVisibility.value = true
        }
    }

    fun onComponentLoad() {
        pageNo = 0
        restaurantVMs.value = ArrayList()
        loadMoreItems()
    }


    fun shouldLoadMoreItems() = !isLoadingMore && !isLastPage

    fun loadMoreItems() {
        isLoadingMore = true
        screen.setListLoader(true)
        screen.getDataForPage(pageNo++)
    }

    override fun onRestaurantClick(id: String) {
        screen.onRestaurantClick(id)
    }

    override fun onLikeClick(venue: Venue) {
        screen.onLikeClick(venue)
    }

    override fun onDisLikeClick(venue: Venue) {
        screen.onDisLikeClick(venue)
    }

    override fun onCallClick(phoneNumber: String?) {
        screen.onCallClick(phoneNumber)
    }

    override fun routeToMaps(latitude: Double, longitude: Double) {
        screen.routeToMaps(latitude,longitude)
    }
}

interface IRestaurantListingVm : IRestaurantVM {

    fun getDataForPage(page: Int)

    fun bindData(items: List<RestaurantItemVm>)

    fun setListLoader(isVisible: Boolean)
}