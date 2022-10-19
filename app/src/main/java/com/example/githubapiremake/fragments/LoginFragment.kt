package com.example.githubapiremake.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.githubapiremake.R
import com.example.githubapiremake.SecondActivity
import com.example.githubapiremake.api.ApiService
import com.example.githubapiremake.databinding.FragmentLoginBinding
import com.example.githubapiremake.datastore.UserLoginPreferences
import com.example.githubapiremake.util.Constant
import com.example.githubapiremake.viewmodel.AuthViewModel
import com.example.githubapiremake.viewmodel.PreferenceFactory
import www.sanju.motiontoast.MotionToast
import www.sanju.motiontoast.MotionToastStyle

class LoginFragment : Fragment() {

    private lateinit var binding : FragmentLoginBinding
    private lateinit var authViewModel: AuthViewModel
    private lateinit var userLoginPreferences: UserLoginPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(layoutInflater)
        userLoginPreferences = UserLoginPreferences(requireActivity())
        authViewModel = ViewModelProvider(requireActivity(), PreferenceFactory(userLoginPreferences))[AuthViewModel::class.java]
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        doLogin()
        toRegister()
    }

    private fun doLogin(){
        binding.btnLogin.setOnClickListener {
            val email = binding.etEmail.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()
            if(email.isNotBlank() && password.isNotBlank() ){
                authViewModel.doSignIn(email,password)
                authViewModel.signInObserver().observe(requireActivity()){
                    if(it != null){
                        authViewModel.setToken(it.token)
                        setToast("Success !","Login Success Hallo ${it.name} ",MotionToastStyle.SUCCESS)
                        startActivity(Intent(requireActivity(),SecondActivity::class.java).also {
                            activity?.finish()
                        })
                    }
                }
            }else{
                setToast("Warning !","Field cannot be empety",MotionToastStyle.WARNING)
            }

        }

    }

    private fun toRegister(){
        binding.tvRegister.setOnClickListener {
            Navigation.findNavController(binding.root).navigate(R.id.registerFragment)
        }
    }

    fun setToast(tittle : String,message :String,style : MotionToastStyle){
        MotionToast.createToast(requireActivity(), tittle,
            message,
            style,
            MotionToast.GRAVITY_BOTTOM,
            MotionToast.SHORT_DURATION,
            ResourcesCompat.getFont(requireActivity(),R.font.poppins_regular))
    }


}