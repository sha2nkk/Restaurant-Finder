package com.shashankk.restaurant_finder

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.shashankk.RestaurantApplication
import com.shashankk.model.Venue
import com.shashankk.restaurant_finder.databinding.RestaurantListingBinding
import com.shashankk.vm.ILikedRestaurantListingVm
import com.shashankk.vm.LikedRestaurantListingVM
import com.shashankk.vm.RestaurantListingVM
import io.reactivex.Single
import kotlinx.android.synthetic.main.restaurant_listing.*

class LikedRestaurantFragment : ListingFragment(), ILikedRestaurantListingVm {

    private val latitude = 28.491906
    private val longitude = 77.0780398
    private lateinit var vm: RestaurantListingVM

    override fun getViewModel() = vm

    override val pageSize: Int
        get() = 10

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        vm = LikedRestaurantListingVM(this)
        val binding =
            DataBindingUtil.inflate<RestaurantListingBinding>(inflater, R.layout.restaurant_listing, container, false)
        binding.setLifecycleOwner(this)
        binding.vm = vm
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        startFetchingData()
    }

    override fun getDataSource(page: Int) =
        Single.create<List<Venue>> {
            it.onSuccess(RestaurantApplication.getDatabaseInstance().VenueDao().getPaginatedLikedVenues(
                page * pageSize,
                pageSize
            ))
        }!!

    override fun getRecyclerView() = rvItems!!

    override fun removeItemAtIndex(Index: Int) {
        rvItems.adapter?.notifyItemRemoved(Index)
    }
}