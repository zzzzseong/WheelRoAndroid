package com.example.mobileprogramingproject_7

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.mobileprogramingproject_7.databinding.ActivityReviewPopupBinding

class ReviewPopupActivity : AppCompatActivity() {
    lateinit var binding : ActivityReviewPopupBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReviewPopupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initLayout()

    }

    private fun initLayout() {
        binding.apply {
            saveBtn.setOnClickListener {
                //db에 저장 or 수정
                val reviewStr = reviewText.text

            }
            cancelBtn.setOnClickListener {
                //나가기

            }
        }
    }
}