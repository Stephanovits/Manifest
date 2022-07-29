package com.manifest.Manifest.dto;


import java.util.ArrayList;
import java.util.List;

public class SelectionDto {

    public enum Sort {
        WARD, EXAMINATION
    }

    List<SelectionAttribute> wardList;
    List<SelectionAttribute> examinationList;
    Boolean incCompletedJobs;
    Sort sort;

    @Override
    public String toString() {
        return "SelectionDto{" +
                "wardList=" + wardList +
                ", examinationList=" + examinationList +
                ", incCompletedJobs=" + incCompletedJobs +
                ", sort=" + sort +
                '}';
    }

    public Sort getSort() {
        return sort;
    }

    public void setSort(Sort sort) {
        this.sort = sort;
    }

    public List<SelectionAttribute> getWardList() {
        return wardList;
    }

    public void setWardList(List<SelectionAttribute> wardList) {
        this.wardList = wardList;
    }

    public List<SelectionAttribute> getExaminationList() {
        return examinationList;
    }

    public void setExaminationList(List<SelectionAttribute> examinationList) {
        this.examinationList = examinationList;
    }

    public Boolean getIncCompletedJobs() {
        return incCompletedJobs;
    }

    public void setIncCompletedJobs(Boolean incCompletedJobs) {
        this.incCompletedJobs = incCompletedJobs;
    }
}
