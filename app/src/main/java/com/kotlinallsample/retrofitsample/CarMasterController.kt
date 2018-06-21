package com.kotlinallsample.retrofitsample

import android.content.Context
import retrofit2.Call
import retrofit2.Response
import java.util.*
import javax.security.auth.callback.Callback

/**
 * Created by Nilesh Birhade on 19-06-2018.
 */

open class CarMasterController {

    var mContext: Context? = null
    var mCallback: callback? = null

    interface callback {
        fun callbackResponse(response: CarMasterResponse)
    }

    constructor(context: Context, callback: callback) {
        mContext = context
        mCallback = callback
    }


    fun serviceCall() {
        val body = HashMap<String, String>()
        body.put("ProductId", "1")

        FetchMasterRequest().getService().vehicleMaster(body)

                .enqueue(object : retrofit2.Callback<CarMasterResponse> {

                    override fun onFailure(call: Call<CarMasterResponse>?, t: Throwable?) {
                        mCallback?.callbackResponse(null as CarMasterResponse)
                    }

                    override fun onResponse(call: Call<CarMasterResponse>?, response: Response<CarMasterResponse>?) {
                        mCallback?.callbackResponse(response?.body() as CarMasterResponse)
                    }
                })

    }
}


