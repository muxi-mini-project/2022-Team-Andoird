package com.example.team.login.logining;

//mvp中的model层，用来处理业务逻辑
public interface IUserModel {
    public void login(String student_id,String password,OnLoginListener listener);
}
