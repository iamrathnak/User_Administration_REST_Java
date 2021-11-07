package com.admin.useradministration.model;

public class Group {
    private int groupid;
    private String Name;
    private GroupType Type;
    public int getGroupid() {
        return groupid;
    }

    public void setGroupid(int groupid) {
        this.groupid = groupid;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public GroupType getType() {
        return Type;
    }

    public void setType(GroupType type) {
        Type = type;
    }

}
