package com.example.team.user.presenter;

import android.util.Log;

import com.example.team.login.logining.LoginUser;
import com.example.team.user.model.UserModel;
import com.example.team.user.model.requestListener;
import com.example.team.user.view.IUserView;

public class UserPresenter {
    private UserModel userModel;
    private IUserView userView;

    public UserPresenter(IUserView view){
        this.userModel = new UserModel();
        //userModel.getUserinfo();
        this.userView = view;
    }

    public void initUserInfo(String token){

        userModel.getUserinfo(token,new requestListener() {
            @Override
            public void onSuccess() {
//                try{
//
//                }
                Log.d("UserPresenter",""+(userModel.getUser()==null));
                //LoginUser user = userModel.getUserinfo()
//                LoginUser.Data data = userModel.getUser().getData();
//                userView.initNickname(data.getNickname());
                LoginUser user = userModel.getUser();
                userView.initNickname(user.getData().getNickname());
            }

            @Override
            public void onFailed() {

            }
        });

        //userView.initAvatar();
    }


}
