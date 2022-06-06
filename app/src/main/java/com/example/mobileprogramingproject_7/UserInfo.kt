package com.example.mobileprogramingproject_7

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

object UserInfo {

    val userID = "jisukelly9@naver.com"
    //나중에 카카오 API에서 가져오는걸로 바꿔야함

    lateinit var db : FirebaseFirestore
    val userRVList = ArrayList<DataReview>()
    val userFavList = ArrayList<DataFav>()

    fun getListdata() {
        db = Firebase.firestore
        db.collection("Users").document(userID).collection("review")
            .get()
            .addOnSuccessListener {
                for(document in it){
                    val centerName = document.id
                    val type = document.getString("centerType")
                    val str = document.getString("reviewString")!!
                    userRVList.add(DataReview(userID,str, centerName))
                }
            }
            .addOnFailureListener {
                Log.d("logger","GET USER REVIEW DATA FAILED")
                it.printStackTrace()
            }

        db.collection("Users").document(userID).collection("favorite")
            .get()
            .addOnSuccessListener {
                for(document in it){
                    val centerName = document.id
                    val type = document.getString("centerType")!!
                    userFavList.add(DataFav(userID,centerName,type))
                }
            }.addOnFailureListener {
                Log.d("logger","GET USER FAV DATA FAILED")
                it.printStackTrace()
            }
    }


    /* DB사용법 - https://firebase.google.com/docs/firestore/quickstart?hl=ko
val db = Firebase.firestore //종속돼있는 DB오픈
binding.apply {
    submitBtn.setOnClickListener {
        val user = hashMapOf( //저장할 데이터 정의
            "id" to userID.text.toString(),
            "pw" to userPW.text.toString()
        )
        db.collection("users").document(userID.text.toString()) //컬렉션에 데이터 추가
            .set(user)
            .addOnSuccessListener {
                Log.d(ContentValues.TAG, "DocumentSnapshot successfully written")
            }
            .addOnFailureListener { e ->
                Log.w(ContentValues.TAG, "Error adding document", e)
            }
    }
}
db.collection("users") //users컬렉션 데이터 가져오기
.get()
.addOnSuccessListener { result ->
    for (document in result) {
        Log.d(ContentValues.TAG, "${document.id} => ${document.data}")
    }
}
.addOnFailureListener { exception ->
    Log.w(ContentValues.TAG, "Error getting documents.", exception)
} */
}