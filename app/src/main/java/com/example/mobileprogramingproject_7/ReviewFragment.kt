package com.example.mobileprogramingproject_7

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.kakao.sdk.user.UserApiClient

// 마이페이지 내 리뷰 관련 데이터. 리사이클러뷰로
// onCreate 시 UserID(이메일) 카카오에서 받아서
// DB 탐색하여 해당 이메일과 같은 데이터의 리뷰 뽑아서 DataReview arraylist에 add
// 이후 리사이클러뷰 어댑터에 전달하여 부착
class ReviewFragment : Fragment() {

    val myReviewList = arrayListOf<DataReview>()
//    lateinit var UserID:String
//    lateinit var UserNick:String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_review, container, false)

        // 사용자 정보 요청 (기본)
//        val UserInfo = UserApiClient.instance.me { user, error ->
        UserApiClient.instance.me { user, error ->
            if (error != null) {
                Log.e(ContentValues.TAG, "사용자 정보 요청 실패", error)
            }
            else if (user != null) {
                Log.i(ContentValues.TAG, "사용자 정보 요청 성공")
                // User 이메일 받아오기
                var UserID = user.kakaoAccount?.email.toString()
                var UserNick = user.kakaoAccount?.profile?.nickname.toString()
//                Toast.makeText(context, "${UserID}", Toast.LENGTH_SHORT).show()

                // DB에서 UserID(이메일)와 일치하는 리뷰들 가져오기 // 코드 추가 필요
                // ArrayList 에 해당 유저 리뷰 정보 담아 넘기기
                val db = Firebase.firestore
                val documents = db.collection("Users")
                    .document(UserID) // "${UserID}"
//                    .whereEqualTo("", )
                    .collection("review")
//            .whereEqualTo("${UserID}", true)
                    .get()
                    .addOnSuccessListener { documents ->
                        for (document in documents) {
//                    Log.d(ContentValues.TAG, "${document.id} => ${document.data}")
                            // Recycler View 상에는 Nickname 으로 표시
                            myReviewList.add(DataReview(UserNick, document.get("centerType").toString(), document.get("reviewString").toString()))
                        }
                    }
                    .addOnFailureListener { exception ->
                        Log.w(ContentValues.TAG, "Error getting documents: ", exception)
                    }
            }
        }
        myReviewList.add(DataReview("유저1", "리뷰1", "센터1"))
        myReviewList.add(DataReview("유저2", "리뷰2", "센터2"))
        myReviewList.add(DataReview("유저3", "리뷰3", "센터3"))
        myReviewList.add(DataReview("유저4", "리뷰4", "센터4"))
        myReviewList.add(DataReview("유저5", "리뷰5", "센터5"))
        myReviewList.add(DataReview("유저6", "리뷰6", "센터6"))
        myReviewList.add(DataReview("유저7", "리뷰7", "센터7"))
        myReviewList.add(DataReview("유저8", "리뷰8", "센터8"))
        myReviewList.add(DataReview("유저9", "리뷰9", "센터9"))
        myReviewList.add(DataReview("유저10", "리뷰10", "센터10"))
        myReviewList.add(DataReview("유저1", "리뷰1", "센터1"))
        myReviewList.add(DataReview("유저2", "리뷰2", "센터2"))
        myReviewList.add(DataReview("유저3", "리뷰3", "센터3"))
        myReviewList.add(DataReview("유저4", "리뷰4", "센터4"))
        myReviewList.add(DataReview("유저5", "리뷰5", "센터5"))
        myReviewList.add(DataReview("유저6", "리뷰6", "센터6"))
        myReviewList.add(DataReview("유저7", "리뷰7", "센터7"))
        myReviewList.add(DataReview("유저8", "리뷰8", "센터8"))
        myReviewList.add(DataReview("유저9", "리뷰9", "센터9"))
        myReviewList.add(DataReview("유저10", "리뷰10", "센터10"))


        // Set the adapter
        if (view is RecyclerView) {
            with(view) {
                layoutManager = LinearLayoutManager(context)
                adapter = MypageRevRecylcerAdapter(myReviewList) // arrayList 타입 생성자 생성해야함
            }
        }

        // Inflate the layout for this fragment
        return view
    }

}