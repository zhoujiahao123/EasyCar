package com.jacob.www.easycar.main;

import android.content.DialogInterface;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.navi.AMapNavi;
import com.amap.api.navi.AMapNaviListener;
import com.amap.api.navi.AMapNaviView;
import com.amap.api.navi.AMapNaviViewListener;
import com.amap.api.navi.AMapNaviViewOptions;
import com.amap.api.navi.enums.NaviType;
import com.amap.api.navi.model.AMapLaneInfo;
import com.amap.api.navi.model.AMapNaviCameraInfo;
import com.amap.api.navi.model.AMapNaviCross;
import com.amap.api.navi.model.AMapNaviInfo;
import com.amap.api.navi.model.AMapNaviLocation;
import com.amap.api.navi.model.AMapNaviTrafficFacilityInfo;
import com.amap.api.navi.model.AMapServiceAreaInfo;
import com.amap.api.navi.model.AimLessModeCongestionInfo;
import com.amap.api.navi.model.AimLessModeStat;
import com.amap.api.navi.model.NaviInfo;
import com.amap.api.navi.model.NaviLatLng;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.geocoder.GeocodeQuery;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.amap.api.services.help.Inputtips;
import com.amap.api.services.help.InputtipsQuery;
import com.amap.api.services.help.Tip;
import com.amap.api.services.route.BusRouteResult;
import com.amap.api.services.route.DrivePath;
import com.amap.api.services.route.DriveRouteResult;
import com.amap.api.services.route.RideRouteResult;
import com.amap.api.services.route.RouteSearch;
import com.amap.api.services.route.WalkRouteResult;
import com.arlib.floatingsearchview.FloatingSearchView;
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;
import com.autonavi.tbt.TrafficFacilityInfo;
import com.bumptech.glide.Glide;
import com.gigamole.infinitecycleviewpager.HorizontalInfiniteCycleViewPager;
import com.jacob.www.easycar.R;
import com.jacob.www.easycar.base.App;
import com.jacob.www.easycar.data.GarageBean;
import com.jacob.www.easycar.data.SearchSuggestionItem;
import com.jacob.www.easycar.login.LogInActivity;
import com.jacob.www.easycar.net.ResponseCons;
import com.jacob.www.easycar.overlay.DrivingRouteOverlay;
import com.jacob.www.easycar.util.DisplayUtil;
import com.jacob.www.easycar.util.SpUtil;
import com.jacob.www.easycar.util.ToActivityUtil;
import com.jacob.www.easycar.widget.CircleImageView;
import com.jacob.www.easycar.widget.GarageImage;
import com.zxr.medicalaid.User;
import com.zxr.medicalaid.UserDao;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author 周家豪
 */
public class MainActivity extends AppCompatActivity implements MainContract.View, AMapNaviListener, AMapNaviViewListener, AMap.OnMyLocationChangeListener, FloatingSearchView.OnQueryChangeListener, FloatingSearchView.OnSearchListener, Inputtips.InputtipsListener, GeocodeSearch.OnGeocodeSearchListener, RouteSearch.OnRouteSearchListener {
    @BindView(R.id.person_image)
    CircleImageView personImage;
    @BindView(R.id.phone_num)
    TextView phoneNum;
    @BindView(R.id.car_num)
    TextView carNum;
    @BindView(R.id.userName)
    TextView userName;
    private String TAG = "MainActivity";
    AMapNavi mAMapNavi;
    MainContract.Presenter presenter;
    /**
     * 起始点经纬度
     */
    protected List<NaviLatLng> sList = new ArrayList<NaviLatLng>();
    /**
     * 终点经纬度
     */
    protected List<NaviLatLng> eList = new ArrayList<NaviLatLng>();
    /**
     * 途中经过的点的经纬度，一般都没用上
     */
    protected List<NaviLatLng> mWayPointList = new ArrayList<NaviLatLng>();
    /**
     * 获取 AMapNaviView 实例
     */
    AMapNaviView mAMapNaviView;
    //地图对象
    MapView mMapView = null;
    AMap aMap;
    /**
     * 定位的style
     */

    MyLocationStyle myLocationStyle;
    String currentCity;
    /**
     * 关键字搜索相关
     */
    GeocodeSearch geocodeSearch;
    InputtipsQuery query;
    Inputtips inputTips;
    /**
     * 目前我所在位置的经纬度
     */
    double myLongitude = 0, myLatitude = 0;
    @BindView(R.id.floating_search_view)
    FloatingSearchView mSearchView;
    //路径规划
    private RouteSearch mRouteSearch;
    private DriveRouteResult mDriveRouteResult;
    private float nowZoom = 18f;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        presenter = new MainPresenter(this);

