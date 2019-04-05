package com.example.myapplication;

import com.example.myapplication.Data.UserData;

public interface Interfacecall {
    public void insert(UserData udata);
    public void update(int index, UserData udata);
    public void delete(int index);
    public int returnva(int idx);

}
