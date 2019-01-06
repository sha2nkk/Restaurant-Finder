package com.shashankk.restaurant_finder

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.shashankk.RestaurantApplication
import com.shashankk.base.RecyclerViewAdapter
import com.shashankk.base.ViewProvider
import com.shashankk.model.Venue
import com.shashankk.vm.IRestaurantListingVm
import com.shashankk.vm.RestaurantItemVm
import com.shashankk.vm.RestaurantListingVM
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import androidx.core.content.ContextCompat.startActivity
import java.util.*


abstract class ListingFragment : Fragment(), IRestaurantListingVm {


    private val subscriptions = ArrayList<Disposable>()

    abstract fun getDataSource(page: Int): Single<List<Venue>>

    abstract fun getViewModel(): RestaurantListingVM

    abstract fun getRecyclerView(): RecyclerView

    abstract val pageSize: Int

    fun startFetchingData() {
        getViewModel().onComponentLoad()
    }

    override fun getDataForPage(page: Int) {
        subscriptions.add(
            getDataSource(page)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ list ->
                    //Bind RecyclerView
                    onRecieveData(list)
                }, {
                    it.printStackTrace()
                    Toast.makeText(context, "failed", Toast.LENGTH_SHORT).show()
                    getViewModel().onError()
                })
        )
    }

    override fun bindData(items: List<RestaurantItemVm>) {
        getRecyclerView().apply {
            adapter?.let {
                (it as RecyclerViewAdapter).updateData(items)
            } ?: let {
                adapter = RecyclerViewAdapter(items, object : ViewProvider {
                    override fun getLayout(model: Class<out ViewModel>): Int {
                        return when (model) {
                            RestaurantItemVm::class.java -> R.layout.list_item_restaurant
                            else -> R.layout.list_item_restaurant
                        }
                    }

                    override fun getLifecycleOwner(): LifecycleOwner? {
                        return this@ListingFragment;
                    }
                })

                layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
                addOnScrollListener(object : RecyclerView.OnScrollListener() {

                    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                        super.onScrolled(recyclerView, dx, dy)
                        val visibleItemCount = layoutManager?.childCount ?: 0
                        val totalItemCount = layoutManager?.itemCount ?: 0
                        val firstVisibleItemPosition =
                            (layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()

                        if (getViewModel().shouldLoadMoreItems()) {
                            if (visibleItemCount + firstVisibleItemPosition >= totalItemCount
                                && firstVisibleItemPosition >= 0
                                && totalItemCount >= pageSize
                            ) {
                                getViewModel().loadMoreItems()
                            }
                        }
                    }
                });
            }
        }
    }

    override fun setListLoader(isVisible: Boolean) {
        (getRecyclerView().adapter as RecyclerViewAdapter?)?.setLoaderVisibilty(isVisible)
    }

    fun onRecieveData(recommendations: List<Venue>) {
        getViewModel().onData(recommendations)
    }

    override fun onDestroy() {
        subscriptions.forEach { it.dispose() }
        super.onDestroy()
    }

    override fun onRestaurantClick(id: String) {
        val i = Intent(context, DetailActivity::class.java)
        i.putExtra("id", id)
        startActivity(i)
    }

    override fun onLikeClick(venue: Venue) {
        Handler(RestaurantApplication.getDBThread().looper).post {
            RestaurantApplication.getDatabaseInstance().VenueDao().insert(venue)
        }

    }

    override fun onDisLikeClick(venue: Venue) {
        Handler(RestaurantApplication.getDBThread().looper).post {
            RestaurantApplication.getDatabaseInstance().VenueDao().insert(venue)
        }
    }

    override fun onCallClick(phoneNumber: String?) {
        phoneNumber?.let {
            val callIntent = Intent(Intent.ACTION_CALL)
            callIntent.data = Uri.parse("tel:$phoneNumber")
            startActivity(callIntent)
        }.let {
            Toast.makeText(context, "Phone Number not Shared", Toast.LENGTH_SHORT).show()
        }

    }

    override fun routeToMaps(latitude: Double, longitude: Double) {
        val i = Intent(Intent.ACTION_VIEW, Uri.parse("http://maps.google.com/maps?q=loc:$latitude,$longitude"))
        startActivity(i)
    }
}