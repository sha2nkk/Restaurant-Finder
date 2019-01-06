package com.shashankk

import android.app.Application
import androidx.room.Room
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley
import com.shashankk.dao.AppDatabase
import android.os.HandlerThread




class RestaurantApplication : Application() {

    val requestQueue: RequestQueue by lazy {
        Volley.newRequestQueue(this)
    }

    val databaseInstance: AppDatabase by lazy {
        Room.databaseBuilder(
            this,
            AppDatabase::class.java, "venueDb"
        ).allowMainThreadQueries().build()
    }

    val dbThread :HandlerThread by lazy { HandlerThread("DBThread")}

    companion object {

        private lateinit var instance: RestaurantApplication

        fun getInstance() = instance

        fun getDatabaseInstance() = instance.databaseInstance

        fun getDBThread() = instance.dbThread
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        createDBThread()
    }

    private fun createDBThread() {
        dbThread.start()
    }


}