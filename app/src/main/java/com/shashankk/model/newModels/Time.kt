package com.shashankk.model.newModels

import java.io.Serializable

/**
 * Created by Shashank Kumar on 02/01/19.
 */
class Hours : Serializable {
    var status: String? = null
    var isOpen: Boolean = false
    var isLocalHoliday: Boolean = false
    internal var timeframes = ArrayList<TimeFrame>()
}

class TimeFrame : Serializable{
    var days: String? = null
    var includesToday: Boolean = false
    internal var open = ArrayList<Time>()
}

class Time : Serializable{
    var renderedTime: String? = null
}