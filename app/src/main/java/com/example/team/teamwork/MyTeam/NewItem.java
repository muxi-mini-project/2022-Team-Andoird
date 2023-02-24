package com.example.team.teamwork.MyTeam;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.team.R;
import com.example.team.StatusBar;

public class NewItem extends StatusBar {

    private ImageButton mReturn_Button;
    private ImageButton mFinish_Button;
    private EditText mItem_Name;
    private EditText mItem_Introduction;
    private static final String TAG = "NewItem";

    @Override
    protected void onCreate(Bundle savedInstanceState){
        StatusBar_to_transparent(this);
        //状态栏文字自适应
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_item);

        mReturn_Button = (ImageButton) findViewById(R.id.ni_ib1);
        mReturn_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NewItem.this.finish();
            }
        });

        mFinish_Button = (ImageButton) findViewById(R.id.ni_ib2);
        mFinish_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(NewItem.this, "创建成功", Toast.LENGTH_SHORT).show();
                NewItem.this.finish();
            }
        });

        mItem_Name = (EditText) findViewById(R.id.ni_et1);
        mItem_Name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mItem_Introduction = (EditText) findViewById(R.id.ni_et2);
        mItem_Introduction.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }


}
