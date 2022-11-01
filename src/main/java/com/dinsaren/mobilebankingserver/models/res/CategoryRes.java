package com.dinsaren.mobilebankingserver.models.res;

import com.dinsaren.mobilebankingserver.models.Category;

import java.util.List;

public class CategoryRes {
    private List<Category> menuList;
    private List<Category> promotionList;
    private List<Category> applicationList;

    public List<Category> getMenuList() {
        return menuList;
    }

    public void setMenuList(List<Category> menuList) {
        this.menuList = menuList;
    }

    public List<Category> getPromotionList() {
        return promotionList;
    }

    public void setPromotionList(List<Category> promotionList) {
        this.promotionList = promotionList;
    }

    public List<Category> getApplicationList() {
        return applicationList;
    }

    public void setApplicationList(List<Category> applicationList) {
        this.applicationList = applicationList;
    }
}
