package com.zhq.exclusivememory.ui.activity.third_party.map;

import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMapOptions;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.LogoPosition;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.UiSettings;
import com.baidu.mapapi.model.LatLng;
//import com.qw.soul.permission.SoulPermission;
//import com.qw.soul.permission.bean.Permission;
//import com.qw.soul.permission.bean.Permissions;
//import com.qw.soul.permission.callbcak.CheckRequestPermissionsListener;
import com.baidu.mapapi.model.LatLngBounds;
import com.zhq.exclusivememory.R;
import com.zhq.exclusivememory.base.BaseSimpleActivity;

public class BaiduMapActivity extends BaseSimpleActivity implements View.OnClickListener {

    private MapView mapView;
    private BaiduMap baiduMap;
    private CheckBox cbTrafficMap;
    private CheckBox cbHotMap;
    private LocationClient mLocationClient;
    private TextView tvLocationLatLng;
    private TextView tvLocationName;
    private static final String TAG = "BaiduMapActivity";
    private MyLocationListener myLocationListener;
    private CheckBox cbScaleRule;
    private CheckBox cbCompass;
    private CheckBox cbScaleButton;
    private UiSettings uiSettings;
    private CheckBox cbMap1;
    private CheckBox cbMap2;
    private CheckBox cbMap3;
    private CheckBox cbMap4;
    private CheckBox cbMap5;
    private SDKReceiver sdkReceiver;
    private CheckBox cbShowPoi;
    private Button btnPoiSearch;
    private String city;

