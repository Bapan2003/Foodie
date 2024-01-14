package com.example.foodie.Activity

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.KeyEvent
import android.view.WindowManager
import com.example.foodie.R
import com.example.foodie.databinding.ActivitySplashBinding

class SplashActivity : AppCompatActivity() {
    private lateinit var viewBinding:ActivitySplashBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding=ActivitySplashBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)


        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, 2500)


    }

    @SuppressLint("MissingSuperCall")
    override fun onBackPressed() {


    }
}