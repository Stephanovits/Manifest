package com.manifest.Manifest.dto;

public class SelectionAttribute {

    String attributeName;
    Boolean selected;

    @Override
    public String toString() {
        return "SelectionAttribute{" +
                "attributeName='" + attributeName + '\'' +
                ", selected=" + selected +
                '}';
    }

    public String getAttributeName() {
        return attributeName;
    }

    public void setAttributeName(String attributeName) {
        this.attributeName = attributeName;
    }

    public Boolean getSelected() {
        return selected;
    }

    public void setSelected(Boolean selected) {
        this.selected = selected;
    }
}
