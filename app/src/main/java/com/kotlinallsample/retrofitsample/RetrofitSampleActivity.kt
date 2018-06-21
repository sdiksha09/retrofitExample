package com.kotlinallsample.retrofitsample

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.widget.Toast
import com.kotlinallsample.R
import com.kotlinallsample.retrofitsample.CarMasterController.callback

import kotlinx.android.synthetic.main.activity_retrofit_sample.*

class RetrofitSampleActivity : AppCompatActivity(), callback {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_retrofit_sample)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        rvRetrofit.layoutManager = LinearLayoutManager(this)
        rvRetrofit.adapter = null

        CarMasterController(this, this).serviceCall()

    }

    override fun callbackResponse(response: CarMasterResponse) {
        val size = response.MasterData.size
        Toast.makeText(this, "" + size, Toast.LENGTH_SHORT).show()
    }

}
