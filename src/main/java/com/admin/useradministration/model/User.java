package com.admin.useradministration.model;

import java.io.Serializable;

public class User implements Serializable {

    private String firstname;
    private String isFamilyMember;
    private String lastname;
    private String password;

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getIsFamilyMember() {
        return isFamilyMember;
    }

    public void setIsFamilyMember(String isFamilyMember) {
        this.isFamilyMember = isFamilyMember;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public Right getM_Right() {
        return m_Right;
    }

    public void setM_Right(Right m_Right) {
        this.m_Right = m_Right;
    }

    public Group getM_Group() {
        return m_Group;
    }

    public void setM_Group(Group m_Group) {
        this.m_Group = m_Group;
    }

    public RightsType getType() {
        return Type;
    }

    public void setType(RightsType type) {
        Type = type;
    }

    private int userid;
    private String username;
    private String userType;
    public Right m_Right;
    public Group m_Group;
    private RightsType Type;



}
