package com.example.team.login.logining;

public interface OnLoginListener {
    //登陆的回调方法
    public void loginSuccess(LoginUser.Data student);
    public void loginFailed();
}
