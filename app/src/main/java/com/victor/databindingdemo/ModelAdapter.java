package com.victor.databindingdemo;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import ju.xposed.com.jumodle.BR;

/**
 * Created by benylwang on 2018/3/14.
 */

public class ModelAdapter extends BaseObservable {
    private User user;

//    @Bindable
//    public String getName = "hello";

    public ModelAdapter(User user) {
        this.user = user;
    }

    @Bindable
    public String getName(){
        return user != null ? user.getFullName(user.firstName, user.lastName) : null;
    }

    public void setFirstName(String name){
        if (user != null) user.firstName = name;
        notifyPropertyChanged(BR.name);
    }

    public void setLastName(String name){
        if (user != null) user.lastName = name;
        notifyPropertyChanged(BR.name);
    }
}
