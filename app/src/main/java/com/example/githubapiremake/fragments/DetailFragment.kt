package com.example.githubapiremake.fragments

import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.example.githubapi.adapter.ViewPagerAdapter
import com.example.githubapi.fragments.FollowersFragment
import com.example.githubapi.fragments.FollowingFragment
import com.example.githubapi.viewmodel.UserGithubViewModel
import com.example.githubapiremake.R
import com.example.githubapiremake.databinding.FragmentDetailBinding
import com.example.githubapiremake.model.Favorite
import com.example.githubapiremake.room.SetupRoom
import com.example.githubapiremake.viewmodel.FavoriteViewModel
import com.google.android.material.tabs.TabLayoutMediator
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class DetailFragment : Fragment() {


    private lateinit var binding : FragmentDetailBinding
    private lateinit var userGithubViewModel: UserGithubViewModel
    private lateinit var favoriteViewModel: FavoriteViewModel
    private val args by navArgs<DetailFragmentArgs>()
    private var _isBookmarked = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailBinding.inflate(layoutInflater)
        userGithubViewModel = ViewModelProvider(requireActivity())[UserGithubViewModel::class.java]
        favoriteViewModel = ViewModelProvider(requireActivity())[FavoriteViewModel::class.java]
        binding.toolbar.title = null
        (activity as AppCompatActivity).setSupportActionBar(binding.toolbar)
        (activity as AppCompatActivity).supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowTitleEnabled(false)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setViewPager()
        showDetail()
        ARGS = args.userGithub.login
        checkFavorite()
        addToFavorite()
        moveBack()

    }

    private fun setViewPager(){
        val fragmentList = arrayListOf(
           FollowersFragment(),
           FollowingFragment()
        )
        val viewPagerAdapter = ViewPagerAdapter(fragmentList,childFragmentManager,lifecycle)
        binding.viewPager.adapter = viewPagerAdapter
        TabLayoutMediator(binding.tabsLayout,binding.viewPager){tab,position ->
            when(position){
                0 -> tab.text = "Followers"
                1 -> tab.text = "Following"
            }
        }.attach()
    }

    private fun checkFavorite(){
        favoriteViewModel.checkUser(args.userGithub.id)
        favoriteViewModel.checkUserObserver().observe(requireActivity()){
            if(it != null){
                if(it > 0){
                    binding.btnFavorite.setImageDrawable(ContextCompat.getDrawable(requireActivity(),
                        R.drawable.ic_bookmark_checked))
                    _isBookmarked = true
                }else{
                    binding.btnFavorite.setImageDrawable(ContextCompat.getDrawable(requireActivity(),
                        R.drawable.ic_bookmark))
                    _isBookmarked = false
                }
            }
        }
    }
    private fun showDetail(){
        userGithubViewModel.detailUser(args.userGithub.login)
        val progressDialog = ProgressDialog(requireActivity())
        progressDialog.setTitle("Please Wait")
        progressDialog.setMessage("Load data from github api")
        progressDialog.show()
        userGithubViewModel.detailUserObserver().observe(requireActivity()){
            if(it != null){
                progressDialog.dismiss()
                binding.apply {
                    binding.collapsingToolbarLayout.title = it.login
                    Picasso.with(context).load(it.avatar_url).resize(120,120).centerCrop().into(imageUser)
                    val forTvUsername = "${it.login}  |  ${it.location}  |  ${it.company} "
                    tvUsername.text = forTvUsername
                    tvBio.text = it.bio
                    tvFollowers.text = it.followers.toString()
                    tvFollowing.text = it.following.toString()
                    tvRepository.text = it.public_repos.toString()
                    btnViewGithub.setOnClickListener { _ ->
                        val intent = Intent(Intent.ACTION_VIEW).apply {
                            data = Uri.parse(it.html_url)
                        }
                        startActivity(intent)
                    }
                }
            }
        }
    }

    private fun addToFavorite(){
        binding.btnFavorite.setOnClickListener {
            _isBookmarked = !_isBookmarked
            if(_isBookmarked){
               favoriteViewModel.postUser(Favorite(
                   args.userGithub.login,
                   args.userGithub.id,
                   args.userGithub.avatar_url,
                   args.userGithub.html_url
               ))
            }else{
                favoriteViewModel.deleteUser(Favorite(
                    args.userGithub.login,
                    args.userGithub.id,
                    args.userGithub.avatar_url,
                    args.userGithub.html_url
                ))
                favoriteViewModel.deleteUserObserver().observe(requireActivity()){
                    binding.btnFavorite.setImageDrawable(ContextCompat.getDrawable(requireActivity(),
                        R.drawable.ic_bookmark))
                }
            }
            binding.btnFavorite.setImageDrawable(ContextCompat.getDrawable(requireActivity(),
                R.drawable.ic_bookmark_checked))
        }
    }

    private fun moveBack(){
        binding.toolbar.setOnClickListener {
            Navigation.findNavController(binding.root).navigate(R.id.listFragment)
        }
    }

    companion object{
        var ARGS = ""
    }
}