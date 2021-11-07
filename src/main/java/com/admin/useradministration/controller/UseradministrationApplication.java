package com.admin.useradministration.controller;


import com.admin.useradministration.model.Group;
import com.admin.useradministration.model.User;
import com.admin.useradministration.model.UserAuthentication;
import com.admin.useradministration.service.UseradministrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/user-admin")
class UseradministrationController{

@Autowired
private UseradministrationService useradministrationService;

    /**
     * Adding new User
     * @param user
     * @param headers
     * @return
     */
    @PostMapping("/add-user")
    public String addUser(@RequestBody User user,@RequestHeader HttpHeaders headers){
        System.out.println("UserName :"+user.getUsername());
        String loggedInUserName=headers.get("UserName").toString();

        return ("ok".equalsIgnoreCase( useradministrationService.addUser(loggedInUserName,user)))?"successfully added "+user.getUsername():"Failed to add user "+user.getUsername();
    }

    /**
     * Assign User to the Group
     * @param group
     * @return
     */
    @PutMapping("/{userName}/add-user-group/{groupId}")
    public String deleteUserGroup(@PathVariable ("userName")String userName,@RequestBody  Group group,@RequestHeader HttpHeaders headers){
        System.out.println("GroupId :"+group.getGroupid());
        String loggedInUserName=headers.get("UserName").toString();
        return ("ok".equalsIgnoreCase( useradministrationService.addUserGroup(loggedInUserName,userName,group)))?"successfully assignd greoup "+group.getGroupid():"Failed to assign group "+group.getGroupid();
    }

    /**
     * Authentication service to  loggin admin user
     * @param userAuthentication
     * @return
     */
    @PostMapping("/authentication")
    public String userAuthentication(@RequestBody UserAuthentication userAuthentication){
        System.out.println("Logged In UserName :"+userAuthentication.getUsername());
        return ("ok".equalsIgnoreCase( useradministrationService.userAuthentication(userAuthentication)))?"successfully logged In "+userAuthentication.getUsername():"Failed to log In "+userAuthentication.getUsername();

    }


    /**
     * Delete  any user by the user who has 'Delete User' rights
     * @param userName
     * @param groupId
     * @param headers
     * @return
     */
     @DeleteMapping("/{userId}/delete-user")
        public String deleteUser(@PathVariable ("userId")String userName,@PathVariable("groupId") Integer groupId,@RequestHeader HttpHeaders headers){
        System.out.println("GroupId :"+groupId);
        String loggedInUserName=headers.get("UserName").toString();
        return ("ok".equalsIgnoreCase( useradministrationService.deleteUser(loggedInUserName,userName)))?"successfully deleted greoup "+groupId:"Failed to delete group "+groupId;
    }

    /**
     * Delete  any user assigned group who has ' Delete Group' rights
     * @param userName
     * @param groupId
     * @param headers
     * @return
     */
    @DeleteMapping("/{userName}/delete-user-group/{groupId}")
    public String deleteUserGroup(@PathVariable ("userName")String userName,@PathVariable("groupId") Integer groupId,@RequestHeader HttpHeaders headers){
        System.out.println("GroupId :"+groupId);
        String loggedInUserName=headers.get("UserName").toString();
        return ("ok".equalsIgnoreCase( useradministrationService.deleteUserGroup(loggedInUserName,userName,groupId)))?"successfully deleted greoup "+groupId:"Failed to delete group "+groupId;
    }


    /**
     * Delete  particular  right for the user using rightId
     * @param userName
     * @param rightId
     */
    @DeleteMapping("/{userName}/delete-user-group/{rightId}")
    public String deleteUserRight(@PathVariable ("userName")String userName,@PathVariable("rightId") Integer rightId,@RequestHeader HttpHeaders headers){
        System.out.println("rightId :"+rightId);
        String loggedInUserName=headers.get("UserName").toString();
        return ("ok".equalsIgnoreCase( useradministrationService.deleteUserRight(loggedInUserName,userName,rightId)))?"successfully deleted right "+rightId:"Failed to delete right "+rightId;
    }



}
