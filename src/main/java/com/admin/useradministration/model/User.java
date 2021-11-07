package com.admin.useradministration.model;

public class User {

    private String Firstname;
    private String IsFamilyMember;
    private String Lastname;
    private String Password;
    private int userid;
    private String Username;

    public RightsType getType() {
        return Type;
    }

    public void setType(RightsType type) {
        Type = type;
    }

    private RightsType Type;

    public String getFirstname() {
        return Firstname;
    }

    public void setFirstname(String firstname) {
        Firstname = firstname;
    }

    public String getIsFamilyMember() {
        return IsFamilyMember;
    }

    public void setIsFamilyMember(String isFamilyMember) {
        IsFamilyMember = isFamilyMember;
    }

    public String getLastname() {
        return Lastname;
    }

    public void setLastname(String lastname) {
        Lastname = lastname;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getUserType() {
        return UserType;
    }

    public void setUserType(String userType) {
        UserType = userType;
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

    private String UserType;
    public Right m_Right;
    public Group m_Group;

}
