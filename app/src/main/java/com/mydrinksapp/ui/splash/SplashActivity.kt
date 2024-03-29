package com.mydrinksapp.ui.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import com.mydrinksapp.R
import com.mydrinksapp.databinding.ActivitySplashBinding
import com.mydrinksapp.ui.main.MainActivity
import kotlinx.coroutines.*

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding

    private val activityScope = CoroutineScope(Dispatchers.Main)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //This is used to hide the status bar and make the splash screen as a full screen activity
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        val bottomAnimation = AnimationUtils.loadAnimation(this, R.anim.desplazamiento_abajo)
        binding.cocktailApp.startAnimation(bottomAnimation)

        val headAnimation = AnimationUtils.loadAnimation(this, R.anim.desplazamiento_arriba)
        binding.splashScreenImage.startAnimation(headAnimation)

        activityScope.launch {
            delay(2000)
            startActivity(Intent(applicationContext, MainActivity::class.java))
            finish()
        }
    }

    override fun onPause() {
        activityScope.cancel()
        super.onPause()
    }
}