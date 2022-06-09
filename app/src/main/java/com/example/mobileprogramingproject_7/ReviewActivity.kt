package com.example.mobileprogramingproject_7

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mobileprogramingproject_7.databinding.ActivityReviewBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class ReviewActivity : AppCompatActivity() {
    lateinit var binding : ActivityReviewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReviewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setLayout()

    }

    private fun setLayout() {
        val centerName = intent.getStringExtra("centerName")!!
        val centerType = intent.getStringExtra("centerType")!!
        binding.writeBtn.setOnClickListener {
            //intent 보내기
            val wrintent = Intent(this, ReviewPopupActivity::class.java)
            wrintent.putExtra("centerName", centerName)
            startActivity(wrintent)
        }

        binding.TitleView.text = centerName

        binding.recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        val reviews = ArrayList<DataReview>()

        val db = Firebase.firestore
        db.collection("Centers")
            .document(centerName)
            .collection("userID")
            .whereEqualTo("centerType", centerType)
            .get()
            .addOnSuccessListener {
                for(document in it){
                    val id = document.id
                    val rv = document.getString("reviewString")
                    if(rv!=null){
                        if(rv!="")  reviews.add(DataReview(UserInfo.userID,rv,id))
                    }
                }
            }
            .addOnFailureListener {
                it.printStackTrace()

            }

        val adapter = RevAdapter(reviews)
        binding.recyclerView.adapter = adapter

    }


}