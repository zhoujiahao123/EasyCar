package com.jacob.www.easycar.main;

import android.widget.EditText;
import android.widget.Toast;

import com.jacob.www.easycar.R;
import com.jacob.www.easycar.base.App;
import com.jacob.www.easycar.base.BaseFragment;
import com.jacob.www.easycar.data.ChangeFragment;
import com.jacob.www.easycar.data.GarageBean;
import com.jacob.www.easycar.net.ResponseCons;
import com.jacob.www.easycar.util.RxBus;
import com.zxr.medicalaid.User;
import com.zxr.medicalaid.UserDao;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by 张兴锐 on 2017/12/9.
 */

public class SettingChangFragment extends BaseFragment implements MainContract.View {

    MainContract.Presenter mPresenter;
    String beforeUserName;
    String beforeCarNum;
    @BindView(R.id.userName)
    EditText userName;
    @BindView(R.id.car_num)
    EditText carNum;
    String uId;
    User user;
    Unbinder unbinder;

    @Override
    public int getLayoutId() {
        return R.layout.setting_change_view;
    }

    @Override
    public void getActivityData() {

    }

    @Override
    public void init() {
        mPresenter = new MainPresenter(this);
        beforeUserName = userName.getText().toString();
        beforeCarNum = carNum.getText().toString();

        UserDao userDao = App.getDaoSession().getUserDao();
        user = userDao.loadAll().get(0);
        userName.setText(user.getUserName());
//        carNum.setText();
        if (null != user) {
            uId = user.getUId();
        }
    }


    @OnClick(R.id.confirm_change)
    public void onViewClicked() {
        boolean hasChanged = false;
        String changeUserName = userName.getText().toString();
        String changeCarNum = carNum.getText().toString();
        if (!changeUserName.equals(beforeUserName)) {
            hasChanged = true;
            beforeUserName = changeUserName;
            mPresenter.changeInfo(uId, ResponseCons.TYPE_USER_NAME, changeUserName);
        }
        if (!changeCarNum.equals(beforeCarNum)) {
            hasChanged = true;
            beforeCarNum = changeCarNum;
            mPresenter.changeInfo(uId, ResponseCons.TYPE_PLATE_NUMBER, changeCarNum);
        }
        if (!hasChanged) {
            Toast.makeText(getContext(), "您当前尚未修改", Toast.LENGTH_SHORT).show();
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
        RxBus.getDefault().post(new ChangeFragment(userName.getText().toString(), carNum.getText().toString()));
    }

    @Override
    public void getGargetSuccess(int parkId) {
        
    }

    @Override
    public void addUserParkPositionSuccess(int parkId) {
        
    }

}

