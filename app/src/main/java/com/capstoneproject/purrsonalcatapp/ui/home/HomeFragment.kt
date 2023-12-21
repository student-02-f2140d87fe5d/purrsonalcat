package com.capstoneproject.purrsonalcatapp.ui.home

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.capstoneproject.purrsonalcatapp.R
import com.capstoneproject.purrsonalcatapp.data.local.pref.AuthPreferences
import com.capstoneproject.purrsonalcatapp.data.local.pref.dataStore
import com.capstoneproject.purrsonalcatapp.data.remote.retrofit.ApiConfig
import com.capstoneproject.purrsonalcatapp.data.repository.AuthRepository
import com.capstoneproject.purrsonalcatapp.databinding.FragmentHomeBinding
import com.capstoneproject.purrsonalcatapp.ui.AuthViewModelFactory
import com.capstoneproject.purrsonalcatapp.ui.login.LoginActivity
import com.capstoneproject.purrsonalcatapp.ui.main.MainViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var authPreferences: AuthPreferences
    private lateinit var viewModel: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        return inflater.inflate(R.layout.fragment_home, container, false)
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        authPreferences = AuthPreferences(requireContext().dataStore)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch {
            val token = authPreferences.authToken.first()
            val apiService = ApiConfig.getApiService(token)
            val repository = AuthRepository(apiService, authPreferences)

            viewModel = ViewModelProvider(this@HomeFragment, AuthViewModelFactory(repository))
                .get(HomeViewModel::class.java)

            viewModel.userData.collect {userResponse ->
                if (userResponse != null) {
                    val username = userResponse.data?.username ?: "Guest"
                    binding.tvHomeUsername.text = "Hi, $username"
                }
            }
        }

        val authTokenFlow = authPreferences.authToken
        val authTokenJob = lifecycleScope.launch {
            authTokenFlow.collect {authToken ->
                if (authToken == null) {
                    val intent = Intent(requireContext(), LoginActivity::class.java)
                    startActivity(intent)
                    requireActivity().finish()
                }
            }
        }

        binding.btnLogoutHome.setOnClickListener {
            authTokenJob.cancel()
            lifecycleScope.launch {
                authPreferences.clearSession()
                val intent = Intent(requireContext(), LoginActivity::class.java)
                startActivity(intent)
                requireActivity().finishAffinity()
            }
        }
    }
}