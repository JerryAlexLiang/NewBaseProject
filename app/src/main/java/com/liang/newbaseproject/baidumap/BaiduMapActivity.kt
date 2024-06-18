package com.liang.newbaseproject.baidumap

import android.content.Intent
import android.os.Build
import android.os.Bundle
import com.baidu.location.BDAbstractLocationListener
import com.baidu.location.BDLocation
import com.baidu.location.LocationClient
import com.baidu.location.LocationClientOption
import com.baidu.mapapi.map.BaiduMap
import com.baidu.mapapi.map.MapStatus
import com.baidu.mapapi.map.MapStatusUpdateFactory
import com.baidu.mapapi.map.MapView
import com.baidu.mapapi.map.MyLocationData
import com.baidu.mapapi.model.LatLng
import com.liang.module_base.base.BaseActivity
import com.liang.module_base.base.PermissionActivity
import com.liang.module_base.constant.Constants.Companion.PERMISSION_DENIEG
import com.liang.module_base.utils.CheckPermissionKt
import com.liang.module_base.utils.GsonUtils
import com.liang.module_base.utils.LogUtils
import com.liang.newbaseproject.R
import com.liang.newbaseproject.databinding.ActivityBaiduMapBinding


@Suppress("DEPRECATION")
class BaiduMapActivity : BaseActivity<ActivityBaiduMapBinding>() {

    private lateinit var builder: MapStatus.Builder
    private lateinit var mCheckPermission: CheckPermissionKt
    private lateinit var mLocationClient: LocationClient
    private lateinit var mBaiduMap: BaiduMap
    private var mapView: MapView? = null

    //权限请求码
    private val REQUEST_CODE = 0

    // 是否是第一次定位
    private var isFirstLocate = true

    private var permissionInfo: String? = null

    // 请求定位权限
//    val permissions = listOf(
//        MapConstant.PERMISSION_FINE_LOCATION,
////            MapConstant.PERMISSION_COARSE_LOCATION,
//    )

    val permissions: ArrayList<String> = arrayListOf(MapConstant.PERMISSION_FINE_LOCATION)

    private var isRequestCheck = false //判断是否需要系统权限检测。防止和系统提示框重叠
    private val PERMISSION_REQUEST_CODE = 0 //系统授权管理页面时的结果参数

    override fun getLayoutId(): Int {
        return R.layout.activity_baidu_map
    }

    override fun initView(savedInstanceState: Bundle?) {

        isRequestCheck = true //改变检测状态

        mapView = mBinding.baiduMapView

        mBaiduMap = mapView!!.map

        // 设置地图放大的倍数
        setMapZoom()

        initMap()

        // 检查定位权限
        initPermission()

//        // 检查定位权限
//        if (!PermissionUtils.checkPermissionsGranted(this, permissions)) {
//            // 请求定位
//            requestLocationPermissions()
//        } else {
//            // 权限已经被授予，执行定位业务逻辑
//            initLocationListener()
//        }

//        // 权限已经被授予，执行定位业务逻辑
//        initLocationListener()
    }

