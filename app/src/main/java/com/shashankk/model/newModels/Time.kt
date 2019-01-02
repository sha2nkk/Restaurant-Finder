package com.shashankk.model.newModels

/**
 * Created by Shashank Kumar on 02/01/19.
 */
class Hours {
    var status: String? = null
    var isOpen: Boolean = false
    var isLocalHoliday: Boolean = false
    internal var timeframes = ArrayList<TimeFrame>()
}

class TimeFrame {
    var days: String? = null
    var includesToday: Boolean = false
    internal var open = ArrayList<Time>()
}

class Time {
    var renderedTime: String? = null
}