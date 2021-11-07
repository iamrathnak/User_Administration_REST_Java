package com.admin.useradministration.model;

public class Right {

    private int rightid;
    private String Name;

    public int getRightid() {
        return rightid;
    }

    public void setRightid(int rightid) {
        this.rightid = rightid;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public RightsType getType() {
        return Type;
    }

    public void setType(RightsType type) {
        Type = type;
    }

    private RightsType Type;
}
