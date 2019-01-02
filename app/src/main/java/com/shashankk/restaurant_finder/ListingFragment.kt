package com.shashankk.restaurant_finder

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.shashankk.model.Restaurant
import com.shashankk.model.Venue
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

abstract class ListingFragment : Fragment() {

    var page = 0

    private val subscriptions = ArrayList<Disposable>()

    abstract fun getData(page : Int): Single<List<Restaurant>>

    abstract fun getRecyclerView() : RecyclerView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        page = 0
        subscriptions.add(getData(page)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ list ->
                //Bind RecyclerView
                onRecieveData(list.map { it.venue })
                Toast.makeText(context,"success",Toast.LENGTH_SHORT).show()
            }, {
                it.printStackTrace()
                Toast.makeText(context,"failed",Toast.LENGTH_SHORT).show()
            })
        )
    }

    abstract fun onRecieveData(recommendations: List<Venue>)

    override fun onDestroy() {
        subscriptions.forEach { it.dispose() }
        super.onDestroy()
    }
}