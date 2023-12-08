package com.capstoneproject.purrsonalcatapp.ui.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.capstoneproject.purrsonalcatapp.R
import com.capstoneproject.purrsonalcatapp.data.local.pref.AuthPreferences
import com.capstoneproject.purrsonalcatapp.data.local.pref.dataStore
import com.capstoneproject.purrsonalcatapp.databinding.ActivityMainBinding
import com.capstoneproject.purrsonalcatapp.ui.login.LoginActivity
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var authPreferences: AuthPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        authPreferences = AuthPreferences(this.dataStore)

        val authTokenFlow = authPreferences.authToken
        val authTokenJob = lifecycleScope.launch {
            authTokenFlow.collect{authToken ->
                if(authToken == null){
                    val intent = Intent(this@MainActivity, LoginActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
        }

        val username = intent.getStringExtra("USERNAME")
        if (!username.isNullOrBlank()){
            binding.tvUsername.text = "Welcome, $username"
        }

        binding.btnLogout.setOnClickListener {
            authTokenJob.cancel()
            lifecycleScope.launch {
                authPreferences.clearSession()
                val intent = Intent(this@MainActivity, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }
}