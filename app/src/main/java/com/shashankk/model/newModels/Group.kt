package com.shashankk.model.newModels

class Group<T> {
    var type: String? = null
    var name: String? = null
    internal var items = ArrayList<T>()
}