package com.example.githubapiremake.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.example.githubapiremake.R
import com.example.githubapiremake.databinding.FragmentEditProfileBinding
import com.example.githubapiremake.datastore.UserLoginPreferences
import com.example.githubapiremake.viewmodel.AuthViewModel
import com.example.githubapiremake.viewmodel.PreferenceFactory
import com.example.githubapiremake.viewmodel.UserViewModel
import www.sanju.motiontoast.MotionToast
import www.sanju.motiontoast.MotionToastStyle


class EditProfileFragment : Fragment() {


    private lateinit var binding : FragmentEditProfileBinding
    private lateinit var userViewModel: UserViewModel
    private lateinit var authViewModel: AuthViewModel
    private lateinit var userLoginPreferences: UserLoginPreferences

    private val args by navArgs<EditProfileFragmentArgs>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        userLoginPreferences = UserLoginPreferences(requireActivity())
        authViewModel = ViewModelProvider(requireActivity(),
            PreferenceFactory(userLoginPreferences)
        )[AuthViewModel::class.java]
        userViewModel = ViewModelProvider(requireActivity())[UserViewModel::class.java]
        binding = FragmentEditProfileBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        doEditProfile()
        setData()
    }

    private fun setData(){
        binding.apply {
            val(name,email,password) = args.userUpdate
            etName.setText(name)
            etEmail.setText(email)
            etPassword.setText(password)
        }
    }

    private fun doEditProfile(){
        binding.btnEditProfile.setOnClickListener {
            val name = binding.etName.text.toString().trim()
            val email = binding.etEmail.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()

            authViewModel.getToken().observe(requireActivity()){ token ->
                if(token != null){
                    if(name.isNotBlank() && email.isNotBlank() && password.isNotBlank()){
                        if(password.length >= 6){
                            userViewModel.updateCurrentUser("Bearer $token",name,email,password)
                            userViewModel.updateCurrentUserObserve().observe(requireActivity()){
                                if(it != null){
                                    setToast("Success !","Update Profile Success ",MotionToastStyle.SUCCESS)
                                    Navigation.findNavController(binding.root).navigate(R.id.profileFragment)
                                }else{
                                    setToast("Error !","Update Profile Failed ",MotionToastStyle.ERROR)
                                }
                            }
                        }else{
                            setToast("Warning !","Password must be at least 6 characters ",MotionToastStyle.WARNING)
                        }
                    }else{
                        setToast("Warning !","Field Cannot be Empety",MotionToastStyle.WARNING)
                    }
                }else{
                    Log.d("TOKEN","Token Null")
                }
            }
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