    private fun initPermission() {
        ////SDK版本小于23时候不做检测
        //        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M)
        //            return;
        //        mCheckPermission = new CheckPermission(this);
        //        //缺少权限时，进入权限设置页面
        //        if (mCheckPermission.permissionSet(PERMISSION)) {
        //            startPermissionActivity();
        //        }

        //SDK版本小于23时候不做检测

        //SDK版本小于23时候不做检测
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) return
        mCheckPermission = CheckPermissionKt(this)
        //缺少权限时，进入权限设置页面
        if (mCheckPermission.permissionSet(permissions)) {
            startPermissionActivity()
        }else{
            // 请求定位
            initLocationListener()
        }
    }

    private fun startPermissionActivity() {
        PermissionActivity.startActivityForResult(this, REQUEST_CODE, permissions)
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE) {
            //权限请求码
            if (resultCode == PERMISSION_DENIEG) {
                //拒绝时，没有获取到主要权限，无法运行，关闭页面
                finish()
            } else {
                // 请求定位
                initLocationListener()
            }
        }
    }

    private fun initLocationListener() {
        //注册LocationListener监听器
        val myLocationListener = MyLocationListener()
        mLocationClient.registerLocationListener(myLocationListener)
        //开启地图定位图层
        mLocationClient.start()
    }

    private fun initMap() {
        // 开启地图的定位图层
        mBaiduMap.isMyLocationEnabled = true

        // 通过LocationClient发起定位
        // 定位初始化
        mLocationClient = LocationClient(this)
        // 通过LocationClientOption设置LocationClient相关参数
        val option = LocationClientOption()
        // 打开gps
        option.isOpenGps = true
        // 可选，设置是否使用卫星定位，默认false
        // 使用高精度和仅用设备两种定位模式的，参数必须设置为true
        option.isOpenGnss = true
        // 设置坐标类型
        option.setCoorType("bd09ll")
        // 可选，设置发起定位请求的间隔，int类型，单位ms
        // 如果设置为0，则代表单次定位，即仅定位一次，默认为0
        // 如果设置非0，需设置1000ms以上才有效
        option.setScanSpan(1000);
        // 可选，是否需要地址信息，默认为不需要，即参数为false
        option.setIsNeedAddress(true)
        // 可选，设置是否需要最新版本的地址信息
        option.setNeedNewVersionRgc(true)
        //可选，设置是否需要地址描述
        option.setIsNeedLocationDescribe(true)
        // 可选，设置是否当卫星定位有效时按照1S/1次频率输出卫星定位结果，默认false
        option.isLocationNotify = true

        // 设置locationClientOption
        // mLocationClient为第二步初始化过的LocationClient对象
        // 需将配置好的LocationClientOption对象，通过setLocOption方法传递给LocationClient对象使用
        mLocationClient.locOption = option

        // 指南针 指南针默认为开启状态
        val uiSettings = mBaiduMap.uiSettings
        uiSettings.isCompassEnabled = true

        // 通过设置enable为true或false 选择是否显示比例尺
        mBinding.baiduMapView.showScaleControl(true)

        // 通过设置enable为true或false 选择是否显示缩放按钮
        mBinding.baiduMapView.showZoomControls(false)

        // 通过设置enable为true或false 选择是否启用地图平移
        uiSettings.isScrollGesturesEnabled = true

        // 通过设置enable为true或false 选择是否启用地图缩放手势
        uiSettings.isZoomGesturesEnabled = true

        // 通过设置enable为true或false 选择是否启用地图俯视功能
        uiSettings.isOverlookingGesturesEnabled = true

        // 通过设置enable为true或false 选择是否启用地图旋转功能
        uiSettings.isRotateGesturesEnabled = true

        // 通过设置enable为true或false 选择是否禁用所有手势
        uiSettings.setAllGesturesEnabled(true)

        // 是否双击放大当前地图中心点 默认：false 即按照双击位置放大地图
        uiSettings.setEnlargeCenterWithDoubleClickEnable(false)
    }

    private fun setMapZoom() {
//        builder = MapStatus.Builder()
//        // 缩放两公里
//        builder.zoom(13f)
//        mBaiduMap.setMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()))
    }

    // 通过继承抽象类BDAbstractListener并重写其onReceieveLocation方法来获取定位数据，并将其传给MapView
    inner class MyLocationListener : BDAbstractLocationListener() {
        override fun onReceiveLocation(location: BDLocation) {
            // 要在第一次定位时定位到当前位置 如果是第一次定位
            val latLng = LatLng(location.latitude, location.longitude)
//            if (isFirstLocate) {
//                isFirstLocate = false
//                // 给地图设置状态
//                mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newLatLng(latLng))
//            }


            mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newLatLng(latLng))
            // 给地图设置状态
            setMapZoom()

            // mapView 销毁后不在处理新接收的位置
            val locData = MyLocationData.Builder()
                .accuracy(location.radius) // 此处设置开发者获取到的方向信息，顺时针0-360
                .direction(location.direction)
                .latitude(location.latitude)
                .longitude(location.longitude).build()

            LogUtils.d(tag = "MyLocationListener", msg = GsonUtils.toJson(locData))
            mBaiduMap.setMyLocationData(locData)

            val latitude = location.latitude
            val longitude = location.longitude
            val country = location.country       // 获取国家
            val province = location.province     // 获取省份
            val city = location.city             // 获取城市
            val district = location.district     // 获取区县
            val street = location.street         // 获取街道信息
            val town = location.town             // 获取街道信息

            val locationDescribe = location.locationDescribe //获取位置描述信息


            LogUtils.d(
                tag = "MyLocationListenerAddress",
                msg = "latitude: $latitude latitude:$latitude $country$province$city$district$street$town locationDescribe:$locationDescribe"
            )

        }
    }

//    private fun requestLocationPermissions() {
//        val locationPermissions = listOf(Manifest.permission.ACCESS_FINE_LOCATION)
//        PermissionUtils.requestPermissions(
//            this,
//            locationPermissions,
//            MapConstant.LOCATION_PERMISSIONS_REQUEST
//        )
//        LogUtils.d(tag = "requestLocationPermissions", msg = "请求权限")
//    }

//    override fun onResume() {
//        // 在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
//        mapView?.onResume()
//
//        if (isRequestCheck) {
//            if (!PermissionUtils.checkPermissionsGranted(this, permissions)) {
//                // 请求定位
//                requestLocationPermissions()
//            } else {
//                // 权限已经被授予，执行定位业务逻辑
//                initLocationListener()
//            }
//        } else {
//            isRequestCheck = true
//        }
//        super.onResume()
//    }

    override fun onResume() {
        // 在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mapView?.onResume()
        super.onResume()
    }

    override fun onPause() {
        // 在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mapView?.onPause()
        super.onPause()
    }

    override fun onDestroy() {
        mLocationClient.stop()
        mBaiduMap.isMyLocationEnabled = false
        // 在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mapView?.onDestroy()
        mapView = null
        super.onDestroy()
    }

    //    override fun onRequestPermissionsResult(
