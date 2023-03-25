package com.example.team.user.view;

import android.content.Context;
import android.net.Uri;

import java.io.File;

import retrofit2.http.Url;

public interface IUserView {
    void initNickname(String name);
    void initAvatar(String url);
    String getNickname();
    Uri getImageUri();
    Context getContext();
}
