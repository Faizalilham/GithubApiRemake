package com.example.githubapiremake.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.githubapiremake.R
import com.example.githubapiremake.databinding.FragmentRegisterBinding
import com.example.githubapiremake.viewmodel.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint
import www.sanju.motiontoast.MotionToast
import www.sanju.motiontoast.MotionToastStyle


@AndroidEntryPoint
class RegisterFragment : Fragment() {


    private lateinit var binding: FragmentRegisterBinding
    private lateinit var authViewModel: AuthViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegisterBinding.inflate(layoutInflater)
        authViewModel = ViewModelProvider(requireActivity())[AuthViewModel::class.java]
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        doRegister()
        toLogin()
    }

    private fun doRegister() {
        binding.btnRegister.setOnClickListener {
            val name = binding.etUser.text.toString().trim()
            val email = binding.etEmail.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()

            if (name.isNotBlank() && email.isNotBlank() && password.isNotBlank()) {
               if(password.length >= 6){
                   authViewModel.doRegister(name, email, password)
                   authViewModel.registerObserver().observe(requireActivity()) {
                       if (it != null) {
                           setToast("Success", "Register Succesfully", MotionToastStyle.SUCCESS)
                           Navigation.findNavController(binding.root).navigate(R.id.loginFragment)
                       }else{
                           Toast.makeText(requireActivity(), "Null Blok", Toast.LENGTH_SHORT).show()
                       }
                   }
               }else{
                   setToast("Warning !", "Password must be at least 6 characters", MotionToastStyle.WARNING)
               }
            } else {
                setToast("Warning !", "Field cannot be empety", MotionToastStyle.WARNING)
            }
        }
    }

    private fun toLogin() {
        binding.tvLogin.setOnClickListener {
            Navigation.findNavController(binding.root).navigate(R.id.loginFragment)
        }
    }

    private fun setToast(tittle: String, message: String, style: MotionToastStyle) {
        MotionToast.createToast(
            requireActivity(), tittle,
            message,
            style,
            MotionToast.GRAVITY_BOTTOM,
            MotionToast.SHORT_DURATION,
            ResourcesCompat.getFont(requireActivity(), R.font.poppins_regular)
        )
    }
}

