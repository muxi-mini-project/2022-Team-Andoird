package com.example.team.home_page;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.RadioButton;


import com.example.team.R;

public class MyRadioButton extends androidx.appcompat.widget.AppCompatRadioButton {
    private Drawable drawable;
    public MyRadioButton(Context context) {
        super(context);
    }
    @SuppressLint("UseCompatLoadingForDrawables")
    public MyRadioButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MyRadioButton);//获取我们定义的属性
        drawable = typedArray.getDrawable(R.styleable.MyRadioButton_drawableTop);
        drawable.setBounds(0, 0, 20, 20);//图片大小
        this.setCompoundDrawables(null, drawable, null, null);//设置位置
    }
}

