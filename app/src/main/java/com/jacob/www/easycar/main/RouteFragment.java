package com.jacob.www.easycar.main;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jacob.www.easycar.R;
import com.jacob.www.easycar.base.BaseFragment;
import com.jacob.www.easycar.data.GarageBean;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ASUS-NB on 2017/11/16.
 */

public class RouteFragment extends BaseFragment{
    @BindView(R.id.tv_des_name)
    TextView tvDesName;
    @BindView(R.id.tv_des_time)
    TextView tvDesTime;
    @BindView(R.id.tv_des_km)
    TextView tvDesKm;
    @BindView(R.id.tv_des_total)
    TextView tvDesTotal;
    @BindView(R.id.tv_des_free)
    TextView tvDesFree;
    String name ;
    int freeLot;
    int totalLot;
    double lat,lon;
    @Override
    public int getLayoutId() {
        return R.layout.fragment_route;
    }

    @Override
    public void getActivityData() {
        name = args.getString("garageName");
        freeLot  = args.getInt("freeGarageLot");
        totalLot = args.getInt("garageLot");
        lat = args.getDouble("lat");
        lon  = args.getDouble("long");
    }

    @Override
    public void init() {
        tvDesName.setText(name);
        tvDesTotal.setText(totalLot);
        tvDesFree.setText(freeLot);
    }

    MainActivity activity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @OnClick(R.id.btn_start_navi)
    public void onClick() {
        activity.startNavi(lat, lon);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = (MainActivity) activity;
    }


}
