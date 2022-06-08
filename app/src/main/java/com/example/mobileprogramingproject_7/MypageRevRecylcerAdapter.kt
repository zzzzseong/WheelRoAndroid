package com.example.mobileprogramingproject_7

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mobileprogramingproject_7.databinding.RevRowBinding

// Review Fragment 에 부착하는 Adapter 입니다 - 윤섭
// UserID(여기서는 이메일 의미) 에 해당하는 DataReview 받아서 바인딩 해주는 역할
class MypageRevRecylcerAdapter(
    private val values:ArrayList<DataReview>
)
    : RecyclerView.Adapter<MypageRevRecylcerAdapter.ViewHolder>(){

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MypageRevRecylcerAdapter.ViewHolder {
            return ViewHolder(
                RevRowBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
    }

    override fun onBindViewHolder(holder: MypageRevRecylcerAdapter.ViewHolder, position: Int) {
        holder.id.text = values[position].userID
        holder.review.text = values[position].review
    }

    override fun getItemCount(): Int {
        return values.size
    }

    inner class ViewHolder(binding: RevRowBinding) : RecyclerView.ViewHolder(binding.root) {
//        val image: ImageView = binding.revPrfImgView
        val id:TextView = binding.revId
        val review:TextView = binding.revStr

//        override fun toString(): String {
//            return super.toString() + " '" + contentView.text + "'"
//        }
    }

}