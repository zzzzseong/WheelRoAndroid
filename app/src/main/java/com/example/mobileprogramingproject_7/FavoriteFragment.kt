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
class FavoriteFragment : Fragment() {

    val myFavList = arrayListOf<DataFav>()
//    lateinit var UserID:String
//    lateinit var UserNick:String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_favorite, container, false)

        // 사용자 정보 요청 (기본)
        UserApiClient.instance.me { user, error ->
            if (error != null) {
//                Toast.makeText(activity, "사용자 정보 불러오기 실패", Toast.LENGTH_SHORT).show()
                Log.e(ContentValues.TAG, "사용자 정보 요청 실패", error)
            }
            else if (user != null) {
                // User 이메일 받아오기
//                Toast.makeText(activity, "사용자 정보 불러오기 성공", Toast.LENGTH_SHORT).show()
                val UserID = user.kakaoAccount?.email.toString()
                val UserNick = user.kakaoAccount?.profile?.nickname.toString()
//                Toast.makeText(context, "${UserID}", Toast.LENGTH_SHORT).show()

                // DB에서 UserID(이메일)와 일치하는 리뷰들 가져오기 // 코드 추가 필요
                // ArrayList 에 해당 유저 리뷰 정보 담아 넘기기
                val db = Firebase.firestore
                val documents = db.collection("Users")
                    .document(UserID)
                    .collection("favorite")
//            .whereEqualTo("${UserID}", true)
                    .get()
                    .addOnSuccessListener { documents ->
                        for (document in documents) {
//                    Log.d(ContentValues.TAG, "${document.id} => ${document.data}")
                            myFavList.add(DataFav(UserNick, document.get("centerName").toString(), document.get("centerType").toString()))
                        }
                    }
                    .addOnFailureListener { exception ->
                        Log.w(ContentValues.TAG, "Error getting documents: ", exception)
                    }
            }
        }

        myFavList.add(DataFav("유저1", "센터1", "센터유형1"))
        myFavList.add(DataFav("유저2", "센터2", "센터유형2"))
        myFavList.add(DataFav("유저3", "센터3", "센터유형3"))
        myFavList.add(DataFav("유저4", "센터4", "센터유형4"))
        myFavList.add(DataFav("유저5", "센터5", "센터유형5"))
        myFavList.add(DataFav("유저6", "센터6", "센터유형6"))
        myFavList.add(DataFav("유저7", "센터7", "센터유형7"))
        myFavList.add(DataFav("유저8", "센터8", "센터유형8"))
        myFavList.add(DataFav("유저9", "센터9", "센터유형9"))
        myFavList.add(DataFav("유저10", "센터10", "센터유형10"))
        myFavList.add(DataFav("유저1", "센터1", "센터유형1"))
        myFavList.add(DataFav("유저2", "센터2", "센터유형2"))
        myFavList.add(DataFav("유저3", "센터3", "센터유형3"))
        myFavList.add(DataFav("유저4", "센터4", "센터유형4"))
        myFavList.add(DataFav("유저5", "센터5", "센터유형5"))
        myFavList.add(DataFav("유저6", "센터6", "센터유형6"))
        myFavList.add(DataFav("유저7", "센터7", "센터유형7"))
        myFavList.add(DataFav("유저8", "센터8", "센터유형8"))
        myFavList.add(DataFav("유저9", "센터9", "센터유형9"))
        myFavList.add(DataFav("유저10", "센터10", "센터유형10"))

        // Set the adapter
        if (view is RecyclerView) {
            with(view) {
                layoutManager = LinearLayoutManager(context)
                adapter = MypageFavRecyclerAdapter(myFavList) // arrayList 타입 생성자 생성해야함
            }
        }

        // Inflate the layout for this fragment
        return view
    }

}