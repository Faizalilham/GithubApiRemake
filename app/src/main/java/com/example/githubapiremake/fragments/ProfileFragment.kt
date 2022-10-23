package com.example.githubapiremake.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.githubapiremake.MainActivity
import com.example.githubapiremake.databinding.FragmentProfileBinding
import com.example.githubapiremake.datastore.UserLoginPreferences
import com.example.githubapiremake.model.UserUpdate
import com.example.githubapiremake.viewmodel.AuthViewModel
import com.example.githubapiremake.viewmodel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.*


@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private lateinit var binding : FragmentProfileBinding
    private lateinit var userViewModel: UserViewModel
    private lateinit var authViewModel: AuthViewModel
    private lateinit var userLoginPreferences: UserLoginPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        userLoginPreferences = UserLoginPreferences(requireActivity())
        authViewModel = ViewModelProvider(requireActivity())[AuthViewModel::class.java]
        userViewModel = ViewModelProvider(requireActivity())[UserViewModel::class.java]
        binding = FragmentProfileBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getProfile()
        setData()
        doLogout()
        binding.btnLanguage.setOnClickListener {
            setLanguage("ja")
        }

    }

    private fun getProfile(){
        authViewModel.getToken().observe(requireActivity()){
            if(it != null){
                userViewModel.currentUser("Bearer $it")
            }else{
                Log.d("TOKEN","Token Null")
            }
        }
    }

    private fun setData(){
        userViewModel.getCurrentUserObserver().observe(requireActivity()){
            if(it != null){
                binding.apply {
                    tvName.text = it.name
                    tvEmail.text = it.email
                    tvPassword.text = it.password
                }
                doEditProfile(it.name,it.email,it.password)
            }else{
                Log.d("PROFILE","Profile Null")
            }
        }
    }

    private fun doEditProfile(name:String,email :String,password :String){
        binding.btnEditProfile.setOnClickListener {
            val userUpdate = UserUpdate(name,email,password)
            val args = ProfileFragmentDirections.actionProfileFragmentToEditProfileFragment(userUpdate)
            findNavController().navigate(args)
        }
    }

    private fun doLogout(){
        binding.btnLogout.setOnClickListener {
            startActivity(Intent(requireActivity(),MainActivity::class.java).also {
                authViewModel.deleteToken()
            })
        }
    }

    private fun setLanguage(lang : String){
        val myLocale = Locale(lang)
        val conf = resources.configuration
        conf.locale = myLocale
        resources.updateConfiguration(conf,resources.displayMetrics)

    }


}