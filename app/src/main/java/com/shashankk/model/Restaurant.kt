package com.shashankk.model

import androidx.annotation.NonNull
import androidx.annotation.Nullable
import androidx.room.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.shashankk.model.newModels.Hours
import com.shashankk.model.newModels.Photo
import com.shashankk.model.newModels.Photos
import com.shashankk.model.newModels.Tips


data class Restaurant(val venue: Venue)

@Entity(tableName = "venue")
data class Venue(
    @PrimaryKey
    @NonNull
    var id: String,

    var name: String,

    @TypeConverters(VenueTypeConverters::class)
    @Nullable
    var contact: Contact? = null,

    @TypeConverters(VenueTypeConverters::class)
    @Nullable
    var location: Location? = null,

    @Nullable
    var verified: Boolean = false,

    @Nullable
    var url: String? = null,

    @TypeConverters(VenueTypeConverters::class)
    @Nullable
    var likes: Likes? = null,

    @Nullable
    var rating: Float = 0.toFloat(),

    @Nullable
    var ratingColor: String? = null,

    @TypeConverters(VenueTypeConverters::class)
    @Nullable
    var photos: Photos? = null,

    @Nullable
    var description: String? = null,

    @Nullable
    var createdAt: Float = 0.toFloat(),

    @Nullable
    var shortUrl: String? = null,

    @Nullable
    var timeZone: String? = null,

    @TypeConverters(VenueTypeConverters::class)
    @Nullable
    var hours: Hours? = null,

    @TypeConverters(VenueTypeConverters::class)
    @Nullable
    var bestPhoto: Photo? = null,

    var state: Int = 0

) {
    companion object {
        val STATE_LIKE = 1
        val STATE_DISLIKE = -1
        val STATE_NEUTRAL = 0
    }

    override fun equals(other: Any?): Boolean {
        return if (other is Venue?) id == other?.id
        else false
    }
}

class VenueTypeConverters {

    companion object {
        @JvmStatic
        inline fun <reified T : Any> toJson(value: T?): String {
            return Gson().toJson(value, T::class.java)
        }

        @JvmStatic
        inline fun <reified T : Any> fromJson(json: String): T? {
            return Gson().fromJson(json, T::class.java)
        }

        @JvmStatic
        @TypeConverter
        fun toPhoto(value: String): Photo? = fromJson(value)

        @JvmStatic
        @TypeConverter
        fun fromPhoto(value: Photo?): String = toJson(value)

        @JvmStatic
        @TypeConverter
        fun toPhotos(value: String): Photos? = fromJson(value)

        @JvmStatic
        @TypeConverter
        fun fromPhotos(value: Photos?): String = toJson(value)

        @JvmStatic
        @TypeConverter
        fun toLocation(value: String): Location? = fromJson(value)

        @JvmStatic
        @TypeConverter
        fun fromLocation(value: Location?): String = toJson(value)

        @JvmStatic
        @TypeConverter
        fun toHours(value: String): Hours? = fromJson(value)

        @JvmStatic
        @TypeConverter
        fun fromHours(value: Hours?): String = toJson(value)

        @JvmStatic
        @TypeConverter
        fun toContact(value: String): Contact? = fromJson(value)

        @JvmStatic
        @TypeConverter
        fun fromContact(value: Contact?): String = toJson(value)

        @JvmStatic
        @TypeConverter
        fun toLikes(value: String): Likes? = fromJson(value)

        @JvmStatic
        @TypeConverter
        fun fromLikes(value: Likes?): String = toJson(value)
    }

}

@Dao
interface VenueDao {

    @get:Query("SELECT * from venue")
    val allVenues: List<Venue>

    @Query("SELECT * from venue where state > 0 limit :pageSize offset :skip")
    fun getPaginatedLikedVenues(skip: Int, pageSize: Int): List<Venue>

    @Query("SELECT * from venue where state < 0 limit :pageSize offset :skip")
    fun getPaginatedHatedVenues(skip: Int, pageSize: Int): List<Venue>

    @Query("SELECT * FROM venue WHERE id IN(:ids)")
    fun getMatchingVenues(ids: List<String>): List<Venue>

    @Query("SELECT * FROM venue WHERE id= :id")
    fun getById(id: String): Venue?

    @Query("SELECT id FROM venue WHERE id= :id")
    fun getMatchingId(id: String): String

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(venue: Venue)

    @Query("DELETE FROM venue")
    fun deleteAll()

    @Query("DELETE FROM venue WHERE id = :id")
    fun deleteById(id: String)
}


class Contact {
    var phone: String? = null
    var formattedPhone: String? = null
    var twitter: String? = null
    var instagram: String? = null
    var facebook: String? = null
    var facebookUsername: String? = null
    var facebookName: String? = null
}

class Location {
    var address: String? = null
    var crossStreet: String? = null
    var lat: Double = 0.toDouble()
    var lng: Double = 0.toDouble()
    var postalCode: String? = null
    var cc: String? = null
    var city: String? = null
    var state: String? = null
    var country: String? = null
}

class Likes {
    var count: Float = 0.toFloat()
    var summary: String? = null
}

class User {
    var id: String? = null
    var firstName: String? = null
    var lastName: String? = null
    var gender: String? = null
    var photo: Photo? = null
    var type: String? = null
    var tips: Tips? = null
    var lists: Lists? = null
    var homeCity: String? = null
    var bio: String? = null
    var contact: Contact? = null
}
