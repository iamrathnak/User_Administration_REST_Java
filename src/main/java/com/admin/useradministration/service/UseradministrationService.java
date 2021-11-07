package com.admin.useradministration.service;


import com.admin.useradministration.memory.UserAdminstrationMemory;
import com.admin.useradministration.model.Group;
import com.admin.useradministration.model.Right;
import com.admin.useradministration.model.User;
import com.admin.useradministration.model.UserAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@Service
public class UseradministrationService {

@Autowired
private UserAdminstrationMemory userAdminstrationMemory;

    public String addUser( String loggedInUserName,User user){
        userAdminstrationMemory.addUser(loggedInUserName,user);
        return "ok";
    }




    public String userAuthentication(UserAuthentication userAuthentication) {
        return userAdminstrationMemory.userAuthentication(userAuthentication)?"ok":"error";
    }

    public String deleteUser(String loggedInUserName,String userName) {
        userAdminstrationMemory.deleteUser(loggedInUserName,userName);
        return "ok";
    }

    public String deleteUserGroup(String loggedInUserName,String userName,int groupId) {
        userAdminstrationMemory.deleteUserGroup(loggedInUserName,userName,groupId);
        return "ok";
    }

    public String addUserGroup(String loggedInUserName, String userName, Group group) {
        userAdminstrationMemory.assignUserToGroup(loggedInUserName,userName,group);

        return  "ok";
    }

    public String deleteUserRight(String loggedInUserName,String userName,int rightId) {
        userAdminstrationMemory.deleteUserRight(loggedInUserName,userName,rightId);
        return "ok";
    }
    public String deleteUserGroupRight(String loggedInUserName,String userName,int rightId,int groupId) {
        userAdminstrationMemory.deleteUserGroupRight(loggedInUserName,userName,rightId,groupId);
        return "ok";
    }

    public Map<User, List<Right>> getUserList() {
        return userAdminstrationMemory.getUserList();
    }
}

