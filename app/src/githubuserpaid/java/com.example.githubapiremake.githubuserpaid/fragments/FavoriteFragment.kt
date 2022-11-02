package com.example.githubapiremake.githubuserpaid.fragments

import android.content.Intent
import android.content.res.Configuration
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubapi.adapter.FavoriteAdapter
import com.example.githubapiremake.R
import com.example.githubapiremake.SecondActivity
import com.example.githubapiremake.databinding.FragmentFavoriteBinding
import com.example.githubapiremake.fragments.FavoriteFragmentDirections
import com.example.githubapiremake.model.Favorite
import com.example.githubapiremake.model.UserGithub
import com.example.githubapiremake.room.SetupRoom
import com.example.githubapiremake.viewmodel.FavoriteViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class FavoriteFragment : Fragment() {

    private lateinit var favoriteAdapter: FavoriteAdapter
    private lateinit var favoriteViewModel: FavoriteViewModel
    private val listData : MutableList<Favorite> = mutableListOf()
    private lateinit var binding : FragmentFavoriteBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        favoriteViewModel = ViewModelProvider(requireActivity())[FavoriteViewModel::class.java]
        binding = FragmentFavoriteBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setDataToRecycler()
        setRecycler()

    }

    private fun setDataToRecycler(){
        favoriteViewModel.allFavorite()
        favoriteViewModel.allFavoriteObserver().observe(requireActivity()){
            if(it != null){
                favoriteAdapter.submitData(it)
            }
        }
    }

    private fun setRecycler(){
        favoriteAdapter = FavoriteAdapter(object : FavoriteAdapter.Clicked{
            override fun onClicktoDetail(favorite: Favorite) {
                val user = UserGithub(favorite.login,favorite.id,favorite.imageUrl,favorite.html_url)
                val args = FavoriteFragmentDirections.actionFavoriteFragmentToDetailFragment(user)
                val navHostFragment = childFragmentManager.findFragmentById(R.id.secondFragmentContainerView) as NavHostFragment
                val navController = navHostFragment.navController
                navController.navigate(args)
            }

            override fun onClicktoSearch(favorite: Favorite) {
                val intent = Intent(Intent.ACTION_VIEW).apply {
                    data = Uri.parse(favorite.html_url)
                }
                startActivity(intent)
            }
        })
        binding.apply {
            recyclerFavorite.apply {
                adapter = favoriteAdapter
                layoutManager = if(context.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE){
                    GridLayoutManager(requireActivity(),2)
                }else{
                    LinearLayoutManager(requireActivity())
                }
            }
            toolbar.setOnClickListener {
                startActivity(Intent(requireActivity(),SecondActivity::class.java).also{activity?.finish()})
            }
        }
    }

    companion object{
        const val DATA_DETAIL_FAVORITE = "data_detail_favorite"
    }



}