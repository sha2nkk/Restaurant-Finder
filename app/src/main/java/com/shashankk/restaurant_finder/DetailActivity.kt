package com.shashankk.restaurant_finder

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.shashankk.RestaurantApplication
import com.shashankk.api.volley.DetailApiCall
import com.shashankk.model.Venue
import com.shashankk.model.VenueDetailResponse
import com.shashankk.restaurant_finder.databinding.ContentRestaurantDetailBinding
import com.shashankk.vm.IRestaurantVM
import com.shashankk.vm.RestaurantItemVm
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class DetailActivity : AppCompatActivity(), IRestaurantVM {

    val subscriptions = ArrayList<Disposable>()

    val venueId: String by lazy { intent.getStringExtra("id") }

    lateinit var binding: ContentRestaurantDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =
                DataBindingUtil.setContentView(this, R.layout.content_restaurant_detail)
        binding.setLifecycleOwner(this)
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        getData()
    }

    private fun getData() {
        subscriptions.add(
            DetailApiCall(venueId, null, null).executeAsObservable<VenueDetailResponse>()
                .map { it -> it.venue }
                .map {
                    RestaurantApplication.getDatabaseInstance().VenueDao().getById(it.id)?.let { dbEntry ->
                        it.state = dbEntry.state
                    }
                    it
                }
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { venue ->
                        run {
                            binding.vm = RestaurantItemVm(venue, this@DetailActivity)
                            binding.executePendingBindings()
                        }
                    },
                    { error ->
                        finish()
                    }
                )
        )
    }

    override fun onRestaurantClick(id: String) {
    }

    override fun onLikeClick(venue: Venue) {
    }

    override fun onDisLikeClick(venue: Venue) {
    }

    @SuppressLint("MissingPermission")
    override fun onCallClick(phoneNumber: String?) {
        phoneNumber?.let {
            val callIntent = Intent(Intent.ACTION_CALL)
            callIntent.data = Uri.parse("tel:$phoneNumber")
            startActivity(callIntent)
        }.let {
            Toast.makeText(this@DetailActivity, "Phone Number not Shared", Toast.LENGTH_SHORT).show()
        }
    }

    override fun routeToMaps(latitude: Double, longitude: Double) {
        val i = Intent(Intent.ACTION_VIEW, Uri.parse("http://maps.google.com/maps?q=loc:$latitude,$longitude"))
        startActivity(i)
    }
}