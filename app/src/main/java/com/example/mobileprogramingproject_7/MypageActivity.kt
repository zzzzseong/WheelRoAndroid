package com.example.mobileprogramingproject_7

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.mobileprogramingproject_7.databinding.ActivityMypageBinding
import com.google.android.material.tabs.TabLayoutMediator
import com.kakao.sdk.user.UserApiClient

// 마이페이지 액티비티 입니다. - 윤섭
class MypageActivity : AppCompatActivity() {
    lateinit var binding: ActivityMypageBinding
    val textarr = arrayListOf<String>("즐겨찾기", "나의 리뷰")
//    val iconarr = arrayListOf<Int>(R.drawable.~~, )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMypageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initLayout()
    }

    private fun initLayout(){
        // 프로필 초기화
        UserApiClient.instance.me { user, error ->
            if(error != null){
                Toast.makeText(this, "사용자 정보 불러오기 실패", Toast.LENGTH_SHORT).show()
            }else if(user != null){
                var url = user.kakaoAccount?.profile?.profileImageUrl

                Glide.with(this)
                    .load(url)
                    .into(binding.profile)
//                Toast.makeText(this, "${user.kakaoAccount?.profile}", Toast.LENGTH_SHORT).show()
                binding.nickname.text = user.kakaoAccount?.profile?.nickname
            }
        }

        // 어댑터 부착
        binding.viewpager.adapter = MypageAdapter(this)
        TabLayoutMediator(binding.tablayout, binding.viewpager){
            tab, position ->
            tab.text = textarr[position]
//            tab.setIcon(iconarr[position]) 아이콘 줄 거면 사용
        }.attach()

        // 로그아웃
        binding.logoutBtn.setOnClickListener {
            // 로그아웃
            //UserApiClient.instance.unlink / logout
            UserApiClient.instance.unlink { error ->
                if (error != null) {
                    Toast.makeText(this, "로그아웃 실패. SDK에서 토큰 삭제됨",Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                }
                else {
                    Toast.makeText(this, "로그아웃 성공. SDK에서 토큰 삭제됨",Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                }
            }
        }
    }
}