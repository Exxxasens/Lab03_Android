package com.example.lab03

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider

class MainActivity : AppCompatActivity() {

    private lateinit var myViewModel: MyViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        myViewModel = ViewModelProvider(this)[MyViewModel::class.java]

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction().replace(R.id.MainFragmentContainerView, FirstFragment.newInstance(), "First").commit()
        }

    }


    // private fun openFragment(f: Fragment) {
    // }


}