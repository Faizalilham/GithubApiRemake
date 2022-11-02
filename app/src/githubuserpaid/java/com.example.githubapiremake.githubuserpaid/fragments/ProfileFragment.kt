package com.example.githubapiremake.githubuserpaid.fragments

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.githubapiremake.MainActivity
import com.example.githubapiremake.R
import com.example.githubapiremake.databinding.FragmentProfileBinding
import com.example.githubapiremake.datastore.UserLoginPreferences
import com.example.githubapiremake.fragments.ProfileFragmentDirections
import com.example.githubapiremake.model.UserUpdate
import com.example.githubapiremake.viewmodel.AuthViewModel
import com.example.githubapiremake.viewmodel.BlurViewModel
import com.example.githubapiremake.viewmodel.UserViewModel
import com.example.githubapiremake.worker.IMAGE_BLURRED
import com.example.githubapiremake.worker.OUTPUT_PATH
import com.example.githubapiremake.worker.PROFILE_IMAGE
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.*


@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private lateinit var binding : FragmentProfileBinding
    private lateinit var userViewModel: UserViewModel
    private lateinit var authViewModel: AuthViewModel
    private lateinit var userLoginPreferences: UserLoginPreferences
    private lateinit var auth : FirebaseAuth
    private val blur by lazy{
        activity?.application?.let { BlurViewModel(it) }
    }
    private var uri : Uri? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        userLoginPreferences = UserLoginPreferences(requireActivity())
        authViewModel = ViewModelProvider(requireActivity())[AuthViewModel::class.java]
        userViewModel = ViewModelProvider(requireActivity())[UserViewModel::class.java]
        auth = FirebaseAuth.getInstance()
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
        setImageBlured()

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
                    btnEditProfile.isEnabled = true
                    fab.isEnabled = true
                    openGallery()
                }
                doEditProfile(it.name,it.email,it.password)
            }else{
                Log.d("PROFILE","Profile Null")
            }
        }
        authViewModel.getDataAccount().observe(requireActivity()){if(it != null && it != "undefined"){binding.tvName.text = it } }
        authViewModel.getDataEmail().observe(requireActivity()){if(it != null && it != "undefined"){binding.tvEmail.text = it} }
        authViewModel.getDataName().observe(requireActivity()){if(it != null && it != "undefined"){binding.tvPassword.text = it }}
        authViewModel.getDataImage().observe(requireActivity()){
            if(it != "undefined"){
                Log.d("KONN",it)
                binding.apply {
                    Glide.with(root.context).load(it).into(imageUser)
                    btnEditProfile.isEnabled = false
                    fab.isEnabled = false
                }
            }else{
                Log.d("PHOTOSS_URL",(it != "undefined").toString())
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
            auth.signOut()
            startActivity(Intent(requireActivity(),MainActivity::class.java).also {
                authViewModel.apply {
                    deleteToken()
                    deleteData()
                }
            })
        }
    }

    private fun setLanguage(lang : String){
        val myLocale = Locale(lang)
        val conf = resources.configuration
        conf.locale = myLocale
        resources.updateConfiguration(conf,resources.displayMetrics)

    }

    private val useGallery = registerForActivityResult(ActivityResultContracts.GetContent()){
        if(it != null){
            uri = it
            binding.imageUser.setImageURI(it)
            blur?.setImageUri(it)
        }
        if(it != null){
            saveImage()
        }
    }

    private fun openGallery(){
       binding.fab.setOnClickListener {
           activity?.intent?.type = "image/*"
           useGallery.launch("image/*")
       }
    }

    private fun setImageBlured(){
        val image = BitmapFactory.decodeFile(requireActivity().applicationContext.filesDir.path + File.separator +OUTPUT_PATH+ File.separator + IMAGE_BLURRED)
        if(binding.imageUser.drawable == null){
            binding.imageUser.setImageBitmap(image)
        }
    }

    private fun saveImage(){
        val resolver = requireActivity().applicationContext.contentResolver
        val picture = BitmapFactory.decodeStream(
            resolver.openInputStream(Uri.parse(uri.toString())))
        saveImageProfile(requireContext(), picture)
        blur?.applyBlur()
    }


    private fun saveImageProfile(applicationContext: Context, bitmap: Bitmap): Uri {
        val name = "profile_image.png"
        val outputDir = File(applicationContext.filesDir, PROFILE_IMAGE)
        if (!outputDir.exists()) {
            outputDir.mkdirs()
        }
        val outputFile = File(outputDir, name)
        var out: FileOutputStream? = null
        try {
            out = FileOutputStream(outputFile)
            bitmap.compress(Bitmap.CompressFormat.PNG, 0 /* ignored for PNG */, out)
        } finally {
            out?.let {
                try {
                    it.close()
                } catch (ignore: IOException) {
                }

            }
        }
        Log.d("YGGY",outputFile.toString())
        Log.d("YGGGY",Uri.fromFile(outputFile).toString())
        return Uri.fromFile(outputFile)

    }


}