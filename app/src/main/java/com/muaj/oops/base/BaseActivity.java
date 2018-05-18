package com.muaj.oops.base;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;

import com.muaj.oops.R;
import com.muaj.oops.databinding.ActivityBaseBinding;

/** Base Activity
 * @author muaj
 * @date 2018/5/14
 */

public abstract class BaseActivity<V extends ViewDataBinding> extends AppCompatActivity {

    protected V mBindingView;
    protected ActivityBaseBinding mBaseBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBaseBinding = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.activity_base, null, false);
        mBindingView = DataBindingUtil.inflate(getLayoutInflater(), getLayoutId(), null, false);
    }

    /**
     * 获取布局
     *
     * @return
     */
    public abstract @LayoutRes
    int getLayoutId();
}
