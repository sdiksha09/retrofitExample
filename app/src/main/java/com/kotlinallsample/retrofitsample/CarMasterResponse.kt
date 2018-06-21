package com.kotlinallsample.retrofitsample

data class CarMasterResponse(
        val Message: String,
        val Status: String,
        val StatusNo: Int,
        val MasterData: List<MasterData>
)