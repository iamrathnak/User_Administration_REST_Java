package com.admin.useradministration.service;


import com.admin.useradministration.memory.UserAdminstrationMemory;
import com.admin.useradministration.model.Group;
import com.admin.useradministration.model.User;
import com.admin.useradministration.model.UserAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Service
public class UseradministrationService {

@Autowired
private UserAdminstrationMemory userAdminstrationMemory;

    public String addUser( User user){
        userAdminstrationMemory.storeUser(user);
        return "ok";
    }

    public String addUserToGroup( Group userGroup, String userId){
        userAdminstrationMemory.assignUserToGroup(userGroup,userId);
        return "ok";
    }


    public String userAuthentication(UserAuthentication userAuthentication) {
        return userAdminstrationMemory.userAuthentication(userAuthentication)?"ok":"error";
    }

    public String deleteUser(int userId) {
        userAdminstrationMemory.deleteUser(userId);
        return "ok";
    }
    public String deleteGroup(int groupId) {
        userAdminstrationMemory.deleteGroup(groupId);
        return "ok";
    }
    public String deleteUserRight(String userName,int userId,int groupId) {
        userAdminstrationMemory.deleteUserRight(userName,userId,groupId);
        return "ok";
    }

}

