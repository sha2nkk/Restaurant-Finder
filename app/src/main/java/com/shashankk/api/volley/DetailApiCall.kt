package com.shashankk.api.volley

import com.android.volley.Request
import com.android.volley.Response
import com.shashankk.model.Venue
import com.shashankk.model.VenueListingResponse
import org.json.JSONObject

/**
 * Created by Shashank Kumar on 02/01/19.
 */
class DetailApiCall(
    private val venueId:String,
    listener: Response.Listener<Venue>?,
    errorListener: Response.ErrorListener?
) :
    ApiCall<Venue>(Request.Method.GET, listener, errorListener) {

    override val relativePath: String
        get() = "/venues/$venueId"
    override val body: JSONObject?
        get() = null
}