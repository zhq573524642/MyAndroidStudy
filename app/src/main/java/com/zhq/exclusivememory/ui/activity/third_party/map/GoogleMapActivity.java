package com.zhq.exclusivememory.ui.activity.third_party.map;

import android.Manifest;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.zhq.exclusivememory.R;
import com.zhq.exclusivememory.base.BaseSimpleActivity;
import com.zhq.exclusivememory.receiver.ProximityAlertReceiver;
import com.zhq.exclusivememory.utils.LogUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Huiqiang Zhang
 * on 2019/3/10.
 */

public class GoogleMapActivity extends BaseSimpleActivity {
    @BindView(R.id.ll_left_back)
    LinearLayout mLlLeftBack;
    @BindView(R.id.tv_center_title)
    TextView mTvCenterTitle;
    @BindView(R.id.btn1)
    Button mBtn1;
    @BindView(R.id.btn2)
    Button mBtn2;
    @BindView(R.id.lv_location_provider)
    ListView mLvLocationProvider;
    @BindView(R.id.tv_location_provider)
    TextView mTvLocationProvider;
    @BindView(R.id.btn_gps_location)
    Button mBtnGpsLocation;
    @BindView(R.id.tv_gps_location)
    TextView mTvGpsLocation;
    @BindView(R.id.btn_net_location)
    Button mBtnNetLocation;
    @BindView(R.id.tv_net_location)
    TextView mTvNetLocation;
    @BindView(R.id.et_input_longitude)
    EditText mEtInputLongitude;
    @BindView(R.id.et_input_latitude)
    EditText mEtInputLatitude;
    @BindView(R.id.et_input_radius)
    EditText mEtInputRadius;
    private LocationManager mLocationManager;

    @Override
    protected void initData() {
        //获取LocationManager
        mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
    }

    @Override
    protected void initView() {
        mTvCenterTitle.setText("google定位");
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_google_map;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.ll_left_back, R.id.btn1, R.id.btn2, R.id.btn_gps_location, R.id.btn_net_location, R.id.btn_approach_alert})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_left_back:
                finish();
                break;
            case R.id.btn1:
                getAllLocationProvider(mLocationManager);
                break;
            case R.id.btn2:
                getSpeciallyLocationProvider(mLocationManager);
                break;
            case R.id.btn_gps_location:
                checkGpsLocationPermission();
                break;
            case R.id.btn_net_location:
                checkNetLocationPermission();
                break;
            case R.id.btn_approach_alert:
                approachAlert(mLocationManager);
                break;
        }
    }

    private void approachAlert(LocationManager locationManager) {
        String lon = mEtInputLongitude.getText().toString().trim();
        String lat = mEtInputLatitude.getText().toString().trim();
        String r = mEtInputRadius.getText().toString().trim();
        if (TextUtils.isEmpty(lon)) {
            Toast.makeText(this, "请输入经度...", Toast.LENGTH_SHORT).show();
            mEtInputLongitude.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(lat)) {
            Toast.makeText(this, "请输入纬度...", Toast.LENGTH_SHORT).show();
            mEtInputLatitude.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(r)) {
            Toast.makeText(this, "请输入范围半径...", Toast.LENGTH_SHORT).show();
            mEtInputRadius.requestFocus();
            return;
        }
        double longitude = Double.parseDouble(lon);//经度
        double latitude = Double.parseDouble(lat);//纬度
        float radius = Float.parseFloat(r);//范围半径
        LogUtil.i("hahahahah","====纬度： "+latitude+" ==经度 "+longitude+" ==半径 "+radius);
        Intent intent = new Intent(GoogleMapActivity.this, ProximityAlertReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, -1, intent, 0);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.addProximityAlert(latitude, longitude, radius, -1, pendingIntent);
    }

    private void checkNetLocationPermission() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 2);
            return;
        } else {
            getNetLocationMsg(mLocationManager);
        }
    }

    private void getNetLocationMsg(final LocationManager locationManager) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        updateView(location);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 3000, 8, new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                updateView(location);
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

                updateView(locationManager.getLastKnownLocation(provider));
            }

            @Override
            public void onProviderDisabled(String provider) {
                updateView(null);
            }
        });
    }

    private void checkGpsLocationPermission() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            return;
        } else {
            getGpsLocationMsg(mLocationManager);
        }
    }

    private void getGpsLocationMsg(LocationManager locationManager) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        final Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        //使用Location展示定位信息
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            AlertDialog.Builder builder = new AlertDialog.Builder(GoogleMapActivity.this);
            builder.setTitle("友情提示：");
            builder.setMessage("请检查您是否打开GPS");
            builder.setPositiveButton("打开", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(
                            Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivityForResult(intent, 1001);
                }
            });
            builder.setNegativeButton("暂不打开", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(GoogleMapActivity.this, "您拒绝打开GPS，可能无法使用该功能", Toast.LENGTH_SHORT).show();
                }
            });
            builder.show();
        } else {
            updateView(location);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 3000, 8, new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    updateView(location);
                }

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {

                }

                @Override
                public void onProviderEnabled(String provider) {
                    updateView(mLocationManager.getLastKnownLocation(provider));
                }

                @Override
                public void onProviderDisabled(String provider) {
                    updateView(null);
                }
            });
        }

    }

    private void updateView(Location location) {
        if (location != null) {
            if (location.getProvider().equals(LocationManager.GPS_PROVIDER)) {
                StringBuilder sb = new StringBuilder();
                sb.append("GPS实时的位置信息：\n");
                sb.append("经度：").append(location.getLongitude());
                sb.append("\n纬度：").append(location.getLatitude());
                sb.append("\n高度：").append(location.getAltitude());
                sb.append("\n速度：").append(location.getSpeed());
                sb.append("\n方向：").append(location.getBearing());
                mTvGpsLocation.setText(sb.toString());
            } else {
                StringBuilder sb = new StringBuilder();
                sb.append("Net实时的位置信息：\n");
                sb.append("经度：").append(location.getLongitude());
                sb.append("\n纬度：").append(location.getLatitude());
                sb.append("\n高度：").append(location.getAltitude());
                sb.append("\n速度：").append(location.getSpeed());
                sb.append("\n方向：").append(location.getBearing());
                mTvNetLocation.setText(sb.toString());
            }

        } else {
            Toast.makeText(this, "请确认您是否已经打开GPS", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode) {
            case 1:
                if (grantResults.length > 0) {
                    getGpsLocationMsg(mLocationManager);
                } else {
                    Toast.makeText(this, "未获取到定位权限", Toast.LENGTH_SHORT).show();
                }
                break;
            case 2:
                if (grantResults.length > 0) {
                    getNetLocationMsg(mLocationManager);
                } else {
                    Toast.makeText(this, "未获取到定位权限", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private void getSpeciallyLocationProvider(LocationManager locationManager) {
        //创建一个LocationProvider的过滤提条件
        Criteria criteria = new Criteria();
        //设置要求LocationProvider必须是免费的
        criteria.setCostAllowed(false);
        //设置LocationProvider能提供高度信息
        criteria.setAltitudeRequired(true);
        //设置LocationProvider能提供方向信息
        criteria.setBearingRequired(true);
        String bestProvider = locationManager.getBestProvider(criteria, true);
        mTvLocationProvider.setText(bestProvider);

    }

    private void getAllLocationProvider(LocationManager locationManager) {
        List<String> allProviders = locationManager.getAllProviders();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, allProviders);
        mLvLocationProvider.setAdapter(adapter);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 1001:
                getGpsLocationMsg(mLocationManager);
                break;
        }
    }
}
