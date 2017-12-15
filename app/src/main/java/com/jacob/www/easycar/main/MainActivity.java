package com.jacob.www.easycar.main;


import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.jacob.www.easycar.data.ChangeFragment;
import com.jacob.www.easycar.data.GarageBean;
import com.jacob.www.easycar.data.PayInfo;
import com.jacob.www.easycar.data.SearchSuggestionItem;
import com.jacob.www.easycar.data.User;
import com.jacob.www.easycar.greendao.UserDao;
import com.jacob.www.easycar.net.ResponseCons;
import com.jacob.www.easycar.overlay.DrivingRouteOverlay;
import com.jacob.www.easycar.util.DisplayUtil;
import com.jacob.www.easycar.util.ProgressDialogUtils;
import com.jacob.www.easycar.util.RxBus;
import com.jacob.www.easycar.util.SpUtil;
import com.jacob.www.easycar.widget.CircleImageView;
import com.jacob.www.easycar.widget.GarageImage;
import com.uuzuche.lib_zxing.activity.CaptureActivity;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;
import rx.Subscription;
import rx.functions.Action1;

/**
 * @author 周家豪
 */
public class MainActivity extends AppCompatActivity implements MainContract.View, AMapNaviListener, AMapNaviViewListener, AMap.OnMyLocationChangeListener, FloatingSearchView.OnQueryChangeListener, FloatingSearchView.OnSearchListener, Inputtips.InputtipsListener, GeocodeSearch.OnGeocodeSearchListener, RouteSearch.OnRouteSearchListener {
    @BindView(R.id.person_image)
    CircleImageView personImage;
    @BindView(R.id.setting_frame_layout)
    FrameLayout settingFrameLayout;

