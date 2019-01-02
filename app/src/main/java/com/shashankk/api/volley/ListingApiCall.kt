package com.shashankk.api.volley

import com.android.volley.Request
import com.android.volley.Response
import com.shashankk.model.VenueListingResponse
import org.json.JSONObject

/**
 * Created by Shashank Kumar on 02/01/19.
 */
class ListingApiCall(
    private val latitude: Double,
    private val longitude: Double,
    val pageNo: Int,
    val pageSize : Int = 10,
    listener: Response.Listener<VenueListingResponse>?,
    errorListener: Response.ErrorListener?
) :
    ApiCall<VenueListingResponse>(Request.Method.GET, listener, errorListener) {

    override val relativePath: String
        get() = "venues/explore?ll=$latitude,$longitude&offset=${pageNo*10}&limit=$pageSize"
    override val body: JSONObject?
        get() = null
}