package com.jacob.www.easycar.main;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.jacob.www.easycar.R;
import com.jacob.www.easycar.base.App;
import com.jacob.www.easycar.base.BaseFragment;
import com.jacob.www.easycar.data.ChangeFragment;
import com.jacob.www.easycar.login.LogInActivity;
import com.jacob.www.easycar.util.RxBus;
import com.jacob.www.easycar.util.SpUtil;
import com.jacob.www.easycar.util.ToActivityUtil;
import com.zxr.medicalaid.User;
import com.zxr.medicalaid.UserDao;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Subscription;
import rx.functions.Action1;

/**
 * Created by 张兴锐 on 2017/12/9.
 */

public class SettingFragment extends BaseFragment {
    @BindView(R.id.userName)
    TextView userName;
    @BindView(R.id.phone_num)
    TextView phoneNum;
    @BindView(R.id.car_num)
    TextView carNum;
    @BindView(R.id.park_id)
    TextView parkId;


    @Override
    public int getLayoutId() {
        return R.layout.setting_view;
    }

    @Override
    public void getActivityData() {

    }

    private final String PARK_ID = "park_id";

    @Override
    public void init() {
        //用戶信息
        UserDao userDao = App.getDaoSession().getUserDao();
        User user = userDao.loadAll().get(0);
        userName.setText(user.getUserName());
        phoneNum.setText("" + user.getPhoneNum());
        carNum.setText(getString(R.string.car_num_test));
        String park_id = SpUtil.getString(getActivity(), PARK_ID, "");
        if ("".equals(park_id)) {
            parkId.setText("当前未停车");
        } else {
            parkId.setText(park_id + "号");
        }

        initRxBus();
    }

    private Subscription subscription1;
    private Subscription subscription2;

    private void initRxBus() {
        subscription1 = RxBus.getDefault().toObservable(String.class)
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        String result = s;
                        Log.i(TAG, result + "");
                        //拿到车位号
                        if ("".equals(SpUtil.getString(getActivity(), PARK_ID, ""))) {
                            Toast.makeText(getActivity(), "停车号" + result + "号", Toast.LENGTH_SHORT).show();
                            //说明没有停车
                            SpUtil.putString(getActivity(), PARK_ID, result);
                            //更新ui
                            parkId.setText(result + "号");
                        } else if (result.equals(SpUtil.getString(getActivity(), PARK_ID, ""))) {
                            Toast.makeText(getActivity(), "您已成功取消停车", Toast.LENGTH_SHORT).show();
                            //说明已经停过车，并且扫描的是同一个二维码
                            SpUtil.putString(getActivity(), PARK_ID, "");
                            parkId.setText("当前未停车");
                        } else {
                            //说明用户扫描错误
                            Toast.makeText(getActivity(), "当前扫描的二维码不是这个车位的二维码哦，请找到正确的二维码并重新扫描", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        subscription2 = RxBus.getDefault().toObservable(ChangeFragment.class)
                .subscribe(new Action1<ChangeFragment>() {
                    @Override
                    public void call(ChangeFragment changeFragment) {
                        userName.setText(changeFragment.userName);
                        carNum.setText(changeFragment.carNum);
                    }
                });
    }


    @OnClick(R.id.log_off)
    public void onViewClicked() {
        new AlertDialog.Builder(getActivity())
                .setTitle("提示")
                .setMessage("您确定要退出吗")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        SpUtil.putBoolean(getActivity(), "has_login", false);
                        App.getDaoSession().getUserDao().deleteAll();
                        ToActivityUtil.toNextActivityAndFinish(getActivity(), LogInActivity.class);
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (null != subscription1) {
            subscription1 = null;
        }
        if (null != subscription2) {
            subscription2 = null;
        }
    }
}
