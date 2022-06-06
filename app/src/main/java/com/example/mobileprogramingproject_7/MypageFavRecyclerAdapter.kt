package com.example.mobileprogramingproject_7

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mobileprogramingproject_7.databinding.FavRowBinding

//  FavFragment 에 부착하는 Adapter 입니다 - 윤섭
// UserID(여기서는 이메일 의미) 에 해당하는 DataFav 받아서 바인딩 해주는 역할
class MypageFavRecyclerAdapter(
    private val values:List<DataFav>
)
    : RecyclerView.Adapter<MypageFavRecyclerAdapter.ViewHolder>(){

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MypageFavRecyclerAdapter.ViewHolder {
        return ViewHolder(
            FavRowBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MypageFavRecyclerAdapter.ViewHolder, position: Int) {
        holder.favName.text = values[position].favCenterName
    }

    override fun getItemCount(): Int {
        return values.size
    }

    inner class ViewHolder(binding: FavRowBinding) : RecyclerView.ViewHolder(binding.root) {
        val favName: TextView = binding.favName

//        override fun toString(): String {
//            return super.toString() + " '" + contentView.text + "'"
//        }
    }

}