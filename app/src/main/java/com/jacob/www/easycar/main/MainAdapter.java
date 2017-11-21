package com.jacob.www.easycar.main;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.gigamole.infinitecycleviewpager.HorizontalInfiniteCycleViewPager;
import com.jacob.www.easycar.R;
import com.jacob.www.easycar.data.GarageBean;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ASUS-NB
 * @date 2017/11/16
 */

public class MainAdapter extends PagerAdapter {

    private MainActivity activity;
    private LayoutInflater mLayoutInflater;
    private GarageBean bean;
    private HorizontalInfiniteCycleViewPager horizontalInfiniteCycleViewPager;
    private TextView tvFreeLot, tvTotalLot, tvDesTime, tvDesKm, tvName;
    private Button btnNavi;
    List<Integer> diss  = new ArrayList<>();
    List<Integer> times = new ArrayList<>();
    public MainAdapter(Context context, GarageBean bean,HorizontalInfiniteCycleViewPager horizontalInfiniteCycleViewPager,List<Integer> diss,List<Integer> times) {

        activity = (MainActivity) context;
        mLayoutInflater = LayoutInflater.from(context);
        this.bean = bean;
        this.horizontalInfiniteCycleViewPager = horizontalInfiniteCycleViewPager;
        this.diss = diss;
        this.times = times;
    }

    public interface onButtonItemClickListener {
        void startNavi(double lat, double lot);

        void setGarageId(String id);
    }

    public interface onDriveRouteGenerate {
        void generate();
    }


    private onButtonItemClickListener buttonItemClickListener;

    public void setButtonItemClickListener(onButtonItemClickListener buttonItemClickListener) {
        this.buttonItemClickListener = buttonItemClickListener;
    }

    @Override
    public int getCount() {
        return bean.getData().size();
    }

    @Override
    public int getItemPosition(final Object object) {
        return POSITION_NONE;
    }


    @Override
    public Object instantiateItem(View container, int position) {
        return super.instantiateItem(container, position);
    }

    @Override
    public Object instantiateItem(final ViewGroup container, final int position) {
        final View view;
        view = mLayoutInflater.inflate(R.layout.fragment_route, container, false);
        tvDesKm = view.findViewById(R.id.tv_des_km);
        tvDesTime = view.findViewById(R.id.tv_des_time);
        tvFreeLot = view.findViewById(R.id.tv_des_free);
        tvTotalLot = view.findViewById(R.id.tv_des_total);
        tvName = view.findViewById(R.id.tv_des_name);
        btnNavi = view.findViewById(R.id.btn_start_navi);
        activity.calculate(bean.getData().get(horizontalInfiniteCycleViewPager.getRealItem()).getPositionLongitude(),bean.getData().get(horizontalInfiniteCycleViewPager.getRealItem()).getPositionLatitude());
        btnNavi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buttonItemClickListener.setGarageId(bean.getData().get(position).getGarageId());
                buttonItemClickListener.startNavi(bean.getData().get(position).getPositionLatitude(), bean.getData().get(position).getPositionLongitude());

            }
        });
        tvName.setText(bean.getData().get(position).getGarageName());
        tvTotalLot.setText("车库总车位："+bean.getData().get(position).getParkingLotCount());
        tvFreeLot.setText("目前剩余车位："+bean.getData().get(position).getFreeParkingLotCount());
        tvDesTime.setText("时间："+times.get(position)/60+"分钟");
        tvDesKm.setText("距离："+((double)diss.get(position))/1000+"公里");
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
