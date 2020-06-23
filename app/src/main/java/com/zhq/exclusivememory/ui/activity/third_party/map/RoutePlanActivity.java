package com.zhq.exclusivememory.ui.activity.third_party.map;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.baidu.location.BDLocation;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.route.BikingRouteResult;
import com.baidu.mapapi.search.route.DrivingRouteResult;
import com.baidu.mapapi.search.route.IndoorRouteResult;
import com.baidu.mapapi.search.route.MassTransitRouteResult;
import com.baidu.mapapi.search.route.OnGetRoutePlanResultListener;
import com.baidu.mapapi.search.route.PlanNode;
import com.baidu.mapapi.search.route.RoutePlanSearch;
import com.baidu.mapapi.search.route.TransitRouteResult;
import com.baidu.mapapi.search.route.WalkingRouteLine;
import com.baidu.mapapi.search.route.WalkingRoutePlanOption;
import com.baidu.mapapi.search.route.WalkingRouteResult;
import com.baidu.mapapi.walknavi.WalkNavigateHelper;
import com.baidu.mapapi.walknavi.adapter.IWEngineInitListener;
import com.baidu.mapapi.walknavi.adapter.IWRoutePlanListener;
import com.baidu.mapapi.walknavi.model.WalkRoutePlanError;
import com.baidu.mapapi.walknavi.params.WalkNaviLaunchParam;
import com.baidu.mapapi.walknavi.params.WalkRouteNodeInfo;
import com.zhq.exclusivememory.R;
import com.zhq.exclusivememory.base.BaseSimpleActivity;
import com.zhq.exclusivememory.ui.activity.third_party.map.map_util.WalkingRouteOverlay;

import java.util.List;

public class RoutePlanActivity extends Activity implements View.OnClickListener, OnGetRoutePlanResultListener {