    @Override
    protected void initView() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            int flagTranslucentStatus = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
            int flagTranslucentNavigation = WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Window window = getWindow();
                WindowManager.LayoutParams attributes = window.getAttributes();
                attributes.flags |= flagTranslucentNavigation;
                window.setAttributes(attributes);
                getWindow().setStatusBarColor(Color.TRANSPARENT);
            } else {
                Window window = getWindow();
                WindowManager.LayoutParams attributes = window.getAttributes();
                attributes.flags |= flagTranslucentStatus | flagTranslucentNavigation;
                window.setAttributes(attributes);
            }
        }
        mapView = findViewById(R.id.map_view);
        RadioGroup radioGroup = findViewById(R.id.radio_group);
        cbTrafficMap = findViewById(R.id.cb_traffic_map);
        cbHotMap = findViewById(R.id.cb_hot_map);
        tvLocationLatLng = findViewById(R.id.tv_location_latlng);
        tvLocationName = findViewById(R.id.tv_location_name);
        Button btnOpenLocation = findViewById(R.id.btn_open_location);
        Button btnCustomLocationUi = findViewById(R.id.btn_custom_location_ui);
        cbScaleRule = findViewById(R.id.cb_scale_rule);
        cbCompass = findViewById(R.id.cb_compass);
        cbScaleButton = findViewById(R.id.cb_scale_button);
        Button btnDrawMarker = findViewById(R.id.btn_draw_marker);
        cbMap1 = findViewById(R.id.cb_map_1);
        cbMap2 = findViewById(R.id.cb_map_2);
        cbMap3 = findViewById(R.id.cb_map_3);
        cbMap4 = findViewById(R.id.cb_map_4);
        cbMap5 = findViewById(R.id.cb_map_5);
        cbShowPoi = findViewById(R.id.cb_show_poi);
        btnPoiSearch = findViewById(R.id.btn_poi_search);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_normal_map:
                        //普通地图
                        baiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
                        break;
                    case R.id.rb_moon_map:
                        baiduMap.setMapType(BaiduMap.MAP_TYPE_SATELLITE);
                        break;
                    case R.id.rb_blank_map:
                        baiduMap.setMapType(BaiduMap.MAP_TYPE_NONE);
                        break;
                }
            }
        });

        cbHotMap.setOnClickListener(this);
        cbTrafficMap.setOnClickListener(this);
        btnOpenLocation.setOnClickListener(this);
        btnCustomLocationUi.setOnClickListener(this);
        cbCompass.setOnClickListener(this);
        cbScaleButton.setOnClickListener(this);
        cbScaleRule.setOnClickListener(this);
        cbMap1.setOnClickListener(this);
        cbMap2.setOnClickListener(this);
        cbMap3.setOnClickListener(this);
        cbMap4.setOnClickListener(this);
        cbMap5.setOnClickListener(this);
        cbShowPoi.setOnClickListener(this);
        btnDrawMarker.setOnClickListener(this);
        btnPoiSearch.setOnClickListener(this);
        initReceiver();
    }

    private void initReceiver() {
        IntentFilter iFilter = new IntentFilter();
        iFilter.addAction(SDKInitializer.SDK_BROADTCAST_ACTION_STRING_PERMISSION_CHECK_ERROR);
        iFilter.addAction(SDKInitializer.SDK_BROADTCAST_ACTION_STRING_PERMISSION_CHECK_OK);
        sdkReceiver = new SDKReceiver();
        registerReceiver(sdkReceiver, iFilter);
    }

    @Override
    protected void initData() {
        baiduMap = mapView.getMap();
        //设置地图操作区距屏幕的距离
        int paddingBottom = getResources().getDimensionPixelSize(R.dimen.dp_50);
        baiduMap.setViewPadding(0, 0, 0, paddingBottom);
        mapView.setLogoPosition(LogoPosition.logoPostionRightBottom);//设置Baidu Logo位置
        baiduMap.setIndoorEnable(false);//打开室内图，默认为关闭状态
        //实例化UiSettings类对象
        uiSettings = baiduMap.getUiSettings();
        //地图状态的监听
        initMapStatusListener();
        //地图单击事件监听
        initMapSingleClickListener();
        //地图双击事件监听
        initMapDoubleClickListener();
        //地图长按事件监听
        initMapLongPressListener();
        //地图覆盖物点击监听
        initMapOverlyClickListener();
        //地图度盖屋拖拽监听
        initMapOverlyDragListener();
        //地图触摸事件监听
        initMapTouchListener();
        //地图加载监听
        initLoadMapListener();
        //地图渲染完成回调监听
        initMapRenderFinishListener();
        //地图截屏回调监听
        initMapSnapShotListener();
        //地图定位图标点击事件
        initLocationIconClickListener();
        BaiduMapOptions mapOptions = new BaiduMapOptions();
        //设置地图缩放级别
        setMapScaleGrade();
        initLocation();
    }

    /**
     * 地图覆盖物拖拽监听
     */
    private void initMapOverlyDragListener() {
        baiduMap.setOnMarkerDragListener(new BaiduMap.OnMarkerDragListener() {

            //在Marker拖拽过程中回调此方法，这个Marker的位置可以通过getPosition()方法获取
            //marker 被拖动的Marker对象
            @Override
            public void onMarkerDrag(Marker marker) {
                //对marker处理拖拽逻辑
            }

            //在Marker拖动完成后回调此方法， 这个Marker的位可以通过getPosition()方法获取
            //marker 被拖拽的Marker对象
            @Override
            public void onMarkerDragEnd(Marker marker) {

            }

            //在Marker开始被拖拽时回调此方法， 这个Marker的位可以通过getPosition()方法获取
            //marker 被拖拽的Marker对象
            @Override
            public void onMarkerDragStart(Marker marker) {

            }
        });

    }

    /**
     * 地图定位图标点击事件
     */
    private void initLocationIconClickListener() {
        BaiduMap.OnMyLocationClickListener listener = new BaiduMap.OnMyLocationClickListener() {
            /**
             * 地图定位图标点击事件监听函数
             */
            @Override
            public boolean onMyLocationClick() {
                Toast.makeText(BaiduMapActivity.this, "定位图标被点击", Toast.LENGTH_SHORT).show();
                return false;
            }
        };
        //设置定位图标点击事件监听
        baiduMap.setOnMyLocationClickListener(listener);
    }

    /**
     * 地图截屏回调
     */
    private void initMapSnapShotListener() {
        BaiduMap.SnapshotReadyCallback callback = new BaiduMap.SnapshotReadyCallback() {

            /**
             * 地图截屏回调接口
             *
             * @param snapshot 截屏返回的 bitmap 数据
             */
            @Override
            public void onSnapshotReady(Bitmap snapshot) {
                Log.d(TAG, "===地图截屏成功过onSnapshotReady: " + snapshot.toString());
            }
        };

        /**
         * 发起截图请求
         *
         * @param callback 截图完成后的回调
         */
        baiduMap.snapshot(callback);
    }

    /**
     * 地图渲染完成监听
     */
    private void initMapRenderFinishListener() {
        BaiduMap.OnMapRenderCallback callback = new BaiduMap.OnMapRenderCallback() {
            /**
             * 地图渲染完成回调函数
             */
            @Override
            public void onMapRenderFinished() {
                Toast.makeText(BaiduMapActivity.this, "地图渲染完成", Toast.LENGTH_SHORT).show();
            }
        };
        //设置地图渲染完成回调
        baiduMap.setOnMapRenderCallbadk(callback);
    }

    /**
     * 地图加载完成回调
     */
    private void initLoadMapListener() {
        BaiduMap.OnMapLoadedCallback callback = new BaiduMap.OnMapLoadedCallback() {
            /**
             * 地图加载完成回调函数
             */
            @Override
            public void onMapLoaded() {
                Toast.makeText(BaiduMapActivity.this, "地图加载完成", Toast.LENGTH_SHORT).show();
            }
        };
        //设置地图加载完成回调
        baiduMap.setOnMapLoadedCallback(callback);
    }

    /**
     * 地图触摸事件监听
     */
    private void initMapTouchListener() {
        BaiduMap.OnMapTouchListener listener = new BaiduMap.OnMapTouchListener() {

            /**
             * 当用户触摸地图时回调函数
             *
             * @param motionEvent 触摸事件
             */
            @Override
            public void onTouch(MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        Log.d(TAG, "===地图触摸DOWN");
                        break;
                    case MotionEvent.ACTION_MOVE:
                        Log.d(TAG, "===地图触摸MOVE");
                        break;
                    case MotionEvent.ACTION_UP:
                        Log.d(TAG, "===地图触摸UP");
                        break;
                }
            }
        };
        //设置触摸地图事件监听者
        baiduMap.setOnMapTouchListener(listener);
    }

    /**
     * 地图覆盖物点击监听回调
     */
    private void initMapOverlyClickListener() {
        BaiduMap.OnMarkerClickListener listener = new BaiduMap.OnMarkerClickListener() {
            /**
             * 地图 Marker 覆盖物点击事件监听函数
             * @param marker 被点击的 marker
             */
            public boolean onMarkerClick(Marker marker) {
                Toast.makeText(BaiduMapActivity.this, "地图覆盖物点击 " + marker.getTitle(), Toast.LENGTH_SHORT).show();
                return true;
            }
        };

        baiduMap.setOnMarkerClickListener(listener);
    }

    /**
     * 地图长按事件监听
     */
    private void initMapLongPressListener() {
        BaiduMap.OnMapLongClickListener listener = new BaiduMap.OnMapLongClickListener() {
            /**
             * 地图长按事件监听回调函数
             *
             * @param point 长按的地理坐标
             */
            @Override
            public void onMapLongClick(LatLng point) {
                Toast.makeText(BaiduMapActivity.this, "长按位置 经度：" + point.longitude + " 纬度：" + point.latitude, Toast.LENGTH_SHORT).show();

            }
        };
        //设置地图长按事件监听
        baiduMap.setOnMapLongClickListener(listener);
    }

    /**
     * 地图双击事件监听
     */
    private void initMapDoubleClickListener() {
        BaiduMap.OnMapDoubleClickListener listener = new BaiduMap.OnMapDoubleClickListener() {
            /**
             * 地图双击事件监听回调函数
             *
             * @param point 双击的地理坐标
             */
            @Override
            public void onMapDoubleClick(LatLng point) {
                Toast.makeText(BaiduMapActivity.this, "双击位置 经度：" + point.longitude + " 纬度：" + point.latitude, Toast.LENGTH_SHORT).show();

            }
        };

        //设置地图双击事件监听
        baiduMap.setOnMapDoubleClickListener(listener);
    }

    /**
     * 地图单击事件监听
     */
    private void initMapSingleClickListener() {
        BaiduMap.OnMapClickListener listener = new BaiduMap.OnMapClickListener() {
            /**
             * 地图单击事件回调函数
             *
             * @param point 点击的地理坐标
             */
            @Override
            public void onMapClick(LatLng point) {
                Toast.makeText(BaiduMapActivity.this, "单击位置 经度：" + point.longitude + " 纬度：" + point.latitude, Toast.LENGTH_SHORT).show();
            }

            /**
             * 地图内 Poi 单击事件回调函数
             *
             * @param mapPoi 点击的 poi 信息
             */
            @Override
            public void onMapPoiClick(MapPoi mapPoi) {
                Toast.makeText(BaiduMapActivity.this, "单击Poi名称：" + mapPoi.getName(), Toast.LENGTH_SHORT).show();
            }
        };
        //设置地图单击事件监听
        baiduMap.setOnMapClickListener(listener);
    }

    private void initMapStatusListener() {
        BaiduMap.OnMapStatusChangeListener listener = new BaiduMap.OnMapStatusChangeListener() {
            /**
             * 手势操作地图，设置地图状态等操作导致地图状态开始改变。
             *
             * @param status 地图状态改变开始时的地图状态
             */
            @Override
            public void onMapStatusChangeStart(MapStatus status) {
            }

            /**
             * 手势操作地图，设置地图状态等操作导致地图状态开始改变。
             *
             * @param status 地图状态改变开始时的地图状态
             *
             * @param reason 地图状态改变的原因
             */

            //用户手势触发导致的地图状态改变,比如双击、拖拽、滑动底图
            //int REASON_GESTURE = 1;
            //SDK导致的地图状态改变, 比如点击缩放控件、指南针图标
            //int REASON_API_ANIMATION = 2;
            //开发者调用,导致的地图状态改变
            //int REASON_DEVELOPER_ANIMATION = 3;
            @Override
            public void onMapStatusChangeStart(MapStatus status, int reason) {
                switch (reason) {
                    case REASON_GESTURE:
                        Toast.makeText(BaiduMapActivity.this, "用户手势导致地图状态改变", Toast.LENGTH_SHORT).show();
                        break;
                    case REASON_API_ANIMATION:
                        Toast.makeText(BaiduMapActivity.this, "点击地图按钮导致地图状态改变", Toast.LENGTH_SHORT).show();
                        break;
                    case REASON_DEVELOPER_ANIMATION:
                        Toast.makeText(BaiduMapActivity.this, "开发者调用导致地图状态改变", Toast.LENGTH_SHORT).show();
                        break;
                }
            }

            /**
             * 地图状态变化中
             *
             * @param status 当前地图状态
             */
            @Override
            public void onMapStatusChange(MapStatus status) {

            }

            /**
             * 地图状态改变结束
             *
             * @param status 地图状态改变结束后的地图状态
             */
            @Override
            public void onMapStatusChangeFinish(MapStatus status) {

            }
        };
        //设置地图状态监听
        baiduMap.setOnMapStatusChangeListener(listener);
    }

