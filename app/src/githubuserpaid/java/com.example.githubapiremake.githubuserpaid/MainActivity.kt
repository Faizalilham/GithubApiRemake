package com.example.githubapiremake.githubuserpaid

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.githubapiremake.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // throw RuntimeException("Test Crash") Force a crash
    }
}