package com.example.mobileprogramingproject_7

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import com.example.mobileprogramingproject_7.databinding.RevRowBinding


class RevAdapter(val reviews : ArrayList<DataReview>) : RecyclerView.Adapter<RevAdapter.ViewHolder>() {


    inner class ViewHolder(val binding: RevRowBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RevRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.revId.text = reviews[position].userID
        holder.binding.revStr.text = reviews[position].review
    }

    override fun getItemCount(): Int {
        return reviews.size
    }

}
