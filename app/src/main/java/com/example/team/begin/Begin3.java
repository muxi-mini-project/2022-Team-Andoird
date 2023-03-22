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
    private ImageButton btn_next;
    private Button btn_login;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.begin3,container,false);
        btn_next = (ImageButton) view.findViewById(R.id.btn_next);
        btn_login = (Button)view.findViewById(R.id.btn_enter);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
                LoginActivity.actionStart(getActivity());
            }
        });
        return view;
    }
}


