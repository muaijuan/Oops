package com.muaj.oops.component;

import com.muaj.oops.MainActivity;
import com.muaj.oops.module.ActivityModule;

import dagger.Component;

/**
 * @author muaj
 * @date 2018/5/15
 */
@Component(modules = {ActivityModule.class})
public interface MainComponent {
    void inject(MainActivity activity);
}
