package com.example.androidproject08;

public interface MainCallbacks {
    public void onMsgFromFragToMain(String sender, String strValue);
    public void onObjectFromFragToMain(String sender, Object value);
}