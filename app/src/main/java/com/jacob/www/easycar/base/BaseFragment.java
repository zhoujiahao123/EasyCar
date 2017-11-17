package com.jacob.www.easycar.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.jacob.www.easycar.util.ProgressDialogUtils;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by 张兴锐 on 2017/11/15.
 */

public abstract class BaseFragment extends Fragment implements BaseView{

    Unbinder unbinder;
    protected Bundle args;
    protected String TAG;
    public abstract int getLayoutId();
    public abstract void getActivityData();
    public abstract void init();
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TAG = this.getClass().getSimpleName();
        if(getArguments() != null){
            args = getArguments();
            getActivityData();

        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutId(), container, false);
        unbinder = ButterKnife.bind(this, view);
        init();
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
//        unbinder.unbind();
    }
    @Override
    public void showProgress() {
        ProgressDialogUtils.getInstance().showProgress(getContext(),"加载中，请稍后...");
    }

    @Override
    public void hideProgress() {
        ProgressDialogUtils.getInstance().hideProgress();
    }

    @Override
    public void showMsg(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }

  
}
