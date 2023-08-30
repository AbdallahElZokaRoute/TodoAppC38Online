package com.route.todosappc38online.ui.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.route.todosappc38online.R
import com.route.todosappc38online.ui.home.HomeActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler(Looper.getMainLooper()).postDelayed(
            {

                startActivity( Intent(this@SplashActivity, HomeActivity::class.java))
                finish()
            },
            2500
        )
    }
}