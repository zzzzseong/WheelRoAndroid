package com.example.mobileprogramingproject_7

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.mobileprogramingproject_7.UserInfo.db
import com.example.mobileprogramingproject_7.databinding.ActivityCenterBinding
import com.google.firebase.firestore.SetOptions

class CenterActivity : AppCompatActivity() {
    lateinit var binding : ActivityCenterBinding
    lateinit var centerName : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCenterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setDataLayout()
//        setRecyclerView()
        setOnClick()

    }

    private fun setOnClick() {
        binding.apply {
            startBtn.setOnClickListener {

            }

            favBtn.setOnClickListener {
                db.collection("Users")
                    .document(UserInfo.userID)
                    .collection("favorite")
                    .document(centerName)
                    .get()
                    .addOnSuccessListener {
                        if(it==null){
                            addFav()
                        }else{
                            delFav()
                        }
                    }.addOnFailureListener {
                        it.printStackTrace()
                    }


            }

            arriveBtn.setOnClickListener {

            }
        }


        binding.reviewBtn.setOnClickListener {
            val rvintent = Intent(this, ReviewActivity::class.java)
            rvintent.putExtra("centerName", centerName)
            rvintent.putExtra("centerType", "Center")
            startActivity(rvintent)
        }
    }


    private fun setDataLayout() {
        //인텐트로 DataWheelchair객체 넘김
        //val data = intent.getSerializableExtra("center") as DataCenter

        Thread.sleep(3000L)
        val data = DataManager.center[0]

        centerName = data.tfcwkerMvmnCnterNm

        binding.apply {
            TitleView.text = data.tfcwkerMvmnCnterNm
            AddrView.text = data.rdnmadr
            PhoneView.text = data.phoneNumber
            trgetView.text = data.useTrget
            chargeView.text = data.useCharge

            var rtime = "평   일 "+data.weekdayRceptOpenHhmm+" ~ "+data.weekdayRceptColseHhmm+"\n"
            rtime+="주  말 "+data.wkendRceptOpenHhmm+" ~ "+data.wkendRceptCloseHhmm
            rcepttimeView.text = rtime

            var timestr = "평  일 "+data.weekdayOperOpenHhmm+" ~ "+data.weekdayOperColseHhmm+"\n"
            timestr+="주  말 "+data.wkendOperOpenHhmm+" ~ "+data.wkendOperCloseHhmm
            timeView.text = timestr

            var areastr = "관내 " +data.outsideOpratArea+"\n관외 "+data.insideOpratArea
            areaView.text = areastr

            callBtn.setOnClickListener {
                val intentCall = Intent(Intent.ACTION_CALL, Uri.parse(data.phoneNumber))
                startActivity(intentCall)
            }

        }

    }


    fun addFav(){
        val fav = hashMapOf(
            "favorite" to false,
            "centerType" to "Center",
            "centerName" to centerName
        )

        UserInfo.db.collection("Centers").document(centerName)
            .collection("userID")
            .document(UserInfo.userID)
            .set(fav, SetOptions.merge())
            .addOnSuccessListener {
                Log.d("logger", "review saved to center DB")
            }
            .addOnFailureListener { e ->
                Log.w("logger", "Error adding document", e)
            }

        UserInfo.db.collection("Users").document(UserInfo.userID)
            .collection("favorite")
            .document(centerName)
            .set(fav, SetOptions.merge())
            .addOnSuccessListener {
                Log.d("logger", "review saved to user DB")
                Toast.makeText(applicationContext, "즐겨찾기에 등록되었습니다",Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener { e ->
                Log.w("logger", "Error adding document", e)
            }
    }

    fun delFav(){
        val fav = hashMapOf(
            "favorite" to false,
            "centerType" to "Center",
            "centerName" to centerName
        )

        UserInfo.db.collection("Centers").document(centerName)
            .collection("userID")
            .document(UserInfo.userID)
            .set(fav, SetOptions.merge())
            .addOnSuccessListener {
                Log.d("logger", "review saved to center DB")
            }
            .addOnFailureListener { e ->
                Log.w("logger", "Error adding document", e)
            }

        UserInfo.db.collection("Users").document(UserInfo.userID)
            .collection("favorite")
            .document(centerName)
            .delete()
            .addOnSuccessListener {
                Log.d("logger", "review saved to user DB")
                Toast.makeText(applicationContext, "즐겨찾기에서 해제되었습니다",Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener { e ->
                Log.w("logger", "Error adding document", e)
            }
    }



}