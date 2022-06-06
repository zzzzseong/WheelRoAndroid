package com.example.mobileprogramingproject_7

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.widget.addTextChangedListener
import com.example.mobileprogramingproject_7.databinding.ActivityCentermapBinding
import com.example.mobileprogramingproject_7.databinding.ActivityWheelchairmapBinding
import com.google.android.gms.location.*
import net.daum.mf.map.api.MapPOIItem
import net.daum.mf.map.api.MapPoint


class CenterMapActivity : AppCompatActivity() {
    lateinit var binding: ActivityCentermapBinding

    private var mFusedLocationProviderClient: FusedLocationProviderClient? = null // 현재 위치를 가져오기 위한 변수
    lateinit var mLastLocation: Location // 위치 값을 가지고 있는 객체
    internal lateinit var mLocationRequest: LocationRequest // 위치 정보 요청의 매개변수를 저장하는
    private val REQUEST_PERMISSION_LOCATION = 10

    private var changeLatitude = 0.0
    private var changeLongitude = 0.0
    private var myLatitude = 0.0
    private var myLongitude = 0.0



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCentermapBinding.inflate(layoutInflater)
        setContentView(binding.root)

        startMap()
        dataMark()
        initLayout()

    }

    private fun initLayout(){
        val manager = DataManager()
        Thread.sleep(3000L)
        val namearr =  mutableListOf<String>()
        for (data in manager.center) {
            namearr.add( data.tfcwkerMvmnCnterNm)
        }

        val adapter = ArrayAdapter<String>(
            this,
            android.R.layout.simple_dropdown_item_1line,
            namearr
        )
        val autoCompleteTextView = binding.autoCompleteTextView

        autoCompleteTextView.setAdapter(adapter)
        val button = binding.button
        button.setOnClickListener{
            for (data in manager.center) {
                if(data.tfcwkerMvmnCnterNm == autoCompleteTextView.text.toString()){
                    changeLatitude  = data.latitude.toDouble()
                    changeLongitude =data.longitude.toDouble()
                    val mapPoint = MapPoint.mapPointWithGeoCoord(changeLatitude, changeLongitude)
                    binding.mapView.setMapCenterPointAndZoomLevel(mapPoint, 1, true)
                }

            }
            val url : String ="kakaomap://route?sp=$myLatitude,$myLongitude&ep=$changeLatitude,$changeLongitude&by=FOOT"
            var intent =  Intent(Intent.ACTION_VIEW, Uri.parse(url))
            intent.addCategory(Intent.CATEGORY_BROWSABLE)
            var list = packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY)
            //startActivity(intent)
            if (list.equals("") && list.isEmpty()){
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=net.daum.android.map")))
            }else{
                startActivity(intent)
            }

        }



    }
    //데이터 마크표시
    private fun dataMark(){

        val manager = DataManager()
        Thread.sleep(3000L)


        val markerArr = ArrayList<MapPOIItem>()
        for (data in manager.center) {
            val marker = MapPOIItem()
            marker.mapPoint = MapPoint.mapPointWithGeoCoord(data.latitude.toDouble(), data.longitude.toDouble())
            marker.itemName = data.tfcwkerMvmnCnterNm
            markerArr.add(marker)
        }
        binding.mapView.addPOIItems(markerArr.toArray(arrayOfNulls<MapPOIItem>(markerArr.size)))

    }

    private fun startMap(){
        mLocationRequest =  LocationRequest.create().apply {
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }

        if (checkPermissionForLocation(this)) {
            startLocationUpdates()
        }
    }

    private fun startLocationUpdates() {

        //FusedLocationProviderClient의 인스턴스를 생성.
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return
        }
        // 기기의 위치에 관한 정기 업데이트를 요청하는 메서드 실행
        // 지정한 루퍼 스레드(Looper.myLooper())에서 콜백(mLocationCallback)으로 위치 업데이트를 요청
        mFusedLocationProviderClient!!.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper()!!
        )
    }

    // 시스템으로 부터 위치 정보를 콜백으로 받음
    private val mLocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            // 시스템에서 받은 location 정보를 onLocationChanged()에 전달
            locationResult.lastLocation
            startTracking(locationResult.lastLocation)
        }
    }


    // 위치 권한이 있는지 확인하는 메서드
    private fun checkPermissionForLocation(context: Context): Boolean {
        // Android 6.0 Marshmallow 이상에서는 위치 권한에 추가 런타임 권한이 필요
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (context.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                true
            } else {
                // 권한이 없으므로 권한 요청 알림 보내기
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_PERMISSION_LOCATION)
                false
            }
        } else {
            true
        }
    }

    // 사용자에게 권한 요청 후 결과에 대한 처리 로직
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_PERMISSION_LOCATION) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startLocationUpdates()

            } else {
                Log.d("ttt", "onRequestPermissionsResult() _ 권한 허용 거부")
                Toast.makeText(this, "권한이 없어 해당 기능을 실행할 수 없습니다.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // 위치추적 시작
    private fun startTracking(location: Location) {
        mLastLocation = location
        myLatitude = mLastLocation.latitude
        myLongitude =  mLastLocation.longitude
        val mapPoint = MapPoint.mapPointWithGeoCoord(mLastLocation.latitude, mLastLocation.longitude)
        binding.mapView.setMapCenterPointAndZoomLevel(mapPoint, 1, true)
        val uNowPosition = MapPoint.mapPointWithGeoCoord(mLastLocation.latitude!!, mLastLocation.longitude!!)

        // 현 위치에 마커 찍기
        val marker = MapPOIItem()
        marker.itemName = "현 위치"
        marker.mapPoint =uNowPosition
        marker.markerType = MapPOIItem.MarkerType.BluePin
        marker.selectedMarkerType = MapPOIItem.MarkerType.RedPin
        binding.mapView.addPOIItem(marker)
    }


}