    //初始化setting
    /**
     * currentFragment
     * 0:settingFragment
     * 1:settingChangFragment
     */
    SharedPreferences spf;
    private int flag = 0;
    Fragment settingFragment = new SettingFragment();
    Fragment settingChangeFragment = new SettingChangFragment();
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
        spf=getSharedPreferences("judge",MODE_PRIVATE);
        gId = SpUtil.getString(this,"gId","");
        initView(savedInstanceState);
        initRxBus();
    }

    private void initRxBus() {
        RxBus.getDefault().toObservable(ChangeFragment.class)
                .subscribe(new Action1<ChangeFragment>() {
                    @Override
                    public void call(ChangeFragment changeFragment) {
                        MainActivity.this.changFragment(settingChangeFragment, settingFragment);
                    }
                });
        subscription =RxBus.getDefault().toObservable(PayInfo.class)
                .subscribe(new Action1<PayInfo>() {
                    @Override
                    public void call(PayInfo s) {
                        Log.e("TAG","call");
                        showPayInfo(s.getPayinfo());
                    }
                });
    }

    /**
     * 展示扣费信息
     * @param
     */
    private void showPayInfo(String info){
        Log.e("TAG","showPayInfo");
        new SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE)
                .setTitleText("扣款成功")
                .setContentText("根据您停车时间扣款"+info)
                .show();
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


        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ta = fm.beginTransaction();
        ta.add(R.id.setting_frame_layout, settingFragment).add(R.id.setting_frame_layout, settingChangeFragment);
        ta.hide(settingChangeFragment).show(settingFragment);
        ta.commit();

        UserDao userDao = App.getDaoSession().getUserDao();
        User user = userDao.loadAll().get(0);
        if (null != user) {
            Glide.with(this).load(ResponseCons.BASE_URL + user.getIcon()).into(personImage);
        }

     
    }

    private void changFragment(Fragment fromFragment, Fragment toFragment) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ta = fm.beginTransaction();
        ta.hide(fromFragment).show(toFragment).commit();
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
        options.setTilt(45);
        mAMapNaviView.setViewOptions(options);
        onInitNaviSuccess();
    }

    int dimens = 3;

    private void changDimens(AMapNaviView mAMapNaviView) {
        AMapNaviViewOptions options = mAMapNaviView.getViewOptions();
        if (dimens == 2) {
            options.setTilt(0);
            dimens = 3;
        } else if (dimens == 3) {
            options.setTilt(45);
            dimens = 2;
        }
        mAMapNaviView.setViewOptions(options);
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
        SharedPreferences.Editor editor=spf.edit();
        boolean needShowInfo =spf.getBoolean("needShowInfo",false);
        if(needShowInfo){
            showPayInfo(spf.getString("money","51元"));
        }
        editor.clear();
        editor.putBoolean("destroy",false);
        editor.commit();
    }

    @Override
    protected void onPause() {
        Log.e("TAG", "onPause");
        super.onPause();
        mAMapNaviView.onPause();
        mMapView.onPause();
    }

    @Override
    protected void onDestroy() {
        Log.e("TAG", "onDestroy");
        super.onDestroy();
        mAMapNaviView.onDestroy();
        if (mAMapNavi != null) {
            mAMapNavi.stopNavi();
            mAMapNavi.destroy();
        }
        mMapView.onDestroy();
        SharedPreferences.Editor editor=spf.edit();
        editor.putBoolean("destroy",true);
        editor.commit();
//        subscription.unsubscribe();
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

    boolean lotIsShow=false,lotClose=false;//第二个是当用户关闭自动弹出的lot后就不再自动弹出了
    @Override
    public void onNaviInfoUpdate(NaviInfo naviInfo) {
        this.naviInfo = naviInfo;
        Log.e(TAG, naviInfo.getPathRetainDistance() + "      " + naviInfo.getPathRetainTime());
        if(naviInfo.getPathRetainDistance()<100&&!lotIsShow&&!lotClose){
            presenter.getGarageLot(gId);
            lotIsShow=true;
            lotClose=true;
        }
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
        mAMapNavi.startNavi(NaviType.EMULATOR);
        isNavi = true;
    }

    boolean isNavi = false;

    @Override
    public void onBackPressed() {
        if (horizontalInfiniteCycleViewPager != null && horizontalInfiniteCycleViewPager.getVisibility() == View.INVISIBLE) {
            Toast.makeText(this, "已退出导航", Toast.LENGTH_SHORT).show();
            mSearchView.setVisibility(View.VISIBLE);
            setGarageId("0");
            isNavi = false;
            mAMapNavi.stopNavi();
            horizontalInfiniteCycleViewPager = null;
            mAMapNaviView.setVisibility(View.INVISIBLE);
            aMap.clear();
            myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATE);
            aMap.setMyLocationStyle(myLocationStyle);
            aMap.moveCamera(CameraUpdateFactory.zoomTo(nowZoom));
            mMapView.setVisibility(View.VISIBLE);
            lotClose=false;
        } else if (horizontalInfiniteCycleViewPager != null) {
            horizontalInfiniteCycleViewPager.setVisibility(View.INVISIBLE);
            horizontalInfiniteCycleViewPager = null;
            aMap.clear();
            myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATE);
            aMap.setMyLocationStyle(myLocationStyle);
            aMap.moveCamera(CameraUpdateFactory.zoomTo(nowZoom));
            lotClose=false;
        } else if (isNavi) {
            isNavi = false;
            mAMapNavi.stopNavi();
            mAMapNaviView.setVisibility(View.INVISIBLE);
            aMap.clear();
            myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATE);
            aMap.setMyLocationStyle(myLocationStyle);
            aMap.moveCamera(CameraUpdateFactory.zoomTo(nowZoom));
            mMapView.setVisibility(View.VISIBLE);
            lotClose=false;
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
        ProgressDialogUtils.getInstance().showProgress(this, getString(R.string.loading));
    }

    @Override
    public void hideProgress() {
        ProgressDialogUtils.getInstance().hideProgress();
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
            is = false;
            iis = false;
            //发起请求
        }
    }

    double desLat, desLon;


    public void getRealItem(double lon, double lat) {
        LatLonPoint mEndPoint = new LatLonPoint(lat, lon);
        RouteSearch.FromAndTo fromAndTo = new RouteSearch.FromAndTo(
                new LatLonPoint(myLatitude, myLongitude), mEndPoint);
        // 第一个参数表示路径规划的起点和终点，第二个参数表示驾车模式，第三个参数表示途经点，第四个参数表示避让区域，第五个参数表示避让道路
        RouteSearch.DriveRouteQuery query = new RouteSearch.DriveRouteQuery(fromAndTo, RouteSearch.DrivingDefault, null,
                null, "");
        // 异步路径规划驾车模式查询
        mRouteSearch.calculateDriveRouteAsyn(query);
    }

    boolean is = false;
    boolean iis = false;

    public void calculate(double lon, double lat) {
        is = true;
        LatLonPoint mEndPoint = new LatLonPoint(lat, lon);
        RouteSearch.FromAndTo fromAndTo = new RouteSearch.FromAndTo(
                new LatLonPoint(myLatitude, myLongitude), mEndPoint);
        // 第一个参数表示路径规划的起点和终点，第二个参数表示驾车模式，第三个参数表示途经点，第四个参数表示避让区域，第五个参数表示避让道路
        RouteSearch.DriveRouteQuery query = new RouteSearch.DriveRouteQuery(fromAndTo, RouteSearch.DrivingDefault, null,
                null, "");
        // 异步路径规划驾车模式查询
        mRouteSearch.calculateDriveRouteAsyn(query);
    }


    HorizontalInfiniteCycleViewPager horizontalInfiniteCycleViewPager;
    GarageBean bean;
    MainAdapter adapter;

    @Override
    public void showGarage(GarageBean garageBean) {
        Log.e(TAG, "showGarage");
        Log.e(TAG, "size" + garageBean.getData().size());
        if (garageBean.getData().size() == 0) {
            Toast.makeText(this, "附近无车库", Toast.LENGTH_SHORT).show();
            startNavi(desLat, desLon);
        } else {
            bean = garageBean;
            for (int i = 0; i < garageBean.getData().size(); i++) {
                getRealItem(garageBean.getData().get(i).getPositionLongitude(), garageBean.getData().get(i).getPositionLatitude());
            }
        }

    }


    @Override
    public void onBusRouteSearched(BusRouteResult busRouteResult, int i) {

    }

    List<Integer> diss = new ArrayList<>();
    List<Integer> times = new ArrayList<>();

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
                    //                    drivingRouteOverlay.setNodeIconVisibility(false);
                    //                    //是否用颜色展示交通拥堵情况，默认true
                    //                    drivingRouteOverlay.setIsColorfulline(true);
                    //                    drivingRouteOverlay.removeFromMap();
                    //                    drivingRouteOverlay.addToMap();
                    //                    drivingRouteOverlay.zoomToSpan();
                    int dis = (int) drivePath.getDistance();
                    int dur = (int) drivePath.getDuration();
                    Log.e("TAG", "外面层显示了");
                    if (iis) {
                        Log.e("TAG", "内面层显示了");
                        drivingRouteOverlay.setNodeIconVisibility(false);
                        //是否用颜色展示交通拥堵情况，默认true
                        drivingRouteOverlay.setIsColorfulline(true);
                        drivingRouteOverlay.removeFromMap();
                        drivingRouteOverlay.addToMap();
                        drivingRouteOverlay.zoomToSpan();
                    }
                    if (!is) {

                        if (!diss.contains(dis)) {
                            diss.add(dis);
                        }
                        if (!times.contains(dur)) {
                            times.add(dur);
                        }
                        if (bean.getData().size() == 0) {
                            Toast.makeText(this, "附近无车库", Toast.LENGTH_SHORT).show();
                            startNavi(desLat, desLon);
                        } else {
                            Log.e("TAG", "调用这个");
                            Log.e("TAG", times.size() + "   -----" + bean.getData().size());
                            if (times.size() == bean.getData().size()) {
                                iis = true;
                                Log.e("TAG", times.size() + "   -----" + bean.getData().size());
                                horizontalInfiniteCycleViewPager = (HorizontalInfiniteCycleViewPager) findViewById(R.id.hicvp);
                                if (horizontalInfiniteCycleViewPager.getVisibility() == View.INVISIBLE) {
                                    horizontalInfiniteCycleViewPager.setVisibility(View.VISIBLE);
                                }
                                List<Integer> diss2 = new ArrayList<>();
                                List<Integer> times2 = new ArrayList<>();
                                diss2.addAll(diss);
                                times2.addAll(times);
                                adapter = new MainAdapter(this, bean, horizontalInfiniteCycleViewPager, diss2, times2);
                                diss.clear();
                                times.clear();
                                Log.e("TAG,他的大小是", diss.size() + "---" + times.size());
                                horizontalInfiniteCycleViewPager.setAdapter(adapter);

                                //设置导航
                                adapter.setButtonItemClickListener(new MainAdapter.onButtonItemClickListener() {
                                    @Override
                                    public void startNavi(double lat, double lot) {
                                        Log.i(TAG, lat + " " + lot);
                                        MainActivity.this.startNavi(lat, lot);
                                        //隐藏
                                        mSearchView.setVisibility(View.INVISIBLE);
                                    }

                                    @Override
                                    public void setGarageId(String id) {
                                        MainActivity.this.setGarageId(id);
                                    }
                                });
                            }
                        }

                    }
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

    @OnClick({R.id.location, R.id.person_age, R.id.person_image, R.id.neighbor_garage, R.id.garage_info, R.id.capture})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.location:
                if (myLatitude != 0 && !isNavi) {
                    aMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(myLatitude, myLongitude), nowZoom));
                } else if (isNavi && mAMapNavi != null) {
                    //切换3D到2D
                    changDimens(mAMapNaviView);

                }
                break;
            case R.id.person_age:
                changBottomSheet(0);
                if (flag == 1) {
                    changFragment(settingChangeFragment, settingFragment);
                    flag = 0;
                }
                break;
            case R.id.person_image:
                if (flag == 0) {
                    changFragment(settingFragment, settingChangeFragment);
                    flag = 1;
                } else if (flag == 1) {
                    changFragment(settingChangeFragment, settingFragment);
                    flag = 0;
                }
                break;
            case R.id.neighbor_garage:
                if (!isNavi) {
                    is = false;
                    changBottomSheet(1);
                    presenter.getNearGarage(myLongitude, myLatitude, 2);
                }
                break;
            case R.id.garage_info:
                //得到二进制序列
                Log.i(TAG,gId);
                presenter.getGarageLot(gId);
                break;
            case R.id.capture:
                Intent intent = new Intent(this, CaptureActivity.class);
                startActivityForResult(intent, 1);
                break;
            default:
                break;

        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (null != data) {
                Bundle bundle = data.getExtras();
                if (bundle == null) {
                    return;
                }
                if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
                    String result = bundle.getString(CodeUtils.RESULT_STRING);
                    RxBus.getDefault().post(result);
                } else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
                    Toast.makeText(this, "扫描结果有误", Toast.LENGTH_SHORT).show();
                }
            }
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


    private String gId ;

    /**
     * 用来设置当前的车库的id
     */
    public void setGarageId(String gId) {
        this.gId = gId;
    }

 
    //展示车位

    @Override
    public void showLot(String lot) {
        int num = lot.length();
        LinearLayout dialogLinear = new LinearLayout(this);
        dialogLinear.setOrientation(LinearLayout.VERTICAL);
        dialogLinear.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        dialogLinear.setGravity(Gravity.CENTER);

        DisplayMetrics dm = this.getResources().getDisplayMetrics();
        LinearLayout.LayoutParams chidLp = new LinearLayout.LayoutParams(DisplayUtil.dip2px(60, dm.density), DisplayUtil.dip2px(60, dm.density));
        chidLp.setMargins(DisplayUtil.dip2px(8, dm.density), DisplayUtil.dip2px(8, dm.density), DisplayUtil.dip2px(8, dm.density), DisplayUtil.dip2px(8, dm.density));
        //一行4个
        int oneLineNum = 4;
        LinearLayout oneLineLinear = null;
        for (int i = 0; i < num; i++) {
            if (i % oneLineNum == 0) {
                oneLineLinear = new LinearLayout(this);
                oneLineLinear.setOrientation(LinearLayout.HORIZONTAL);
                oneLineLinear.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                dialogLinear.addView(oneLineLinear);
            }
            GarageImage garageImage = new GarageImage(this);
            garageImage.setLayoutParams(chidLp);
            garageImage.setText(i + 1 + "");
            garageImage.setTextSize(DisplayUtil.sp2px(18, dm.scaledDensity));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                garageImage.setFillColor(lot.toCharArray()[i] == '0' ? getColor(R.color.red) : getColor(R.color.green));
                garageImage.setWordColor(getColor(R.color.white));
            }
            if (oneLineLinear != null) {
                oneLineLinear.addView(garageImage);
            }
        }

        new AlertDialog.Builder(this)
                .setTitle("当前车库可用情况")
                .setView(dialogLinear)
                .setCancelable(true)
                .setPositiveButton("车库分布图", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        ImageView imageView = new ImageView(MainActivity.this);
                        imageView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                        Glide.with(MainActivity.this).load(ResponseCons.GARAGE_IMAGE + gId + ".png").into(imageView);
                        new AlertDialog.Builder(MainActivity.this)
                                .setTitle("当前车库分布图")
                                .setView(imageView)
                                .setCancelable(true)
                                .setPositiveButton("好的", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.dismiss();
                                    }
                                }).show();
                    }
                })
                .setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialogInterface) {
                        lotIsShow=false;
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .show();
    }

    @Override
    public void changeSuccess() {

    }
    Subscription subscription;

    @Override
    public void getGargetSuccess(int parkId) {
      
    }

    @Override
    public void addUserParkPositionSuccess(int parkId, String gId) {
        
    }



}