        initView(savedInstanceState);

    }


    private void initView(Bundle savedInstanceState) {
        mSearchView.setOnQueryChangeListener(this);
        mSearchView.setOnSearchListener(this);
        //搜索功能初始化
        geocodeSearch = new GeocodeSearch(this);
        geocodeSearch.setOnGeocodeSearchListener(this);
        inputTips = new Inputtips(this, query);
        inputTips.setInputtipsListener(this);
        //地图
        mAMapNaviView = (AMapNaviView) findViewById(R.id.navi_view);
        mAMapNaviView.onCreate(savedInstanceState);
        mMapView = (MapView) findViewById(R.id.map);
        mMapView.onCreate(savedInstanceState);

        if (aMap == null) {
            aMap = mMapView.getMap();
        }
        aMap.getUiSettings().setMyLocationButtonEnabled(false);
        aMap.getUiSettings().setZoomControlsEnabled(false);
        aMap.getUiSettings().setZoomGesturesEnabled(true);


        myLocationStyle = new MyLocationStyle();
        myLocationStyle.interval(1000);
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATE);
        myLocationStyle.strokeColor(R.color.colorPrimary);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            myLocationStyle.radiusFillColor(getColor(R.color.touming));
        }
        myLocationStyle.strokeWidth(5);
        aMap.setMyLocationEnabled(true);

        aMap.setMyLocationStyle(myLocationStyle);
        aMap.setOnMyLocationChangeListener(this);
        aMap.moveCamera(CameraUpdateFactory.zoomTo(nowZoom));
        mRouteSearch = new RouteSearch(this);
        mRouteSearch.setRouteSearchListener(this);

        //用戶信息
        UserDao userDao = App.getDaoSession().getUserDao();
        User user = userDao.loadAll().get(0);
        userName.setText(user.getUserName());
        phoneNum.setText("" + user.getPhoneNum());
        carNum.setText("渝C00000");
        Glide.with(this).load(ResponseCons.BASE_URL + user.getIcon()).into(personImage);
    }

    /**
     * 开始导航,根据经纬度
     */
    public void startNavi(double latitude, double longitude) {
        double myLatitude = this.myLatitude;
        double myLongitude = this.myLongitude;
        Log.e("TAG", "收到的经纬度" + latitude + " " + longitude);
        Log.e("TAG", "我的经纬度:" + myLatitude + " " + myLongitude);
        mAMapNaviView.setVisibility(View.VISIBLE);
        mMapView.setVisibility(View.INVISIBLE);
        //算路终点坐标
        NaviLatLng mEndLatlng = new NaviLatLng(latitude, longitude);
        //算路起点坐标
        NaviLatLng mStartLatlng = new NaviLatLng(myLatitude, myLongitude);

        sList.add(mStartLatlng);
        eList.add(mEndLatlng);
        mAMapNavi = AMapNavi.getInstance(App.getAppContext());
        //添加监听回调，用于处理算路成功
        mAMapNavi.addAMapNaviListener(this);
        mAMapNaviView.setAMapNaviViewListener(this);
        AMapNaviViewOptions options = mAMapNaviView.getViewOptions();
        options.setLayoutVisible(false);
        mAMapNaviView.setViewOptions(options);
        onInitNaviSuccess();
    }

    @Override
    public void onMyLocationChange(Location location) {
        myLatitude = location.getLatitude();
        myLongitude = location.getLongitude();
        currentCity = location.getProvider();
    }


    @Override
    protected void onResume() {
        Log.e("TAG", "onResume");
        super.onResume();
        mAMapNaviView.onResume();
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        Log.e("TAG", "onResume");
        super.onPause();
        mAMapNaviView.onPause();
        mMapView.onPause();
    }

    @Override
    protected void onDestroy() {
        Log.e("TAG", "onResume");
        super.onDestroy();
        mAMapNaviView.onDestroy();
        if (mAMapNavi != null) {
            mAMapNavi.stopNavi();
            mAMapNavi.destroy();
        }
        mMapView.onDestroy();
    }

    @Override
    public void onInitNaviFailure() {
        Log.e("TAG", "算路失败");
    }

    @Override
    public void onInitNaviSuccess() {
        Log.e("TAG", "算路成功");
        int strategy = 0;
        try {
            strategy = mAMapNavi.strategyConvert(true, false, false, false, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
        mAMapNavi.calculateDriveRoute(sList, eList, mWayPointList, strategy);
        eList.remove(0);
        sList.remove(0);
    }

    @Override
    public void onStartNavi(int i) {

    }

    @Override
    public void onTrafficStatusUpdate() {

    }

    @Override
    public void onLocationChange(AMapNaviLocation aMapNaviLocation) {

    }

    @Override
    public void onGetNavigationText(int i, String s) {

    }

    @Override
    public void onGetNavigationText(String s) {

    }

    @Override
    public void onEndEmulatorNavi() {

    }

    @Override
    public void onArriveDestination() {

    }

    @Override
    public void onCalculateRouteFailure(int i) {

    }

    @Override
    public void onReCalculateRouteForYaw() {

    }

    @Override
    public void onReCalculateRouteForTrafficJam() {

    }

    @Override
    public void onArrivedWayPoint(int i) {

    }

    @Override
    public void onGpsOpenStatus(boolean b) {

    }

    private NaviInfo naviInfo;

    @Override
    public void onNaviInfoUpdate(NaviInfo naviInfo) {
        this.naviInfo = naviInfo;
        Log.e(TAG, naviInfo.getPathRetainDistance() + "      " + naviInfo.getPathRetainTime());
    }

    @Override
    public void onNaviInfoUpdated(AMapNaviInfo aMapNaviInfo) {

    }

    @Override
    public void updateCameraInfo(AMapNaviCameraInfo[] aMapNaviCameraInfos) {

    }

    @Override
    public void onServiceAreaUpdate(AMapServiceAreaInfo[] aMapServiceAreaInfos) {

    }

    @Override
    public void showCross(AMapNaviCross aMapNaviCross) {

    }

    @Override
    public void hideCross() {

    }

    @Override
    public void showLaneInfo(AMapLaneInfo[] aMapLaneInfos, byte[] bytes, byte[] bytes1) {

    }

    @Override
    public void hideLaneInfo() {

    }

    //算路成功后执行的
    @Override
    public void onCalculateRouteSuccess(int[] ints) {

        if (horizontalInfiniteCycleViewPager != null && horizontalInfiniteCycleViewPager.getVisibility() == View.VISIBLE) {
            horizontalInfiniteCycleViewPager.setVisibility(View.INVISIBLE);
        }
        mAMapNavi.startNavi(NaviType.GPS);
        isNavi = true;
    }

    boolean isNavi = false;

    @Override
    public void onBackPressed() {
        if (horizontalInfiniteCycleViewPager != null && horizontalInfiniteCycleViewPager.getVisibility() == View.INVISIBLE) {
            Toast.makeText(this, "已退出导航", Toast.LENGTH_SHORT).show();
            isNavi = false;
            mAMapNavi.stopNavi();
            horizontalInfiniteCycleViewPager = null;
            mAMapNaviView.setVisibility(View.INVISIBLE);
            aMap.clear();
            myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATE);
            aMap.setMyLocationStyle(myLocationStyle);
            aMap.moveCamera(CameraUpdateFactory.zoomTo(nowZoom));
            mMapView.setVisibility(View.VISIBLE);
        } else if (horizontalInfiniteCycleViewPager != null) {
            horizontalInfiniteCycleViewPager.setVisibility(View.INVISIBLE);
            horizontalInfiniteCycleViewPager = null;
            aMap.clear();
            myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATE);
            aMap.setMyLocationStyle(myLocationStyle);
            aMap.moveCamera(CameraUpdateFactory.zoomTo(nowZoom));
        } else if (isNavi) {
            isNavi = false;
            mAMapNavi.stopNavi();
            mAMapNaviView.setVisibility(View.INVISIBLE);
            aMap.clear();
            myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATE);
            aMap.setMyLocationStyle(myLocationStyle);
            aMap.moveCamera(CameraUpdateFactory.zoomTo(nowZoom));
            mMapView.setVisibility(View.VISIBLE);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void notifyParallelRoad(int i) {

    }

    @Override
    public void OnUpdateTrafficFacility(AMapNaviTrafficFacilityInfo aMapNaviTrafficFacilityInfo) {

    }

    @Override
    public void OnUpdateTrafficFacility(AMapNaviTrafficFacilityInfo[] aMapNaviTrafficFacilityInfos) {

    }

    @Override
    public void OnUpdateTrafficFacility(TrafficFacilityInfo trafficFacilityInfo) {

    }

    @Override
    public void updateAimlessModeStatistics(AimLessModeStat aimLessModeStat) {

    }

    @Override
    public void updateAimlessModeCongestionInfo(AimLessModeCongestionInfo aimLessModeCongestionInfo) {

    }

    @Override
    public void onPlayRing(int i) {

    }

    @Override
    public void onNaviSetting() {

    }

    @Override
    public void onNaviCancel() {

    }

    @Override
    public boolean onNaviBackClick() {
        return false;
    }

    @Override
    public void onNaviMapMode(int i) {

    }

    @Override
    public void onNaviTurnClick() {

    }

    @Override
    public void onNextRoadClick() {

    }

    @Override
    public void onScanViewButtonClick() {

    }

    @Override
    public void onLockMap(boolean b) {

    }

    @Override
    public void onNaviViewLoaded() {

    }


    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void showMsg(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSearchTextChanged(String oldQuery, String newQuery) {
        if (!"".equals(newQuery)) {
            Log.i(TAG, "oldQuery" + oldQuery + " " + "new" + newQuery);
            query = new InputtipsQuery(newQuery, currentCity);
            inputTips.setQuery(query);
            inputTips.requestInputtipsAsyn();
        }

    }

    @Override
    public void onSuggestionClicked(SearchSuggestion searchSuggestion) {
        SearchSuggestionItem item = (SearchSuggestionItem) searchSuggestion;
        GeocodeQuery query = new GeocodeQuery(item.getBody(), currentCity);
        geocodeSearch.getFromLocationNameAsyn(query);
    }

    @Override
    public void onSearchAction(String currentQuery) {
        Log.i(TAG, "currentQuery" + currentQuery);
        GeocodeQuery query = new GeocodeQuery(currentQuery, currentCity);
        geocodeSearch.getFromLocationNameAsyn(query);
    }


    @Override
    public void onGetInputtips(List<Tip> list, int i) {
        List<SearchSuggestionItem> suggestionItems = new ArrayList<>();
        for (Tip tip : list) {
            suggestionItems.add(new SearchSuggestionItem(tip.getName()));
        }
        mSearchView.swapSuggestions(suggestionItems);
    }

    /**
     * 坐标转文字
     *
     * @param regeocodeResult
     * @param i
     */
    @Override
    public void onRegeocodeSearched(RegeocodeResult regeocodeResult, int i) {

    }

    /**
     * 文字转坐标
     *
     * @param geocodeResult
     * @param i
     */
    @Override
    public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {
        if (i == 1000) {
            //请求成功,得到经纬度
            LatLonPoint latLonPoint = geocodeResult.getGeocodeAddressList().get(0).getLatLonPoint();
            Log.i(TAG, latLonPoint.getLatitude() + " " + latLonPoint.getLongitude() + "");
            Log.e(TAG, "开始搜索车库");
            presenter.getNearGarage(latLonPoint.getLongitude(), latLonPoint.getLatitude(), 2);
            desLat = latLonPoint.getLatitude();
            desLon = latLonPoint.getLongitude();
            //发起请求
        }
    }

    double desLat, desLon;

    public void getRealItem(double lon, double lat) {
        LatLonPoint mEndPoint = new LatLonPoint(lat, lon);
        RouteSearch.FromAndTo fromAndTo = new RouteSearch.FromAndTo(
                new LatLonPoint(myLatitude, myLongitude), mEndPoint);
        RouteSearch.DriveRouteQuery query = new RouteSearch.DriveRouteQuery(fromAndTo, RouteSearch.DrivingDefault, null,
                null, "");// 第一个参数表示路径规划的起点和终点，第二个参数表示驾车模式，第三个参数表示途经点，第四个参数表示避让区域，第五个参数表示避让道路
        mRouteSearch.calculateDriveRouteAsyn(query);// 异步路径规划驾车模式查询
    }

    HorizontalInfiniteCycleViewPager horizontalInfiniteCycleViewPager;
    GarageBean bean;

    @Override
    public void showGarage(GarageBean garageBean) {
        if (garageBean.getData().size() == 0) {
            Toast.makeText(this, "附近无车库", Toast.LENGTH_SHORT).show();
            startNavi(desLat, desLon);
        } else {
            bean = garageBean;
            horizontalInfiniteCycleViewPager = (HorizontalInfiniteCycleViewPager) findViewById(R.id.hicvp);
            if (horizontalInfiniteCycleViewPager.getVisibility() == View.INVISIBLE) {
                horizontalInfiniteCycleViewPager.setVisibility(View.VISIBLE);
            }
            MainAdapter adapter = new MainAdapter(this, garageBean);
            horizontalInfiniteCycleViewPager.setAdapter(adapter);
        }
    }


    @Override
    public void onBusRouteSearched(BusRouteResult busRouteResult, int i) {

    }

    @Override
    public void onDriveRouteSearched(DriveRouteResult result, int i) {
        if (i == AMapException.CODE_AMAP_SUCCESS) {
            if (result != null && result.getPaths() != null) {
                if (result.getPaths().size() > 0) {
                    aMap.clear();
                    mDriveRouteResult = result;
                    DrivePath drivePath = mDriveRouteResult.getPaths()
                            .get(0);
                    DrivingRouteOverlay drivingRouteOverlay = new DrivingRouteOverlay(
                            this, aMap, drivePath,
                            mDriveRouteResult.getStartPos(),
                            mDriveRouteResult.getTargetPos(), null);
                    //设置节点marker是否显示
                    drivingRouteOverlay.setNodeIconVisibility(false);
                    //是否用颜色展示交通拥堵情况，默认true
                    drivingRouteOverlay.setIsColorfulline(true);
                    drivingRouteOverlay.removeFromMap();
                    drivingRouteOverlay.addToMap();
                    drivingRouteOverlay.zoomToSpan();
                }
            }
        }
    }

    @Override
    public void onWalkRouteSearched(WalkRouteResult walkRouteResult, int i) {

    }

    @Override
    public void onRideRouteSearched(RideRouteResult rideRouteResult, int i) {

    }

    @OnClick({R.id.location, R.id.person_age, R.id.neighbor_garage, R.id.log_off, R.id.garage_info})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.location:
                if (myLatitude != 0) {
                    aMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(myLatitude, myLongitude), nowZoom));
                }
                break;
            case R.id.person_age:
                changBottomSheet(0);
                break;
            case R.id.neighbor_garage:
                changBottomSheet(1);
                presenter.getNearGarage(myLongitude, myLatitude, 2);
                break;
            case R.id.log_off:
                new AlertDialog.Builder(this)
                        .setTitle("提示")
                        .setMessage("您确定要退出吗")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                                SpUtil.putBoolean(MainActivity.this, "has_login", false);
                                App.getDaoSession().getUserDao().deleteAll();
                                ToActivityUtil.toNextActivityAndFinish(MainActivity.this, LogInActivity.class);
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        }).show();
                break;
            case R.id.garage_info:
                //得到二进制序列
                presenter.getGarageLot(gId);
                break;
            default:
                break;

        }

    }

    private void changBottomSheet(int i) {
        BottomSheetBehavior behavior = BottomSheetBehavior.from(findViewById(R.id.person_age_bottom_sheet));
        if (behavior.getState() == BottomSheetBehavior.STATE_COLLAPSED && i == 0) {
            behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        } else if (behavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
            behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        }
    }


    private String gId;

    /**
     * 用来设置当前的车库的id
     */
    public void setGarageId(String gId) {
        this.gId = gId;
        Log.e(TAG, gId + "是gid");
    }

    //展示车位

    @Override
    public void showLot(String lot) {
        LinearLayout dialogLinear = new LinearLayout(this);
        dialogLinear.setOrientation(LinearLayout.HORIZONTAL);
        dialogLinear.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        dialogLinear.setGravity(Gravity.CENTER);
        DisplayMetrics dm = this.getResources().getDisplayMetrics();
        LinearLayout.LayoutParams chidLp = new LinearLayout.LayoutParams(DisplayUtil.dip2px(60, dm.density), DisplayUtil.dip2px(60, dm.density));
        chidLp.setMargins(DisplayUtil.dip2px(8, dm.density), DisplayUtil.dip2px(8, dm.density), DisplayUtil.dip2px(8, dm.density), DisplayUtil.dip2px(8, dm.density));

        for (int i = 0; i < lot.length(); i++) {
            GarageImage garageImage = new GarageImage(this);
            garageImage.setLayoutParams(chidLp);
            garageImage.setText(i + 1 + "");
            garageImage.setTextSize(DisplayUtil.sp2px(18, dm.scaledDensity));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                garageImage.setFillColor(lot.toCharArray()[i] == '0' ? getColor(R.color.red) : getColor(R.color.green));
                garageImage.setWordColor(getColor(R.color.white));
            }
            dialogLinear.addView(garageImage);
        }
        new AlertDialog.Builder(this)
                .setTitle("当前车库信息")
                .setView(dialogLinear)
                .setCancelable(true)
                .setPositiveButton("好的", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .show();
    }

}
