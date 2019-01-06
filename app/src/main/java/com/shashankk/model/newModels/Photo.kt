package com.shashankk.model.newModels

import com.shashankk.model.Source
import com.shashankk.model.User
import java.io.Serializable

/**
 * Created by Shashank Kumar on 02/01/19.
 */
class Photos : Serializable{
    var count: Float = 0.toFloat()
    internal var groups = ArrayList<Group<Photo>>()
}

class Photo : Serializable {
    var id: String? = null
    var createdAt: Float = 0.toFloat()
    var source: Source? = null
    var prefix: String? = null
    var suffix: String? = null
    var width: Float = 0.toFloat()
    var height: Float = 0.toFloat()
    var visibility: String? = null
}