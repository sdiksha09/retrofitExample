package com.kotlinallsample.retrofitsample

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST
import java.util.HashMap

/**
 * Created by Nilesh Birhade on 19-06-2018.
 */
open class FetchMasterRequest : RetroRequestBuilder() {

    fun getService(): FetchMasterRequest.FetchNetworkService {
        return super.build().create(FetchMasterRequest.FetchNetworkService::class.java)
    }

    interface FetchNetworkService {

        @Headers("token:1234567890")
        @POST("/api/vehicle-details")
        fun vehicleMaster(@Body body: HashMap<String, String>): Call<CarMasterResponse>

    }
}