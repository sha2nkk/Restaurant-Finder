package com.shashankk.api.volley

import android.net.Uri
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.shashankk.RestaurantApplication
import io.reactivex.Single
import io.reactivex.SingleOnSubscribe
import org.json.JSONObject
import java.lang.Exception
import java.net.URI
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by Shashank Kumar on 02/01/19.
 */
abstract class ApiCall<T>(
    val method: Int, private val listener: Response.Listener<T>?,
    private val errorListener: Response.ErrorListener?
) {

    val BASE_URL = "https://api.foursquare.com/v2"

    abstract val relativePath: String

    abstract val body: JSONObject?

    fun getURL(): String {

        val uri = Uri.parse("$BASE_URL/$relativePath")
            .buildUpon()
            .appendQueryParameter("client_id", "354QE2OQPBSQKMHM4YEYXU0LPA34RQS5P1EQGVM45TSAPOGI")
            .appendQueryParameter("client_secret", "WJR143VMWYVJ2AV3KO0XCJA1SDAGKWNQBXQUACGUXTHRVOCM")
            .appendQueryParameter("v", SimpleDateFormat("YYYYMMDD").format(Date()))
            .build()

        return uri.toString()
    }

    private fun createRequest() = JsonObjectRequest(method, getURL(), body, successListener(), errorListener())

    private fun successListener() =
        Response.Listener<JSONObject> { response ->
            if (response.has("response")) {
                listener?.onResponse(Gson().fromJson(response.getString("response"), object : TypeToken<T>() {}.type))
            } else {
                errorListener?.onErrorResponse(VolleyError("response not Found"))
            }
        }

    private fun errorListener() =
        Response.ErrorListener { err ->
            errorListener?.onErrorResponse(err)
        }

    fun execute() {
        getRequestQueue().add(createRequest())
    }

    inline fun <reified T> executeAsObservable(): Single<T> =
        Single.create<T> { emitter ->
            getRequestQueue().add(JsonObjectRequest(method, getURL(), body, { response ->
                if (response.has("response")) {
                    try {
                        val yourClassList: T = Gson().fromJson(response.getString("response"), T::class.java)
                        emitter.onSuccess(yourClassList)
                    } catch (e: Exception) {
                        emitter.onError(e)
                    }

                } else {
                    emitter.onError(VolleyError("response not Found"))
                }
            }, { err ->
                emitter.onError(err)
            }))
        }

    fun getRequestQueue() = RestaurantApplication.getInstance().requestQueue
}

