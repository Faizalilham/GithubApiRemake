package com.example.githubapiremake.fragments

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.githubapiremake.R
import com.example.githubapiremake.SecondActivity
import com.example.githubapiremake.databinding.FragmentLoginBinding
import com.example.githubapiremake.datastore.UserLoginPreferences
import com.example.githubapiremake.viewmodel.AuthViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import dagger.hilt.android.AndroidEntryPoint
import www.sanju.motiontoast.MotionToast
import www.sanju.motiontoast.MotionToastStyle

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private lateinit var binding : FragmentLoginBinding
    private lateinit var authViewModel: AuthViewModel
    private lateinit var userLoginPreferences: UserLoginPreferences
    private lateinit var googleSignInClient : GoogleSignInClient
    private lateinit var auth : FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(layoutInflater)
        userLoginPreferences = UserLoginPreferences(requireActivity())
        authViewModel = ViewModelProvider(requireActivity())[AuthViewModel::class.java]
        auth = FirebaseAuth.getInstance()
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestIdToken(getString(R.string.default_web_client_id)).requestEmail().build()
        googleSignInClient = GoogleSignIn.getClient(requireActivity(),gso)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        doLogin()
        toRegister()
        doSignInWithGoogle()
    }

    private fun doSignInWithGoogle(){
        binding.btnGoogle.setOnClickListener {
            signInGoogle()
        }
    }

    private val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        if(it.resultCode == Activity.RESULT_OK){
            val task = GoogleSignIn.getSignedInAccountFromIntent(it.data)
            handleResult(task)
        }
    }

    private fun handleResult(task: Task<GoogleSignInAccount>) {
        if(task.isSuccessful){
            val account = task.result
            if(account != null){
                setUI(account)
            }
        }else{
            setToast("Failed !", task.exception.toString(),MotionToastStyle.SUCCESS)
        }

    }

    private fun setUI(account: GoogleSignInAccount) {
        val credentials = GoogleAuthProvider.getCredential(account.idToken,null)
        auth.signInWithCredential(credentials).addOnCompleteListener {
            if(it.isSuccessful){
                Log.d("PHOTO RESPONSE",account.photoUrl.toString())
                authViewModel.setData(account.account.toString(), account.email.toString(),
                    account.displayName.toString(),account.photoUrl.toString()
                )
                startActivity(Intent(requireActivity(),SecondActivity::class.java).also {
                    activity?.finish()
                })
            }else{
                setToast("Failed !", it.exception.toString(),MotionToastStyle.SUCCESS)
            }
        }
    }

    private fun signInGoogle() {
        val signIn = googleSignInClient.signInIntent
        launcher.launch(signIn)
    }

    private fun doLogin(){
        binding.btnLogin.setOnClickListener {
            val email = binding.etEmail.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()
            if(email.isNotBlank() && password.isNotBlank() ){
                authViewModel.doLogin(email,password)
                authViewModel.loginObserver().observe(requireActivity()){
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

    private fun setToast(tittle : String,message :String,style : MotionToastStyle){
        MotionToast.createToast(requireActivity(), tittle,
            message,
            style,
            MotionToast.GRAVITY_BOTTOM,
            MotionToast.SHORT_DURATION,
            ResourcesCompat.getFont(requireActivity(),R.font.poppins_regular))
    }


}