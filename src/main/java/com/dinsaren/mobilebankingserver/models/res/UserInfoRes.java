package com.dinsaren.mobilebankingserver.models.res;

import com.dinsaren.mobilebankingserver.models.User;
import com.dinsaren.mobilebankingserver.models.UserAccount;

import java.util.List;

public class UserInfoRes {
    private User user;
    private List<UserAccount> userAccounts;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<UserAccount> getUserAccounts() {
        return userAccounts;
    }

    public void setUserAccounts(List<UserAccount> userAccounts) {
        this.userAccounts = userAccounts;
    }

    @Override
    public String toString() {
        return "UserInfoRes{" +
                "user=" + user +
                ", userAccounts=" + userAccounts +
                '}';
    }
}