    private EditText etStart;
    private EditText etEnd;
    private MapView mapView;
    private BaiduMap baiduMap;
    private RoutePlanSearch routePlanSearch;
    private double sLatitude;
    private double sLongitude;
    private double eLatitude;
    private double eLongitude;
    private String startName;
    private String endName;
    private WalkNaviLaunchParam walkNaviLaunchParam;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_plan);
        initView();
        initData();
    }


    private void initView() {
        etStart = findViewById(R.id.et_start);
        etEnd = findViewById(R.id.et_end);
        mapView = findViewById(R.id.map_view);
        Button btnWalkRoute = findViewById(R.id.btn_walk_route);
        Button btnBusRoute = findViewById(R.id.btn_bus_route);
        Button btnDriveRoute = findViewById(R.id.btn_drive_route);
        Button btnBikeRoute = findViewById(R.id.btn_bike_route);
        Button btnWaklNavi = findViewById(R.id.btn_walk_navi);
        Button btnBusNavi = findViewById(R.id.btn_bus_navi);
        Button btnDriveNavi = findViewById(R.id.btn_drive_navi);
        Button btnBikeNavi = findViewById(R.id.btn_bike_navi);
        btnWalkRoute.setOnClickListener(this);
        btnBusRoute.setOnClickListener(this);
        btnDriveRoute.setOnClickListener(this);
        btnBikeRoute.setOnClickListener(this);
        btnWaklNavi.setOnClickListener(this);
        btnBikeNavi.setOnClickListener(this);
        btnBusNavi.setOnClickListener(this);
        btnDriveNavi.setOnClickListener(this);
    }


    private void initData() {
        Intent intent = getIntent();
        sLatitude = intent.getDoubleExtra("sLatitude", 0);
        sLongitude = intent.getDoubleExtra("sLongitude", 0);
        eLatitude = intent.getDoubleExtra("eLatitude", 0);
        eLongitude = intent.getDoubleExtra("eLongitude", 0);
        startName = intent.getStringExtra("startName");
        endName = intent.getStringExtra("endName");
        etStart.setText(startName);
        etEnd.setText(endName);
        baiduMap = mapView.getMap();
        baiduMap.setMyLocationEnabled(true);
        navigateTo(sLatitude,sLongitude);
        routePlanSearch = RoutePlanSearch.newInstance();
        routePlanSearch.setOnGetRoutePlanResultListener(this);
    }

    private void navigateTo(double latitude,double longitude) {
        //如果是第一次进入显示地图

        //获取到当前所在位置的经纬度
        LatLng ll = new LatLng(latitude,longitude);
        //通过MapStatusUpdateFactory设置经纬度
        MapStatusUpdate mapStatusUpdate = MapStatusUpdateFactory.newLatLng(ll);
        //baiduMap定位到那里
        baiduMap.animateMapStatus(mapStatusUpdate);
        //通过MapStatusUpdateFactory进行比例缩放
        mapStatusUpdate = MapStatusUpdateFactory.zoomTo(16f);
        //baiduMap进行缩放
        baiduMap.animateMapStatus(mapStatusUpdate);
        WalkNavigateHelper.getInstance().initNaviEngine(RoutePlanActivity.this, new IWEngineInitListener() {

            @Override
            public void engineInitSuccess() {
                //引擎初始化成功的回调
                Log.d(TAG, "==engineInitSuccess: ");
                routeWalkPlanWithParam();
            }

            @Override
            public void engineInitFail() {
                //引擎初始化失败的回调
                Log.d(TAG, "==engineInitFail: ");
            }
        });
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
        routePlanSearch.destroy();
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_walk_route:
                walkRoutePlan(sLatitude,sLongitude,eLatitude,eLongitude);
                break;
            case R.id.btn_bus_route:
                break;
            case R.id.btn_drive_route:
                break;
            case R.id.btn_bike_route:
                break;
            case R.id.btn_walk_navi:
                walkNavigation();
                break;
            case R.id.btn_bus_navi:
                break;
            case R.id.btn_drive_navi:
                break;
            case R.id.btn_bike_navi:
                break;
        }
    }

    private static final String TAG = "RoutePlanActivity";
    private void walkNavigation() {
        // 获取导航控制类
         // 引擎初始化
        WalkNavigateHelper.getInstance().initNaviEngine(RoutePlanActivity.this, new IWEngineInitListener() {

            @Override
            public void engineInitSuccess() {
                //引擎初始化成功的回调
                Log.d(TAG, "==engineInitSuccess: ");
                routeWalkPlanWithParam();
            }

            @Override
            public void engineInitFail() {
                //引擎初始化失败的回调
                Log.d(TAG, "==engineInitFail: ");
            }
        });
    }

    private void routeWalkPlanWithParam() {
        //起终点位置
        LatLng startPoint = new LatLng(sLatitude, sLongitude);
        LatLng endPoint = new LatLng(eLatitude, eLongitude);
        WalkRouteNodeInfo startNode = new WalkRouteNodeInfo();
        startNode.setLocation(startPoint);
        WalkRouteNodeInfo endNode = new WalkRouteNodeInfo();
        endNode.setLocation(endPoint);
        //构造WalkNaviLaunchParam
        walkNaviLaunchParam = new WalkNaviLaunchParam().startNodeInfo(startNode).endNodeInfo(endNode);
        //发起算路
        WalkNavigateHelper.getInstance().routePlanWithParams(walkNaviLaunchParam, new IWRoutePlanListener() {
            @Override
            public void onRoutePlanStart() {
                //开始算路的回调
            }

            @Override
            public void onRoutePlanSuccess() {
                //算路成功
                //跳转至诱导页面
                Intent intent = new Intent(RoutePlanActivity.this, WalkNavigationActivity.class);
                startActivity(intent);
            }

            @Override
            public void onRoutePlanFail(WalkRoutePlanError walkRoutePlanError) {
                //算路失败的回调
            }
        });
    }



    private void walkRoutePlan(double startLatitude,double startLongitude,double endLatitude,double endLongitude){
        LatLng sLatLng = new LatLng(startLatitude, startLongitude);
        LatLng eLatLng = new LatLng(endLatitude, endLongitude);
        PlanNode startPlanNode = PlanNode.withLocation(sLatLng);
        PlanNode endPlanNode = PlanNode.withLocation(eLatLng);
        routePlanSearch.walkingSearch(new WalkingRoutePlanOption()
                .from(startPlanNode)
                .to(endPlanNode));
    }

    /**
     * 步行线路规划
     *
     * @param walkingRouteResult
     */
    @Override
    public void onGetWalkingRouteResult(WalkingRouteResult walkingRouteResult) {
        if (walkingRouteResult != null) {
            if (walkingRouteResult.status == 0) {
                List<WalkingRouteLine> routeLines = walkingRouteResult.getRouteLines();
                if (routeLines != null && routeLines.size() > 0) {
                    WalkingRouteLine line = routeLines.get(0);
                    WalkingRouteOverlay walkingRouteOverlay = new WalkingRouteOverlay(baiduMap);
                    walkingRouteOverlay.setData(line);
                    walkingRouteOverlay.addToMap();
                    //baiduMap进行缩放
                    baiduMap.animateMapStatus(MapStatusUpdateFactory.zoomTo(13f));
                    //找到起始点
                    WalkingRouteLine.WalkingStep walkingStep = line.getAllStep().get(0);
                    etStart.setText(walkingStep.getEntrance().getTitle());
                }
            }
        }
    }

    /**
     * 公交线路规划
     *
     * @param transitRouteResult
     */
    @Override
    public void onGetTransitRouteResult(TransitRouteResult transitRouteResult) {

    }

    /**
     * 地铁线路规划
     *
     * @param massTransitRouteResult
     */
    @Override
    public void onGetMassTransitRouteResult(MassTransitRouteResult massTransitRouteResult) {

    }

    /**
     * 驾车线路规划
     *
     * @param drivingRouteResult
     */
    @Override
    public void onGetDrivingRouteResult(DrivingRouteResult drivingRouteResult) {

    }

    /**
     * 室内线路规划
     *
     * @param indoorRouteResult
     */
    @Override
    public void onGetIndoorRouteResult(IndoorRouteResult indoorRouteResult) {

    }

    /**
     * 骑行线路规划
     *
     * @param bikingRouteResult
     */
    @Override
    public void onGetBikingRouteResult(BikingRouteResult bikingRouteResult) {

    }
}
