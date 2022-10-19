package com.example.githubapiremake.fragments

import android.content.Intent
import android.content.res.Configuration
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubapi.adapter.FavoriteAdapter
import com.example.githubapiremake.R
import com.example.githubapiremake.databinding.FragmentFavoriteBinding
import com.example.githubapiremake.model.Favorite
import com.example.githubapiremake.model.UserGithub
import com.example.githubapiremake.room.SetupRoom
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class FavoriteFragment : Fragment() {

    private lateinit var favoriteAdapter: FavoriteAdapter
    private val db by lazy{
        SetupRoom(requireActivity())
    }
    private val listData : MutableList<Favorite> = mutableListOf()

    private lateinit var binding : FragmentFavoriteBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoriteBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setDataToRecycler()
        setRecycler()

    }

    private fun setDataToRecycler(){
        CoroutineScope(Dispatchers.IO).launch {
            val datax = db.daoFavorite().getAllFavorite()
            withContext(Dispatchers.Main){
                listData.addAll(datax)
                favoriteAdapter.submitData(listData)
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
        binding.recyclerFavorite.apply {
            adapter = favoriteAdapter
            layoutManager = if(context.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE){
                GridLayoutManager(requireActivity(),2)
            }else{
                LinearLayoutManager(requireActivity())
            }
        }
    }

    companion object{
        const val DATA_DETAIL_FAVORITE = "data_detail_favorite"
    }



}