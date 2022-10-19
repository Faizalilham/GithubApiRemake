package com.example.githubapiremake.fragments

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.githubapiremake.R
import com.example.githubapiremake.SecondActivity
import com.example.githubapiremake.databinding.FragmentSplashBinding
import com.example.githubapiremake.datastore.UserLoginPreferences
import com.example.githubapiremake.util.Constant
import com.example.githubapiremake.viewmodel.AuthViewModel
import com.example.githubapiremake.viewmodel.PreferenceFactory


class SplashFragment : Fragment() {


    private lateinit var binding : FragmentSplashBinding
    private lateinit var authViewModel: AuthViewModel
    private lateinit var userLoginPreferences: UserLoginPreferences
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        userLoginPreferences = UserLoginPreferences(requireActivity())
        authViewModel = ViewModelProvider(requireActivity(), PreferenceFactory(userLoginPreferences))[AuthViewModel::class.java]
        binding = FragmentSplashBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed({
            isLogin()
        },1500)
    }

    private fun isLogin(){
        authViewModel.getToken().observe(requireActivity()){
            if(it != null){
                if(!it.equals("undefined")){
                    startActivity(Intent(requireActivity(),SecondActivity::class.java).also{ activity?.finish()})
                }else{
                    Navigation.findNavController(binding.root).navigate(R.id.loginFragment)
                }
            }else{
                Toast.makeText(requireActivity(), "Token Null", Toast.LENGTH_SHORT).show()
            }
        }

    }


}