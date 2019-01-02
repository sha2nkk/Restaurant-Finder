package com.shashankk.restaurant_finder

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.shashankk.api.volley.ListingApiCall
import com.shashankk.base.RecyclerViewAdapter
import com.shashankk.base.ViewProvider
import com.shashankk.model.Restaurant
import com.shashankk.model.Venue
import com.shashankk.model.VenueListingResponse
import com.shashankk.restaurant_finder.databinding.RestaurantListingBinding
import com.shashankk.vm.IRestaurantListingVm
import com.shashankk.vm.RestaurantItemVm
import com.shashankk.vm.RestaurantListingVM
import io.reactivex.Single
import kotlinx.android.synthetic.main.restaurant_listing.*

class RestaurantListingFragment : ListingFragment(), IRestaurantListingVm {

    private val latitude = 53.2734
    private val longitude = -7.77832031

    private val PAGE_SIZE = 10

    private lateinit var vm: RestaurantListingVM

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        vm = RestaurantListingVM(this)
        val binding =
            DataBindingUtil.inflate<RestaurantListingBinding>(inflater, R.layout.restaurant_listing, container, false)
        binding.setLifecycleOwner(this)
        binding.vm = vm
        return binding.root
    }

    override fun getData(page: Int): Single<List<Restaurant>> =
        ListingApiCall(
            latitude,
            longitude,
            page,
            PAGE_SIZE,
            null,
            null
        ).executeAsObservable<VenueListingResponse>().map { it.groups[0].items }

    override fun getRecyclerView() = rvItems


    override fun onRecieveData(recommendations: List<Venue>) {
        vm.onData(recommendations)
    }

    override fun bindData(items: List<RestaurantItemVm>) {
        rvItems.adapter?.let {
            (it as RecyclerViewAdapter).updateData(items)
        } ?: let {
            rvItems.adapter = RecyclerViewAdapter(items, object : ViewProvider {
                override fun getLayout(model: Class<out ViewModel>): Int {
                    return when (model) {
                        RestaurantItemVm::class.java -> R.layout.list_item_restaurant
                        else -> R.layout.list_item_restaurant
                    }
                }
            })

            rvItems.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        }
    }

    override fun onRestaurantClick(id: String) {

    }

    override fun onLikeClick(id: String) {

    }

    override fun onDisLikeClick(id: String) {

    }

    override fun onCallClick(phoneNumber: String) {

    }

    override fun routeToMaps(latitude: Double, longitude: Double) {

    }
}