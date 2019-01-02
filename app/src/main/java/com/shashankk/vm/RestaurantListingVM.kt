package com.shashankk.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.shashankk.base.ViewProvider
import com.shashankk.model.Restaurant
import com.shashankk.model.Venue

/**
 * Created by Shashank Kumar on 02/01/19.
 */
class RestaurantListingVM(val screen: IRestaurantListingVm) : ViewModel() {

    val loaderVisibility = MutableLiveData<Boolean>().apply { value = true }
    val errorVisibility = MutableLiveData<Boolean>().apply { value = false }
    val contentVisibility = MutableLiveData<Boolean>().apply { value = false }

    val restaurantVMs = MutableLiveData<List<RestaurantItemVm>>()

    fun onData(items : List<Venue>) {
        restaurantVMs.value = (restaurantVMs.value?:ArrayList()).let { list ->
            (list as ArrayList).addAll(items.map { RestaurantItemVm(it, screen) })
            screen.bindData(list)
            list
        }
        loaderVisibility.value = false
        errorVisibility.value = false
        contentVisibility.value = true
    }

    fun onError() {

    }

    fun onComponentLoad() {
        //start fetching Data
    }

}

interface IRestaurantListingVm : IRestaurantVM {
    fun bindData(items : List<RestaurantItemVm>)
}