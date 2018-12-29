package com.shashankk.restaurant_finder

import androidx.fragment.app.Fragment
import com.shashankk.model.Restaurant

abstract class ListingFragment : Fragment() {

    abstract fun getData() : List<Restaurant>
}