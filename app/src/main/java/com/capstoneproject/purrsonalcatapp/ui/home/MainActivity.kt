package com.capstoneproject.purrsonalcatapp.ui.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.capstoneproject.purrsonalcatapp.R
import com.capstoneproject.purrsonalcatapp.data.local.pref.AuthPreferences
import com.capstoneproject.purrsonalcatapp.data.local.pref.dataStore
import com.capstoneproject.purrsonalcatapp.data.remote.retrofit.ApiConfig
import com.capstoneproject.purrsonalcatapp.data.repository.AuthRepository
import com.capstoneproject.purrsonalcatapp.databinding.ActivityMainBinding
import com.capstoneproject.purrsonalcatapp.ui.AuthViewModelFactory
import com.capstoneproject.purrsonalcatapp.ui.login.LoginActivity
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var authPreferences: AuthPreferences
    private lateinit var viewModel: MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        authPreferences = AuthPreferences(this.dataStore)
        lifecycleScope.launch {
            val token = authPreferences.authToken.first()
            val apiService = ApiConfig.getApiService(token)
            val repository = AuthRepository(apiService, authPreferences)

            viewModel =
                ViewModelProvider(this@MainActivity, AuthViewModelFactory(repository)).get(
                    MainViewModel::class.java
                )

            viewModel.userData.collect { userResponse ->
                if (userResponse != null) {
                    val username = userResponse.data?.username ?: "Guest"
                    binding.tvUsername.text = "Welcome, $username"
                }
            }

        }

        val authTokenFlow = authPreferences.authToken
        val authTokenJob = lifecycleScope.launch {
            authTokenFlow.collect { authToken ->
                if (authToken == null) {
                    val intent = Intent(this@MainActivity, LoginActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
        }


        binding.btnLogout.setOnClickListener {
            authTokenJob.cancel()
            lifecycleScope.launch {
                authPreferences.clearSession()
                val intent = Intent(this@MainActivity, LoginActivity::class.java)
                startActivity(intent)
                finishAffinity()
            }
        }
    }
}