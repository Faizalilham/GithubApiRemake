package com.example.githubapi.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.githubapiremake.databinding.ListFavoriteBinding
import com.example.githubapiremake.model.Favorite
import com.squareup.picasso.Picasso

class FavoriteAdapter(val listener : Clicked):RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>() {


    private val diffUtil = object : DiffUtil.ItemCallback<Favorite>(){
        override fun areItemsTheSame(oldItem: Favorite, newItem: Favorite): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Favorite, newItem: Favorite): Boolean {
            return when{
                oldItem.id != newItem.id -> false
                oldItem.login != newItem.login -> false
                oldItem.imageUrl != newItem.imageUrl-> false
                else -> true
            }
        }
    }

    private var data = AsyncListDiffer(this,diffUtil)
    fun submitData(datas : MutableList<Favorite>) = data.submitList(datas)

    inner class FavoriteViewHolder(val binding : ListFavoriteBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        return FavoriteViewHolder(ListFavoriteBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        holder.binding.apply {
            favorite = data.currentList[position]
            Picasso.with(root.context).load(data.currentList[position].imageUrl).resize(150,150).centerCrop().into(imageUser)
            card.setOnClickListener {
                listener.onClicktoDetail(data.currentList[position])
            }
            urlGithub.setOnClickListener {
                listener.onClicktoSearch(data.currentList[position])
            }

        }
    }

    override fun getItemCount(): Int = data.currentList.size

    interface Clicked{
        fun onClicktoDetail(favorite: Favorite)
        fun onClicktoSearch(favorite: Favorite)
    }
}