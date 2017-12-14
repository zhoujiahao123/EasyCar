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
import com.jacob.www.easycar.data.GarageBean;
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
 * @author 张兴锐
 * @date 2017/12/9
 */

public class SettingFragment extends BaseFragment implements MainContract.View {
    @BindView(R.id.userName)
    TextView userName;
    @BindView(R.id.phone_num)
    TextView phoneNum;
    @BindView(R.id.car_num)
    TextView carNum;
    @BindView(R.id.park_id)
    TextView parkIdTv;


    MainPresenter mainPresenter;

    @Override
    public int getLayoutId() {
        return R.layout.setting_view;
    }

    @Override
    public void getActivityData() {

    }

    private final String PARK_ID = "park_id";
    User user;

    @Override
    public void init() {
        mainPresenter = new MainPresenter(this);
        //用戶信息
        UserDao userDao = App.getDaoSession().getUserDao();
        user = userDao.loadAll().get(0);
        userName.setText(user.getUserName());
        phoneNum.setText("" + user.getPhoneNum());
        carNum.setText(getString(R.string.car_num_test));
        String park_id = SpUtil.getString(getContext(), PARK_ID, "");
        if ("".equals(park_id)) {
            parkIdTv.setText("当前未停车");
            mainPresenter.getGargetResult(user.getUId());
        } else {
            parkIdTv.setText(park_id + "号");
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
                        if (!s.contains(" ") || (s.contains(" ") && s.split(" ").length != 2)) {
                            Toast.makeText(getContext(), "该二维码无效", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        String[] result = s.split(" ");
                        String gId = result[0];
                        String pId = result[1];
                        Log.i(TAG, s);
                        //拿到车位号
                        if ("".equals(SpUtil.getString(getContext(), PARK_ID, ""))) {
                            //向网络请求
                            //停车
                            mainPresenter.addUserPosition(user.getUId(), gId, pId);
                        } else if (pId.equals(SpUtil.getString(getContext(), PARK_ID, ""))) {
                            //取车
                            //网络请求
                            mainPresenter.deletePark(user.getUId());
                        } else {
                            //说明用户扫描错误
                            Toast.makeText(getContext(), "当前扫描的二维码不是这个车位的二维码哦，请找到正确的二维码并重新扫描", Toast.LENGTH_SHORT).show();
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
        new AlertDialog.Builder(getContext())
                .setTitle("提示")
                .setMessage("您确定要退出吗")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        SpUtil.putBoolean(getContext(), "has_login", false);
                        App.getDaoSession().getUserDao().deleteAll();
                        ToActivityUtil.toNextActivityAndFinish(getContext(), LogInActivity.class);
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

    @Override
    public void showGarage(GarageBean garageBeans) {

    }

    @Override
    public void showLot(String lot) {

    }

    @Override
    public void changeSuccess() {

    }

    @Override
    public void getGargetSuccess(int pId) {
        SpUtil.putString(getContext(), PARK_ID, pId + "");
        parkIdTv.setText(pId + "号");
    }

    @Override
    public void addUserParkPositionSuccess(int pId) {
        Toast.makeText(getContext(), "成功停靠", Toast.LENGTH_SHORT).show();
        SpUtil.putString(getContext(), PARK_ID, pId + "");
        parkIdTv.setText(pId + "号");
    }

    @Override
    public void showMsg(String msg) {
        if ("已经取消停靠,祝您一路顺风".equals(msg)) {
            SpUtil.putString(getContext(), PARK_ID, "");
            parkIdTv.setText("当前未停车");
        }
        super.showMsg(msg);

    }
}
