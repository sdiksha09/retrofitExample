package com.kotlinallsample

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import android.widget.Toast
import com.kotlinallsample.navigation.NavigationDrawerActivity
import com.kotlinallsample.recycler.RecyclerSampleActivity
import com.kotlinallsample.retrofitsample.RetrofitSampleActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setListener()
    }

    private fun setListener() {
        btnRecycler.setOnClickListener(this)
        btnNavigationDrawer.setOnClickListener(this)
        btnCamera.setOnClickListener(this)
        btnRetrofit.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btnRecycler -> {
                Toast.makeText(this, "Recycler click", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, RecyclerSampleActivity::class.java)
                startActivity(intent)
            }

            R.id.btnNavigationDrawer -> {
                Toast.makeText(this, "Navigation click", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, NavigationDrawerActivity::class.java)
                startActivity(intent)
            }

            R.id.btnCamera -> {
                Toast.makeText(this, "Camera click", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, CameraActivity::class.java)
                startActivity(intent)
            }

            R.id.btnRetrofit -> {
                Toast.makeText(this, "Retrofit click", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, RetrofitSampleActivity::class.java)
                startActivity(intent)
            }
        }
    }
}
