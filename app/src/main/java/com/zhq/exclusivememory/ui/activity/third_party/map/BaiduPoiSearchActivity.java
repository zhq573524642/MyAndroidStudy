package com.zhq.exclusivememory.ui.activity.third_party.map;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiCitySearchOption;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiDetailSearchResult;
import com.baidu.mapapi.search.poi.PoiIndoorResult;
import com.baidu.mapapi.search.poi.PoiNearbySearchOption;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.zhq.exclusivememory.R;
import com.zhq.exclusivememory.base.BaseSimpleActivity;

import java.util.ArrayList;
import java.util.List;

public class BaiduPoiSearchActivity extends BaseSimpleActivity implements OnRefreshListener, OnLoadmoreListener, View.OnClickListener, OnGetPoiSearchResultListener, PoiSearchResultAdapter.OnPoiInfoItemClickListener {


    private EditText etInputKey;
    private Button btnSearch;
    private Button btnCancel;
    private SmartRefreshLayout smartRefreshLayout;
    private RecyclerView rvPoiSearchView;
    private PoiSearch poiSearch;
    private String cityName;
    private int page = 0;
    private int totalPageNum;
    private List<PoiInfo> poiInfoList = new ArrayList<>();
    private PoiSearchResultAdapter poiSearchResultAdapter;
    private TextView tvNoData;
    private RadioGroup radioGroup;
    private int searchType = 1;
    private double latitude;
    private double longitude;
    private MapView mapView;
    private BaiduMap baiduMap;
    private LocationClient mLocationClient;
    private MyLocationListener myLocationListener;
    private String startName;

