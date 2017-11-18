package com.jacob.www.easycar.main;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.jacob.www.easycar.R;
import com.jacob.www.easycar.data.GarageBean;

/**
 * Created by ASUS-NB on 2017/11/16.
 */

public class MainAdapter extends PagerAdapter {

    private MainActivity activity;
    private LayoutInflater mLayoutInflater;
    private GarageBean bean;

    private TextView tvFreeLot,tvTotalLot,tvDesTime,tvDesKm,tvName;
    private Button btnNavi;
    public MainAdapter(Context context, GarageBean bean) {
        activity = (MainActivity) context;
        mLayoutInflater = LayoutInflater.from(context);
        this.bean = bean;
    }

    @Override
    public int getCount() {
        return 5;
    }

    @Override
    public int getItemPosition(final Object object) {
        return POSITION_NONE;
    }

    @Override
    public Object instantiateItem(final ViewGroup container, final int position) {
        final View view;
        view = mLayoutInflater.inflate(R.layout.fragment_route,container,false);
        tvDesKm = view.findViewById(R.id.tv_des_km);
        tvDesTime = view.findViewById(R.id.tv_des_time);
        tvFreeLot = view.findViewById(R.id.tv_des_free);
        tvTotalLot = view.findViewById(R.id.tv_des_total);
        tvName = view.findViewById(R.id.tv_des_name);
        btnNavi = view.findViewById(R.id.btn_start_navi);
        activity.getRealItem(bean.getData().get(0).getPositionLongitude(),bean.getData().get(0).getPositionLatitude());
        btnNavi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.startNavi(bean.getData().get(0).getPositionLatitude(),bean.getData().get(0).getPositionLongitude());
            }
        });
        tvName.setText(bean.getData().get(0).getGarageName());
        tvTotalLot.setText("车库总车位："+bean.getData().get(0).getParkingLotCount());
        tvFreeLot.setText("目前剩余车位："+bean.getData().get(0).getFreeParkingLotCount());
        container.addView(view);
        return view;
    }

    @Override
    public boolean isViewFromObject(final View view, final Object object) {
        return view.equals(object);
    }

    @Override
    public void destroyItem(final ViewGroup container, final int position, final Object object) {
        container.removeView((View) object);
    }
}
