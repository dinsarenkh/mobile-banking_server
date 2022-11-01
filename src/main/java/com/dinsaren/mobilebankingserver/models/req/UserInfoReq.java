package com.dinsaren.mobilebankingserver.models.req;

public class UserInfoReq {
    private String userAccountId;
    private String deviceId;

    public String getUserAccountId() {
        return userAccountId;
    }

    public void setUserAccountId(String userAccountId) {
        this.userAccountId = userAccountId;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    @Override
    public String toString() {
        return "UserInfoReq{" +
                "userAccountId='" + userAccountId + '\'' +
                ", deviceId='" + deviceId + '\'' +
                '}';
    }
}