    @Override
    protected void initView() {
        etInputKey = findViewById(R.id.et_input_key);
        btnSearch = findViewById(R.id.btn_search);
        btnCancel = findViewById(R.id.btn_cancel);
        smartRefreshLayout = findViewById(R.id.smart_refresh_layout);
        rvPoiSearchView = findViewById(R.id.rv_poi_search_view);
        tvNoData = findViewById(R.id.tv_no_data);
        radioGroup = findViewById(R.id.radio_group);
        mapView = findViewById(R.id.map_view);
        smartRefreshLayout.setOnRefreshListener(this);
        smartRefreshLayout.setOnLoadmoreListener(this);
        btnSearch.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
        rvPoiSearchView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        poiSearchResultAdapter = new PoiSearchResultAdapter(getContext(), poiInfoList);
        rvPoiSearchView.setAdapter(poiSearchResultAdapter);
        poiSearchResultAdapter.setOnPoiInfoItemClickListener(this);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_in_city:
                        searchType = 1;
                        break;
                    case R.id.rb_nearby:
                        searchType = 2;
                        break;
                }
            }
        });

    }

    @Override
    protected void initData() {
        poiInfoList.clear();
        Intent intent = getIntent();
        if (intent != null) {
            cityName = intent.getStringExtra("city_name");
            startName = intent.getStringExtra("startName");
            latitude = intent.getDoubleExtra("latitude", 0);
            longitude = intent.getDoubleExtra("longitude", 0);
        }
        baiduMap = mapView.getMap();
        setMapScaleGrade();
        baiduMap.setMyLocationEnabled(true);
        poiSearch = PoiSearch.newInstance();
        poiSearch.setOnGetPoiSearchResultListener(this);
        initLocation();
    }

    @Override
    public void onPoiInfoItemClick(int position) {
        Intent intent = new Intent(getContext(), RoutePlanActivity.class);
        intent.putExtra("startName", startName);
        intent.putExtra("endName",poiInfoList.get(position).getName());
        intent.putExtra("sLatitude",latitude);
        intent.putExtra("sLongitude",longitude);
        intent.putExtra("eLatitude",poiInfoList.get(position).getLocation().latitude);
        intent.putExtra("eLongitude",poiInfoList.get(position).getLocation().longitude);
        startActivity(intent);
    }
    /**
     * 设置地图的缩放级别
     */
    private void setMapScaleGrade() {
        MapStatus.Builder builder = new MapStatus.Builder();
        builder.zoom(18.0f);
        baiduMap.setMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        if (refreshlayout != null) {
            refreshlayout.finishRefresh();
        }
        page = 0;
        String key = etInputKey.getText().toString().trim();
        startPoiSearch(key, page);
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
        baiduMap.setMyLocationEnabled(false);
        mapView.onDestroy();
        mapView = null;
        poiSearch.destroy();
        super.onDestroy();
    }

    @Override
    public void onLoadmore(RefreshLayout refreshlayout) {
        if (refreshlayout != null) {
            refreshlayout.finishLoadmore();
        }
        if (page == totalPageNum) {
            Toast.makeText(this, "暂无更多数据", Toast.LENGTH_SHORT).show();
            return;
        }
        page++;
        String key = etInputKey.getText().toString().trim();
        startPoiSearch(key, page);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_baidu_poi_search;
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
    private static final String TAG = "BaiduPoiSearchActivity";



    public class MyLocationListener extends BDAbstractLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            Log.d(TAG, "===定位中onReceiveLocation: " + location.getLatitude() + "=" + location.getLongitude());
            //mapView 销毁后不在处理新接收的位置
            if (location == null || mapView == null) {
                return;
            }
            bdLocation = location;
            navigateTo(location);
        }
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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_search:
                String key = etInputKey.getText().toString().trim();
                if (TextUtils.isEmpty(key)) {
                    Toast.makeText(this, "请输入您要搜索的内容", Toast.LENGTH_SHORT).show();
                    return;
                }
                page = 0;
                startPoiSearch(key, page);
                break;
            case R.id.btn_cancel:
                etInputKey.getText().clear();
                poiInfoList.clear();
                poiSearchResultAdapter.notifyDataSetChanged();
                tvNoData.setVisibility(View.VISIBLE);
                break;
        }
    }

    private void startPoiSearch(String key, int page) {
/**
 *  PoiCiySearchOption 设置检索属性
 *  city 检索城市
 *  keyword 检索内容关键字
 *  pageNum 分页页码
 */
        if (searchType == 1) {
            poiSearch.searchInCity(new PoiCitySearchOption()
                    .city(cityName) //必填
                    .keyword(key) //必填
                    .pageCapacity(10)
                    .scope(2)//1 返回基本信息 2 返回Poi详细信息
                    .pageNum(page));
        } else if (searchType == 2) {
            poiSearch.searchNearby(new PoiNearbySearchOption()
                    .location(new LatLng(latitude, longitude))
                    .radius(1000)
                    .pageCapacity(10)
                    .scope(2)
                    .keyword(key)
                    .pageNum(page));
        }

    }

    @Override
    public void onGetPoiResult(PoiResult poiResult) {
        if (poiResult != null) {
            if (poiResult.status == 0) {//成功
                totalPageNum = poiResult.getTotalPageNum();
                if (page == 0) {
                    poiInfoList.clear();
                }
                List<PoiInfo> allPoi = poiResult.getAllPoi();
                if (allPoi != null && allPoi.size() > 0) {
                    tvNoData.setVisibility(View.GONE);
                    poiInfoList.addAll(allPoi);
                } else {
                    tvNoData.setVisibility(View.VISIBLE);
                }
                poiSearchResultAdapter.notifyDataSetChanged();
                baiduMap.clear();
                if (allPoi != null && allPoi.size() > 0) {
                    for (PoiInfo info : allPoi) {
                        //定义Maker坐标点
                        LatLng point = new LatLng(info.getLocation().latitude, info.getLocation().longitude);
                        //构建Marker图标
                        BitmapDescriptor bitmap = BitmapDescriptorFactory
                                .fromResource(R.drawable.ic_tag);
                        //构建MarkerOption，用于在地图上添加Marker
                        OverlayOptions option = new MarkerOptions()
                                .position(point)
                                .icon(bitmap)
                                .draggable(true)
                                //设置平贴地图，在地图中双指下拉查看效果
                                .flat(true)
                                .alpha(0.5f);
                        //在地图上添加Marker，并显示
                        baiduMap.addOverlay(option);
                    }
                }else {
                    Toast.makeText(this, "未查询到数据", Toast.LENGTH_SHORT).show();
                }


            } else {
                Toast.makeText(this, "搜索失败", Toast.LENGTH_SHORT).show();
                tvNoData.setVisibility(View.VISIBLE);
            }
        } else {
            Toast.makeText(this, "搜索失败", Toast.LENGTH_SHORT).show();
            tvNoData.setVisibility(View.VISIBLE);


        }
    }

    @Override
    public void onGetPoiDetailResult(PoiDetailResult poiDetailResult) {

    }

    @Override
    public void onGetPoiDetailResult(PoiDetailSearchResult poiDetailSearchResult) {

    }

    @Override
    public void onGetPoiIndoorResult(PoiIndoorResult poiIndoorResult) {

    }
}