//        requestCode: Int,
//        permissions: Array<out String>,
//        grantResults: IntArray
//    ) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//        if (requestCode == MapConstant.LOCATION_PERMISSIONS_REQUEST) {
//            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
//                // 权限被授予
//                // 执行需要定位权限的操作
//                initLocationListener()
//            } else {
//                // 权限被拒绝
//                // 你可以向用户解释为什么需要这个权限，或者禁用依赖于这个权限的功能
////                ToastUtil.showFailToast(this, true, "缺少必要权限")
//                showMissingPermissionDialog()
//                LogUtils.d(tag = "requestLocationPermissions", msg = "缺少必要权限")
//
//            }
//        } else if (PERMISSION_REQUEST_CODE == requestCode && PermissionUtils.hasAllPermissionGranted(
//                grantResults
//            )
//        ) {
//            isRequestCheck = true //需要检测权限，直接进入，否则提示对话框进行设置
//            initLocationListener()
//        } else {
//            //提示对话框设置
//            isRequestCheck = false
//            showMissingPermissionDialog()
//        }
//    }

//    //显示对话框提示用户缺少权限
//    private fun showMissingPermissionDialog() {
//        val builder = AlertDialog.Builder(this)
//        builder.setTitle(com.liang.module_base.R.string.help) //提示帮助
//        builder.setMessage(com.liang.module_base.R.string.string_help_text2)
//
//        //如果是拒绝授权，则退出应用
//        //退出
//        builder.setNegativeButton(
//            com.liang.module_base.R.string.quit
//        ) { _, _ ->
//            // 权限不足
//            finish()
//        }
//        //打开设置，让用户选择打开权限
//        builder.setPositiveButton(
//            com.liang.module_base.R.string.settings
//        ) { _, _ ->
//            startAppSettings() //打开设置
//        }
//        builder.setCancelable(false)
//        builder.show()
//    }

//    //打开系统应用设置(ACTION_APPLICATION_DETAILS_SETTINGS:系统设置权限)
//    private fun startAppSettings() {
//        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
//        intent.setData(Uri.parse(MapConstant.PACKAGE_URL_SCHEME + packageName))
//        startActivity(intent)
////        //Activity切换动画,必须在 StartActivity()  或 finish() 之后立即调用
////        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
//    }

//    private fun initPermission() {
//        // 请求定位权限
//        val permissions = listOf(
//            MapConstant.PERMISSION_FINE_LOCATION,
////            MapConstant.PERMISSION_COARSE_LOCATION,
//        )
//
//        // 请求定位权限
//        if (!PermissionUtils.isPermissionGranted(this, MapConstant.PERMISSION_FINE_LOCATION)) {
//            PermissionUtils.requestPermissions(
//                this,
//                permissions,
//                MapConstant.LOCATION_PERMISSIONS_REQUEST
//            ) {
//                // 可以在这里显示为什么需要这个权限的解释
//                // 例如：showDialogExplainingPermissions()
//            }
//        } else {
//            // 执行需要定位权限的操作
//            initLocationListener()
//        }
//
////        if (!PermissionUtils.checkPermissionsGranted(this, permissions)) {
////            PermissionUtils.requestPermissions(
////                this,
////                permissions,
////                MapConstant.LOCATION_PERMISSIONS_REQUEST
////            ) {
////                // 可以在这里显示为什么需要这个权限的解释
////                // 例如：showDialogExplainingPermissions()
////            }
////        } else {
////            // 执行需要定位权限的操作
////            initLocationListener()
////        }
//
//
////        if (!PermissionUtils.isPermissionGranted(this, MapConstant.PERMISSION_COARSE_LOCATION)) {
////            PermissionUtils.requestPermissions(
////                this,
////                permissions,
////                MapConstant.LOCATION_PERMISSIONS_REQUEST
////            ) {
////                // 可以在这里显示为什么需要这个权限的解释
////                // 例如：showDialogExplainingPermissions()
////            }
////        }
//
////        // 请求存储权限（注意：从Android 10开始，对于应用私有目录的访问通常不需要此权限）
////        if (!PermissionUtils.isPermissionGranted(this, MapConstant.PERMISSION_STORAGE)) {
////            PermissionUtils.requestPermissions(
////                this,
////                permissions,
////                MapConstant.STORAGE_PERMISSIONS_REQUEST
////            ) {
////                // 可以在这里显示为什么需要这个权限的解释
////                // 例如：showDialogExplainingPermissions()
////            }
////        }
//
//    }
}
