package com.shashankk.dao

import android.os.HandlerThread
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.shashankk.model.Venue
import com.shashankk.model.VenueDao
import com.shashankk.model.VenueTypeConverters

/**
 * Created by Shashank Kumar on 04/01/19.
 */
@Database(entities = [Venue::class], version = 1)
@TypeConverters(VenueTypeConverters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun VenueDao(): VenueDao
}