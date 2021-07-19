package com.example.githubdemo.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import com.example.githubdemo.R
import dagger.hilt.android.AndroidEntryPoint

/*
MainActivity which is entry point of the app
Written by Nathan N
*/

@Suppress("DEPRECATION")
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        hideStatusBar()
    }

    // hide status bar so user can read full screen
    private fun hideStatusBar(){
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }
}