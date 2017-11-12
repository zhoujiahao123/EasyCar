package com.jacob.www.easycar.main;

/**
 * Created by ASUS-NB on 2017/11/12.
 */

public class MainPresenter implements MainContract.Presenter {
    Model model = new MainModelImpl();
    MainContract.View view ;
    public MainPresenter(MainContract.View view){
        view.setPresenter(this);
        this.view = view;
    }


    @Override
    public void loadSome() {
        model.getData(new Model.LoadingCallBack() {
            @Override
            public void onLoaded() {
                view.showMsg();
            }
        });
    }

    @Override
    public void start(String... args) {

    }
}
