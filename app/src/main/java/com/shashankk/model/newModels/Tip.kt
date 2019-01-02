package com.shashankk.model.newModels

import com.shashankk.model.Likes
import com.shashankk.model.Todo
import com.shashankk.model.User

/**
 * Created by Shashank Kumar on 02/01/19.
 */

class Tips {
    var count: Float = 0.toFloat()
    internal var groups = ArrayList<Group<Tip>>()
}

class Tip {
    var id: String? = null
    var createdAt: Float = 0.toFloat()
    var text: String? = null
    var type: String? = null
    var canonicalUrl: String? = null
    var photo: Photo? = null
    var photourl: String? = null
    var lang: String? = null
    var likes: Likes? = null
    var logView: Boolean = false
    var agreeCount: Float = 0.toFloat()
    var disagreeCount: Float = 0.toFloat()
    var todo: Todo? = null
    var user: User? = null
}