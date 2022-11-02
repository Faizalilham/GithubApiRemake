package com.example.githubapi.githubuserpaid.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.githubapiremake.databinding.ListItemBinding
import com.example.githubapiremake.model.UserGithub
import com.squareup.picasso.Picasso

class UserGithubAdapter(private val listener : Clicked):RecyclerView.Adapter<UserGithubAdapter.UserGithubViewHolder>() {

    private val diffUtil = object : DiffUtil.ItemCallback<UserGithub>(){
        override fun areItemsTheSame(oldItem: UserGithub, newItem: UserGithub): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: UserGithub, newItem: UserGithub): Boolean {
            return when{
                oldItem.id != newItem.id -> false
                oldItem.login != newItem.login -> false
                oldItem.avatar_url != newItem.avatar_url -> false
                else -> true
            }
        }
    }

    private var data = AsyncListDiffer(this,diffUtil)
    fun submitData(datas : MutableList<UserGithub>) = data.submitList(datas)

    inner class UserGithubViewHolder(val binding : ListItemBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserGithubViewHolder {
       return UserGithubViewHolder(ListItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: UserGithubViewHolder, position: Int) {
       holder.binding.apply {
           userGithub = data.currentList[position]
           Picasso.with(root.context).load(data.currentList[position].avatar_url).resize(150,150).centerCrop().into(imageUser)
           card.setOnClickListener {
               listener.onClicktoDetail(data.currentList[position])
           }
           urlGithub.setOnClickListener {
               listener.onClicktoSearch(data.currentList[position])
           }

       }
    }

    override fun getItemCount(): Int  = data.currentList.size

    interface Clicked{
        fun onClicktoDetail(userGithub: UserGithub)
        fun onClicktoSearch(userGithub: UserGithub)
    }

}