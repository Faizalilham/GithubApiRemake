package com.example.githubapiremake.githubuserpaid


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.githubapiremake.R
import com.example.githubapiremake.databinding.ActivitySecondBinding
import com.example.githubapiremake.fragments.FavoriteFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SecondActivity : AppCompatActivity() {
    private var _binding : ActivitySecondBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val host = supportFragmentManager.findFragmentById(R.id.secondFragmentContainerView) as NavHostFragment
        binding.apply {
            bottomNavigation.setupWithNavController(host.navController)
            bottomNavigation.background = null
            fabFavorite.setOnClickListener {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.secondFragmentContainerView,FavoriteFragment())
                    .commit()
            }
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}