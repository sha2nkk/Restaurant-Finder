package com.shashankk.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LocationVM(val screen : ILocationVM) : ViewModel() {

    val location = MutableLiveData<Pair<Double,Double>>()

    val fetchCurrentLocation =  {
        screen.startFetchingLocation()
    }

    fun onNewLocation(latitude : Double, longitude: Double) {
        location.value = Pair(latitude, longitude)
    }
}

interface ILocationVM {
    fun startFetchingLocation()

}