package com.shashankk.model

import com.google.gson.annotations.SerializedName
import com.shashankk.model.newModels.Group

/**
 * Created by Shashank Kumar on 02/01/19.
 */
data class VenueListingResponse(

    @SerializedName("groups")
    val groups : ArrayList<Group<Restaurant>>
)