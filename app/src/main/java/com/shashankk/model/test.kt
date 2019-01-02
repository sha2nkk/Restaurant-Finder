package com.shashankk.model

import com.shashankk.model.newModels.BestPhoto
import com.shashankk.model.newModels.Hours
import com.shashankk.model.newModels.Photos
import com.shashankk.model.newModels.Tips


class Test {
    var id: String? = null
    var name: String? = null
    var contact: Contact? = null
    var location: Location? = null
    var canonicalUrl: String? = null
    internal var categories = ArrayList<Any>()
    var verified: Boolean = false
    var stats: Stats? = null
    var url: String? = null
    var likes: Likes? = null
    var rating: Float = 0.toFloat()
    var ratingColor: String? = null
    var ratingSignals: Float = 0.toFloat()
    var beenHere: BeenHere? = null
    var photos: Photos? = null
    var description: String? = null
    var storeId: String? = null
    var page: Page? = null
    var hereNow: HereNow? = null
    var createdAt: Float = 0.toFloat()
    var tips: Tips? = null
    var shortUrl: String? = null
    var timeZone: String? = null
    internal var phrases = ArrayList<Any>()
    var hours: Hours? = null
    var popular: Popular? = null
    var pageUpdates: PageUpdates? = null
    var inbox: Inbox? = null
    internal var venueChains = ArrayList<Any>()
    var attributes: Attributes? = null
    var bestPhoto: BestPhoto? = null
}

class Attributes {
    internal var groups = ArrayList<Any>()
}

class Inbox {
    var count: Float = 0.toFloat()
    internal var items = ArrayList<Any>()
}

class PageUpdates {
    var count: Float = 0.toFloat()
    internal var items = ArrayList<Any>()
}

class Popular {
    var status: String? = null
    var isOpen: Boolean = false
    var isLocalHoliday: Boolean = false
    internal var timeframes = ArrayList<Any>()
}

// Setter Methods
class Todo {

    var count: Float = 0.toFloat()
}

class HereNow {
    var count: Float = 0.toFloat()
    var summary: String? = null
    internal var groups = ArrayList<Any>()
}

class Page {
    var pageInfo: PageInfo? = null
    var user: User? = null
}

class Lists {
    internal var groups = ArrayList<Any>()
}

class PageInfo {
    var description: String? = null
    var banner: String? = null
    var links: Links? = null
}

class Links {
    var count: Float = 0.toFloat()
    internal var items = ArrayList<Any>()
}

class Source {
    var name: String? = null
    var url: String? = null
}

class BeenHere {
    var count: Float = 0.toFloat()
    var unconfirmedCount: Float = 0.toFloat()
    var marked: Boolean = false
    var lastCheckinExpiredAt: Float = 0.toFloat()
}

class Stats {
    var checkinsCount: Float = 0.toFloat()
    var usersCount: Float = 0.toFloat()
    var tipCount: Float = 0.toFloat()
    var visitsCount: Float = 0.toFloat()
}