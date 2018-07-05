package com.muaj.oops.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.muaj.lib.view.ClearEditText;
import com.muaj.oops.R;

/**
 * Created by muaj on 2018/7/5
 * TODO
 */
public class GameActivity extends AppCompatActivity{

    ClearEditText edit_X0;
    ClearEditText edit_Y0;
    ClearEditText edit_Xfrom;
    ClearEditText edit_Xto;
    ClearEditText edit_Xadd;
    ClearEditText edit_Yfrom;
    ClearEditText edit_Yto;
    ClearEditText edit_Yadd;
    ClearEditText edit_Morethan;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
    }
}
