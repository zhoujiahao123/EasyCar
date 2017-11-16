package com.jacob.www.easycar.main;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jacob.www.easycar.R;
import com.jacob.www.easycar.base.BaseFragment;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ASUS-NB on 2017/11/16.
 */

public class ForthFragment extends BaseFragment {
    @Override
    public int getLayoutId() {
        return R.layout.fragment_route;
    }

    @Override
    public void getActivityData() {
        
    }
    

    @Override
    public void init() {

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
        activity.startNavi(29.568711, 106.550721);
    }
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = (MainActivity) activity;
    }
}
