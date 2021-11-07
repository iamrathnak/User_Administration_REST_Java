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

//done
    @RequestMapping("/add-user")
    public String addUser(@RequestBody User user){
        System.out.println("UserName :"+user.getUsername());
        return ("ok".equalsIgnoreCase( useradministrationService.addUser(user)))?"successfully added "+user.getUsername():"Failed to add user "+user.getUsername();
    }
//H-B
    @RequestMapping("/add-user-group")
    public String addUserGroup(@RequestBody Group group){
        System.out.println("GroupName :"+group.getName());
        useradministrationService.addUserToGroup(group,"null");
        return "user  added to the group "+group.getName()+" successfully";
    }
//DOne
    @RequestMapping("/authentication")
    public String userAuthentication(@RequestBody UserAuthentication userAuthentication){
        System.out.println("Logged In UserName :"+userAuthentication.getUsername());
        return ("ok".equalsIgnoreCase( useradministrationService.userAuthentication(userAuthentication)))?"successfully logged In "+userAuthentication.getUsername():"Failed to log In "+userAuthentication.getUsername();

    }


    //done
    @RequestMapping("/delete-user/{userId}")
    public String deleteUser(@PathVariable int userId){
        System.out.println("UserId :"+userId);
        return ("ok".equalsIgnoreCase( useradministrationService.deleteUser(userId)))?"successfully deleted user "+userId:"Failed to delete user "+userId;
    }

    //done
  /*  @RequestMapping("/delete-group/{groupId}")
    public String deleteGroup(@PathVariable int groupId){
        System.out.println("GroupId :"+groupId);
        return ("ok".equalsIgnoreCase( useradministrationService.deleteGroup(groupId)))?"successfully deleted greoup "+groupId:"Failed to delete group "+groupId;
    }
    */


     @DeleteMapping("/{userId}/delete-user-right/{groupId}")
        public String deleteUserRight(@PathVariable ("userId")Integer userId,@PathVariable("groupId") Integer groupId,@RequestHeader HttpHeaders headers){
        System.out.println("GroupId :"+groupId);
        String loginUserName=headers.get("UserName").toString();
        return ("ok".equalsIgnoreCase( useradministrationService.deleteUserRight(loginUserName,userId,groupId)))?"successfully deleted greoup "+groupId:"Failed to delete group "+groupId;
    }




}
