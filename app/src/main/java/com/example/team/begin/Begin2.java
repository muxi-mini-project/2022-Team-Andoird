package com.example.team.begin;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import androidx.annotation.Nullable;

import com.example.team.R;
import com.example.team.StatusBar;
import com.example.team.login.logining.LoginActivity;

public class Begin2 extends Fragment {
    private ImageButton mImageButton;
    private static Current mCurrent;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.begin2, container, false);
        mImageButton = view.findViewById(R.id.begin2);
        mImageButton.setBackgroundResource(R.mipmap.huang_jian_tou);
        mImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrent.add_current();
            }
        });
        return view;
    }

    public static void setCurrent2(Current current) {
        mCurrent = current;
    }
}
