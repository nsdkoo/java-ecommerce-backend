package com.app.agent.wssdk;

public interface XMessageObserver {

    public void onIMMessage(String message);

    public void onIMError(String message);

}
