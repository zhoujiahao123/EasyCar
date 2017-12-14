package com.jacob.www.easycar.main;

import com.jacob.www.easycar.base.App;
import com.jacob.www.easycar.data.GarageBean;
import com.jacob.www.easycar.data.GarageLotBean;
import com.jacob.www.easycar.data.User;
import com.jacob.www.easycar.data.UserParkVO;
import com.jacob.www.easycar.greendao.UserDao;
import com.jacob.www.easycar.net.LoadingCallBack;


/**
 * @author ASUS-NB
 * @date 2017/11/12
 */

public class MainPresenter implements MainContract.Presenter {
    Model model = new MainModelImpl();
    MainContract.View view;


    public MainPresenter(MainContract.View view) {
        this.view = view;
    }

    @Override
    public void getNearGarage(double longitude, double latitude, double distance) {
        model.getData(longitude, latitude, distance, new LoadingCallBack() {
            @Override
            public void loaded(Object data) {
                if (data instanceof GarageBean) {
                    view.showGarage((GarageBean) data);
                }
            }

            @Override
            public void error(String msg) {
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
        model.changeInfo(uId, type, value, new LoadingCallBack<User>() {
            @Override
            public void loaded(User data) {
                view.hideProgress();
                //数据处理
                view.changeSuccess();
                //更新数据库
                UserDao userDao = App.getDaoSession().getUserDao();
                User user = userDao.loadAll().get(0);
                user.setUsername(data.getUsername());
                String plateNums = "";
                for (int i = 0; i < data.getPlateNumberInfo().size(); i++) {
                    plateNums += data.getPlateNumberInfo().get(i) + ";";
                }
                plateNums = plateNums.substring(0, plateNums.length() - 1);
                user.setPlateNums(plateNums);
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
                view.addUserParkPositionSuccess(data.getUserPark().getParkId(),data.getUserPark().getGarageId());
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
