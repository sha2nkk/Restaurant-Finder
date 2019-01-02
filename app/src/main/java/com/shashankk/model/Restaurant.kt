package com.shashankk.model

import com.shashankk.model.newModels.*

data class Restaurant(
    val venue : Venue)

data class Venue(
    var id: String,
    var name: String,
    var contact: Contact? = null,
    var location: Location,
    var verified: Boolean = false,
    var url: String,
    var likes: Likes,
    var rating: Float = 0.toFloat(),
    var ratingColor: String? = null,
    var photos: Photos,
    var description: String? = null,
    var createdAt: Float = 0.toFloat(),
    var tips: Tips,
    var shortUrl: String? = null,
    var timeZone: String? = null,
    var hours: Hours? = null,
    var bestPhoto: BestPhoto? = null

)


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
    internal var formattedAddress = ArrayList<Any>()
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