//    @Override
//    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
//        switch (requestCode) {
//            case 1:
//                if (grantResults.length > 0) {
//                    for (int grantResult : grantResults) {
//                        if (grantResult != PackageManager.PERMISSION_GRANTED) {
//                            Toast.makeText(this, "您必须统一所有权限才能使用本程序", Toast.LENGTH_SHORT).show();
//                            finish();
//                            return;
//                        }
//                    }
//                    //开启地图定位图层
//                    mLocationClient.start();
//                } else {
//                    Toast.makeText(this, "出现未知错误", Toast.LENGTH_SHORT).show();
//                    finish();
//                }
//                break;
//            default:
//                break;
//        }
//    }
//
//    private void requestUsePermissions() {
//        SoulPermission.getInstance().checkAndRequestPermissions(
//                Permissions.build(Manifest.permission.READ_PHONE_STATE, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION),
//                //if you want do noting or no need all the callbacks you may use SimplePermissionsAdapter instead
//                new CheckRequestPermissionsListener() {
//                    @Override
//                    public void onAllPermissionOk(Permission[] allPermissions) {
//                        Log.d(TAG, "==onAllPermissionOk: ");
//                    }
//
//                    @Override
//                    public void onPermissionDenied(Permission[] refusedPermissions) {
//                        Log.d(TAG, "==onPermissionDenied: ");
//                    }
//                });
//    }


    /**
     * 设置地图的缩放级别
     */
    private void setMapScaleGrade() {
        MapStatus.Builder builder = new MapStatus.Builder();
        builder.zoom(18.0f);
        baiduMap.setMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
    }

    @Override
    protected void onResume() {
        mapView.onResume();
        super.onResume();
    }

    @Override
    protected void onPause() {
        mapView.onPause();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        mLocationClient.stop();
        baiduMap.setMyLocationEnabled(false);
        mLocationClient.unRegisterLocationListener(myLocationListener);
        mapView.onDestroy();
        mapView = null;
        unregisterReceiver(sdkReceiver);
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cb_traffic_map:
                //开启交通图
                baiduMap.setTrafficEnabled(cbTrafficMap.isChecked());
                //修改交通图线路颜色
                //分别代表严重拥堵，拥堵，缓行，畅通
//                baiduMap.setCustomTrafficColor("#ffba0101", "#fff33131", "#ffff9e19", "#00000000");
//                //对地图状态做更新，否则可能不会触发渲染，造成样式定义无法立即生效。
//                MapStatusUpdate u = MapStatusUpdateFactory.zoomTo(13);
//                baiduMap.animateMapStatus(u);
                break;
            case R.id.cb_hot_map:
                //开启热力图
                baiduMap.setBaiduHeatMapEnabled(cbHotMap.isChecked());
                break;
            case R.id.btn_open_location://开启定位图层
                baiduMap.setMyLocationEnabled(true);
                break;
            case R.id.btn_custom_location_ui://自定义定位UI
                //自定义定位的UI样式
                BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory.fromResource(R.drawable.ic_location_icon);
                MyLocationConfiguration myLocationConfiguration = new MyLocationConfiguration(MyLocationConfiguration.LocationMode.NORMAL,
                        true, bitmapDescriptor, 0x66FF0000, 0x000099ff);
                baiduMap.setMyLocationConfiguration(myLocationConfiguration);
                break;
            case R.id.cb_scale_rule://比例尺
                //通过设置enable为true或false 选择是否显示比例尺
                mapView.showScaleControl(cbScaleRule.isChecked());
                break;
            case R.id.cb_compass://指南针
                //通过设置enable为true或false 选择是否显示指南针
                uiSettings.setCompassEnabled(cbCompass.isChecked());
                break;
            case R.id.cb_scale_button://缩放按钮
                //通过设置enable为true或false 选择是否显示缩放按钮
                mapView.showZoomControls(cbScaleButton.isChecked());
                break;
            case R.id.cb_map_1://禁用全部手势
                //通过设置enable为true或false 选择是否禁用所有手势
                uiSettings.setAllGesturesEnabled(!cbMap1.isChecked());
                break;
            case R.id.cb_map_2://地图平移
                //通过设置enable为true或false 选择是否启用地图平移
                uiSettings.setScrollGesturesEnabled(cbMap2.isChecked());
                break;
            case R.id.cb_map_3://地图缩放
                //通过设置enable为true或false 选择是否启用地图缩放手势
                uiSettings.setZoomGesturesEnabled(cbMap3.isChecked());
                break;
            case R.id.cb_map_4://地图俯视3D
                //通过设置enable为true或false 选择是否启用地图俯视功能
                uiSettings.setOverlookingGesturesEnabled(cbMap4.isChecked());
                break;
            case R.id.cb_map_5://地图旋转
                //通过设置enable为true或false 选择是否启用地图旋转功能
                uiSettings.setRotateGesturesEnabled(cbMap5.isChecked());
                break;
            case R.id.cb_show_poi://是否显示地图上的poi
                //隐藏地图标注，只显示道路信息
                //默认显示地图标注
                baiduMap.showMapPoi(cbShowPoi.isChecked());
                break;
            case R.id.btn_draw_marker:
                drawCustomMarker();
                break;
            case R.id.btn_poi_search:
                Intent intent = new Intent();
                intent.setClass(getContext(), BaiduPoiSearchActivity.class);
                intent.putExtra("city_name", city);
                intent.putExtra("startName",bdLocation.getBuildingName());
                if(bdLocation!=null){
                    intent.putExtra("latitude", bdLocation.getLatitude());
                    intent.putExtra("longitude", bdLocation.getLongitude());
                }
                startActivity(intent);
                break;
        }
    }

    /**
     * 绘制Marker
     */
    private void drawCustomMarker() {
        //定义Maker坐标点
        LatLng point = new LatLng(30.25949, 120.12979);
        //构建Marker图标
        BitmapDescriptor bitmap = BitmapDescriptorFactory
                .fromResource(R.drawable.ic_beijing);
        //构建MarkerOption，用于在地图上添加Marker
        OverlayOptions option = new MarkerOptions()
                .position(point) //必传参数
                .icon(bitmap) //必传参数
                .draggable(true)//设置平贴地图，在地图中双指下拉查看效果
                .perspective(true)//是否开启近大远小效果
                .animateType(MarkerOptions.MarkerAnimateType.jump)//动画类型
                .flat(true)
                .alpha(0.8f);
        //在地图上添加Marker，并显示
        baiduMap.addOverlay(option);
    }

    private void initLocation() {
        //定位初始化
        mLocationClient = new LocationClient(this);
        //通过LocationClientOption设置LocationClient相关参数
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        option.setOpenGps(true); // 打开gps
        option.setCoorType("bd09ll"); // 设置坐标类型
        option.setScanSpan(1000);
        option.setIsNeedAddress(true);

        //设置locationClientOption
        mLocationClient.setLocOption(option);
        //注册LocationListener监听器
        myLocationListener = new MyLocationListener();
        mLocationClient.registerLocationListener(myLocationListener);
        //开启地图定位图层
        mLocationClient.start();
    }

    private BDLocation bdLocation;

    public class MyLocationListener extends BDAbstractLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            Log.d(TAG, "===定位中onReceiveLocation: " + location.getLatitude() + "=" + location.getLongitude());
            //mapView 销毁后不在处理新接收的位置
            if (location == null || mapView == null) {
                return;
            }
            bdLocation = location;
            city = location.getCity();
            navigateTo(location);
            showBounds(location);
            //设置UI数据
            tvLocationLatLng.setText("经度：" + location.getLongitude() + "纬度：" + location.getLatitude());
            tvLocationName.setText(location.getCity());
        }
    }

    /**
     * 1.设置地图显示范围 2.改变地图手势的中心点
     *
     * @param location
     */
    private void showBounds(BDLocation location) {
        //1.设置地图显示范围
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        LatLngBounds build = builder.include(new LatLng(location.getLatitude(), location.getLongitude())).build();
        baiduMap.setMapStatus(MapStatusUpdateFactory.newLatLngBounds(build));
        //2.改变地图手势的中心点
        baiduMap.setMapStatus(MapStatusUpdateFactory.newLatLng(new LatLng(location.getLatitude(), location.getLongitude())));

    }

    private boolean isFirstLocate = true;

    private void navigateTo(BDLocation location) {
        //如果是第一次进入显示地图
        if (isFirstLocate) {
            //获取到当前所在位置的经纬度
            LatLng ll = new LatLng(location.getLatitude(), location.getLongitude());
            //通过MapStatusUpdateFactory设置经纬度
            MapStatusUpdate mapStatusUpdate = MapStatusUpdateFactory.newLatLng(ll);
            //baiduMap定位到那里
            baiduMap.animateMapStatus(mapStatusUpdate);
            //通过MapStatusUpdateFactory进行比例缩放
            mapStatusUpdate = MapStatusUpdateFactory.zoomTo(16f);
            //baiduMap进行缩放
            baiduMap.animateMapStatus(mapStatusUpdate);
            isFirstLocate = false;
            //获取到当前的位置，并进行标记
            MyLocationData.Builder locationBuilder = new MyLocationData.Builder();
            locationBuilder.accuracy(location.getRadius());
            locationBuilder.direction(location.getDirection());
            locationBuilder.latitude(location.getLatitude());
            locationBuilder.longitude(location.getLongitude());
            MyLocationData myLocationData = locationBuilder.build();
            baiduMap.setMyLocationData(myLocationData);
        }


    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_baidu_map;
    }

    /**
     * 地图元素压盖顺序
     * 1.自定义View（MapView.addView(View);）；
     *
     * 2.弹出窗图层（InfoWindow）；
     *
     * 3.定位图层（BaiduMap.setMyLocationEnabled(true);）；
     *
     * 4.指南针图层（当地图发生旋转和视角变化时，默认出现在左上角的指南针）；
     *
     * 5.标注图层（Marker），文字绘制图层（Text）；
     *
     * 6.几何图形图层（点、折线、弧线、圆、多边形）；
     *
     * 7.底图标注（指的是底图上面自带的那些POI元素）；
     *
     * 8.百度城市热力图（BaiduMap.setBaiduHeatMapEnabled(true);）；
     *
     * 9.实时路况图图层（BaiduMap.setTrafficEnabled(true);）；
     *
     * 10.热力图图层（HeatMap）；
     *
     * 11.地形图图层（GroundOverlay）；
     *
     * 12.瓦片图层（TileOverlay）；
     *
     * 13.基础底图（包括底图、底图道路、卫星图、室内图等）；
     */


}
