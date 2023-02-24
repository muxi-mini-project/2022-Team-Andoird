package com.example.team.begin;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import androidx.annotation.Nullable;

import com.example.team.R;
import com.example.team.StatusBar;
import com.example.team.login.logining.LoginActivity;

public class Begin3 extends Fragment {
    private ImageButton mImageButton;
    private Button mButton;
    private Current mCurrent;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.begin3,container,false);
        mImageButton=(ImageButton) view.findViewById(R.id.begin3);
        mImageButton.setBackgroundResource(R.mipmap.huang_jian_tou);

        mButton=(Button)view. findViewById(R.id.begin4);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 Intent intent=new Intent(getActivity(),LoginActivity.class);
                 startActivity(intent);
            }
        });
        return view;
    }
    public  void setCurrent(Current current){
        mCurrent=current;
    }
}


