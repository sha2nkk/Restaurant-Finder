package com.shashankk.restaurant_finder

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationManager
import android.nfc.tech.MifareUltralight.PAGE_SIZE
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import com.shashankk.RestaurantApplication
import com.shashankk.api.volley.ListingApiCall
import com.shashankk.dao.AppDatabase
import com.shashankk.model.Restaurant
import com.shashankk.model.Venue
import com.shashankk.model.VenueListingResponse
import com.shashankk.restaurant_finder.databinding.RestaurantListingBinding
import com.shashankk.vm.RestaurantListingVM
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.restaurant_listing.*

class RestaurantListingFragment : ListingFragment() {

    private val PERMISSIONS =
        arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION)

    private var latitude: Double? = null
    private var longitude: Double? = null
    private lateinit var vm: RestaurantListingVM

    override fun getViewModel() = vm

    override val pageSize: Int
        get() = 10

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        vm = RestaurantListingVM(this)
        val binding =
            DataBindingUtil.inflate<RestaurantListingBinding>(inflater, R.layout.restaurant_listing, container, false)
        binding.setLifecycleOwner(this)
        binding.vm = vm
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requestPermissions()
    }

    override fun getDataSource(page: Int): Single<List<Venue>> =
        ListingApiCall(
            latitude ?: 0.0,
            longitude ?: 0.0,
            page,
            pageSize,
            null,
            null
        ).executeAsObservable<VenueListingResponse>()
            .map { it.groups.flatMap { it.items.map { it.venue } } }
            .observeOn(Schedulers.newThread())
            .map { apiVenues ->
                val ids = apiVenues.map { it.id }
                val dbVenues = RestaurantApplication.getInstance().databaseInstance.VenueDao().getMatchingVenues(ids)
                val finalVenues = ArrayList<Venue>()
                apiVenues.forEach { value ->
                    dbVenues.find { it == value }?.let {
                        if (it.state >= 0) {
                            finalVenues.add(it)
                        }
                    } ?: let {
                        finalVenues.add(value)
                    }
                }
                finalVenues as List<Venue>
            }

    private fun requestPermissions() {
        activity?.let {
            requestPermissions(PERMISSIONS, 101)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 101) {
            if (permissions.size != grantedPermissionCount(grantResults)) {
                Toast.makeText(context, "Permission Denied", Toast.LENGTH_SHORT).show()
            } else {
                startFetchingLocation()
            }
        }
    }

    private fun startFetchingLocation() {
        context?.let { context ->
            if (ActivityCompat.checkSelfPermission(
                    context, Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(
                    context, Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                requestPermissions()
                return
            }

            with(context.getSystemService(Context.LOCATION_SERVICE) as LocationManager) {
                getLastKnownLocation(LocationManager.GPS_PROVIDER)?.let {
                    latitude = it.latitude
                    longitude = it.longitude
                    startFetchingData()
                }
            }
        }
    }

    private fun grantedPermissionCount(grantResults: IntArray) = grantResults.filter { it != -1 }.count()

    override fun getRecyclerView() = rvItems!!
}