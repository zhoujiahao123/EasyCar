package com.jacob.www.easycar.main;

import android.util.Log;

import com.jacob.www.easycar.base.App;
import com.jacob.www.easycar.data.GarageBean;
import com.jacob.www.easycar.data.GarageLotBean;
import com.jacob.www.easycar.data.UserBean;
import com.jacob.www.easycar.data.UserParkVO;
import com.jacob.www.easycar.net.LoadingCallBack;
import com.zxr.medicalaid.User;
import com.zxr.medicalaid.UserDao;

/**
 * @author ASUS-NB
 * @date 2017/11/12
 */

public class MainPresenter implements MainContract.Presenter {
    Model model = new MainModelImpl();
    MainContract.View view;
    UserDao userDao = App.getDaoSession().getUserDao();

    public MainPresenter(MainContract.View view) {
        this.view = view;
    }

    @Override
    public void getNearGarage(double longitude, double latitude, double distance) {
        model.getData(longitude, latitude, distance, new LoadingCallBack() {
            @Override
            public void loaded(Object data) {
                Log.e("getNearGarage", "搜索车库成功");
                if (data instanceof GarageBean) {
                    view.showGarage((GarageBean) data);
                }
            }

            @Override
            public void error(String msg) {
                Log.e("getNearGarage", "失败了");
                view.showMsg(msg);
            }
        });
    }

    @Override
    public void start(double longitude, double latitude, double distance) {
        getNearGarage(longitude, latitude, distance);
    }

    @Override
    public void getGarageLot(String gId) {
        view.showProgress();
        model.getLot(gId, new LoadingCallBack() {
            @Override
            public void loaded(Object data) {
                view.hideProgress();
                if (data instanceof GarageLotBean) {
                    view.showLot(((GarageLotBean) data).getData().getParkingLotInfo());
                }
            }

            @Override
            public void error(String msg) {
                view.showMsg(msg);
                view.hideProgress();
            }
        });
    }

    @Override
    public void changeInfo(String uId, String type, String value) {
        view.showProgress();
        model.changeInfo(uId, type, value, new LoadingCallBack<UserBean>() {
            @Override
            public void loaded(UserBean data) {
                view.hideProgress();
                //数据处理
                view.changeSuccess();
                User user = userDao.loadAll().get(0);
                user.setPhoneNum(data.getPhone());
                user.setUId(data.getUid());
                user.setUserName(data.getUsername());
                userDao.update(user);
            }

            @Override
            public void error(String msg) {
                view.hideProgress();
                view.showMsg(msg);
            }
        });
    }

    @Override
    public void getGargetResult(String uId) {
        view.showProgress();
        model.getGarget(uId, new LoadingCallBack<UserParkVO>() {
            @Override
            public void loaded(UserParkVO data) {
                view.hideProgress();
                view.getGargetSuccess(data.getUserPark().getParkId());
            }

            @Override
            public void error(String msg) {
                view.showMsg(msg);
                view.hideProgress();
            }
        });
    }

    @Override
    public void addUserPosition(String uId, String garageId, String parkId) {
        view.showProgress();
        model.addUserParkPosition(uId, garageId, parkId, new LoadingCallBack<UserParkVO>() {
            @Override
            public void loaded(UserParkVO data) {
                view.hideProgress();
                view.addUserParkPositionSuccess(data.getUserPark().getParkId());
            }

            @Override
            public void error(String msg) {
                view.showMsg(msg);
                view.hideProgress();
            }
        });
    }

    @Override
    public void deletePark(String uId) {
        view.showProgress();
        model.deletePark(uId, new LoadingCallBack() {
            @Override
            public void loaded(Object data) {
                view.hideProgress();
                view.showMsg("已经取消停靠,祝您一路顺风");
            }

            @Override
            public void error(String msg) {
                view.hideProgress();
                view.showMsg(msg);
            }
        });
    }

    @Override
    public void start(String... args) {

    }
}
