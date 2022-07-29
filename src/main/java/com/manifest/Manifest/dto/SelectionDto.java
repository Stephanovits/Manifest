package com.manifest.Manifest.dto;


import java.util.ArrayList;
import java.util.List;

public class SelectionDto {

    List<SelectionAttribute> wardList;
    Boolean bool1;
    Boolean bool2;

    @Override
    public String toString() {
        return "SelectionDto{" +
                "wardList=" + wardList +
                ", bool1=" + bool1 +
                ", bool2=" + bool2 +
                '}';
    }

    public List<SelectionAttribute> getWardList() {
        return wardList;
    }

    public void setWardList(List<SelectionAttribute> wardList) {
        this.wardList = wardList;
    }

    public Boolean getBool1() {
        return bool1;
    }

    public void setBool1(Boolean bool1) {
        this.bool1 = bool1;
    }

    public Boolean getBool2() {
        return bool2;
    }

    public void setBool2(Boolean bool2) {
        this.bool2 = bool2;
    }
}
