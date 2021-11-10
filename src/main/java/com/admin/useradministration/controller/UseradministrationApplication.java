package com.admin.useradministration.controller;


import com.admin.useradministration.memory.UserAdminstrationMemory;
import com.admin.useradministration.model.Group;
import com.admin.useradministration.model.Right;
import com.admin.useradministration.model.User;
import com.admin.useradministration.model.UserAuthentication;
import com.admin.useradministration.service.UseradministrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;


@RestController
@RequestMapping("/user-admin")
class UseradministrationController{

@Autowired
private UseradministrationService useradministrationService;
    @Autowired
    private UserAdminstrationMemory memory;


    /**
     * Adding new User
     * @param user
     * @param headers
     * @return
     */
    @PostMapping("/user")
    public String addUser(@RequestBody User user,@RequestHeader HttpHeaders headers){
        System.out.println("UserName :"+user.getUsername());
        String loggedInUserName=headers.get("UserName").get(0);

        return ("ok".equalsIgnoreCase( useradministrationService.addUser(loggedInUserName,user)))?"successfully added "+user.getUsername():"Failed to add user "+user.getUsername();
    }


    /**
     * Assign User to the Group
     * @param group
     * @return
     */
    @PutMapping("/{userName}/user-group")
    public String addUserGroup(@PathVariable ("userName")String userName,@RequestBody  Group group,@RequestHeader HttpHeaders headers){
        System.out.println("GroupId :"+group.getGroupid());
        String loggedInUserName=headers.get("UserName").get(0);
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
        return ("ok".equalsIgnoreCase( useradministrationService.userAuthentication(userAuthentication)))?"successfully logged In "+userAuthentication.getUsername():"Incorrect Details ! Try again Later ";

    }

    /**
     * Adding rights to the user
     * @param userAuthentication
     * @return
     */
    @PostMapping("/user/{username}/right")
    public String addRightsForTheUser(@PathVariable ("username")String userName ,@RequestBody Right userAuthentication){
        return ( memory.userAuthentication(userName,userAuthentication))?"Rights added successfully for the user "+userName:"Rights Already Exist !!";

    }


    /**
     * Delete  any user by the admin
     * @param headers
     * @return
     */
     @DeleteMapping("/user/{userId}")
        public String deleteUser(@PathVariable ("userId")String userName,@RequestHeader HttpHeaders headers){
        System.out.println("userId :"+userName);
         String loggedInUserName=headers.get("UserName").get(0);
        return ( useradministrationService.deleteUser(loggedInUserName,userName))?"successfully deleted user "+userName:"Failed to delete user/User Id doesn't Exist "+userName;
    }

    /**
     * Delete  the assigned group for the user
     * @param userName
     * @param groupId
     * @param headers
     * @return
     */
    @DeleteMapping("/user/{userName}/group/{groupId}")
    public String deleteUserGroup(@PathVariable ("userName")String userName,@PathVariable("groupId") Integer groupId,@RequestHeader HttpHeaders headers){
        System.out.println("GroupId :"+groupId);
        String loggedInUserName=headers.get("UserName").get(0);
        return ("ok".equalsIgnoreCase( useradministrationService.deleteUserGroup(loggedInUserName,userName,groupId)))?"successfully deleted greoup "+groupId:"Failed to delete group "+groupId;
    }


    /**
     * Delete  particular  right for the user using rightId
     * @param userName
     * @param rightId
     */
    @DeleteMapping("/user/{userName}/right/{rightId}")
    public String deleteUserRight(@PathVariable ("userName")String userName,@PathVariable("rightId") Integer rightId,@RequestHeader HttpHeaders headers){
        System.out.println("rightId :"+rightId);
        String loggedInUserName=headers.get("UserName").get(0);
        return ("ok".equalsIgnoreCase( useradministrationService.deleteUserRight(loggedInUserName,userName,rightId)))?"successfully deleted right "+rightId:"Failed to delete right "+rightId;
    }

    /**
     * Delete  particular  right for the user group
     * @param rightId
     */

    @DeleteMapping("/group/{groupId}/right/{rightId}")
    public String deleteUserGroupRight(
                                       @PathVariable("rightId") Integer rightId,
                                       @PathVariable("groupId") Integer groupId,
                                       @RequestHeader HttpHeaders headers){
        System.out.println("rightId :"+rightId);
        String loggedInUserName=headers.get("UserName").get(0);
        return ("ok".equalsIgnoreCase( useradministrationService.deleteUserGroupRight(loggedInUserName,rightId,groupId)))?"successfully deleted right "+rightId:"Failed to delete right "+rightId;
    }

    /**
     * Resource  to view the user details
     * @param headers
     * @return
     */
    @GetMapping("/user")
    public List getUser(@RequestHeader HttpHeaders headers){
        String loggedInUserName=headers.get("UserName").get(0);
        Map<User,List<Right>> data =useradministrationService.getUserList();
List list = new ArrayList();
        for(Map.Entry<User,List<Right>> key: data.entrySet()){
            Map map = new HashMap();
            map.put("username",key.getKey().getUsername());
            map.put("firstname",key.getKey().getFirstname());
            map.put("data", key.getValue());
            list.add(map);
        }
        return  list;
    }
    /**
     * Resource  to view the user details
     * @param headers
     * @return
     */
    @GetMapping("/user/{userName}")
    public List getUser(@PathVariable("userName") String userName,@RequestHeader HttpHeaders headers){
        String loggedInUserName=headers.get("UserName").get(0);
        Map<String, List<Group>> data =memory.getUserGroups(userName);
        List list = new ArrayList();
        for(Map.Entry<String, List<Group>> key: data.entrySet()){
            Map map = new HashMap();
            map.put("username",key.getKey());
            map.put("groupList", key.getValue());
            list.add(map);
        }
        return  list;
    }

    @GetMapping("/group/{groupId}")
    public List getGroupRights(@PathVariable("groupId") Integer groupId,@RequestHeader HttpHeaders headers){
        String loggedInUserName=headers.get("UserName").get(0);
        Map<Group, List<Right>> data =memory.getGroupRights(groupId);
        List list = new ArrayList();
        for(Map.Entry<Group, List<Right>> key: data.entrySet()){
            Map map = new HashMap();
            map.put("GroupName",key.getKey().getName());
            map.put("GroupId",key.getKey().getGroupid());
            map.put("groupList", key.getValue());
            list.add(map);
        }
        return  list;
    }


    @GetMapping("/user/{userName}/group-rights")
    public List getUserGroupRights(@PathVariable("userName") String userName,@RequestHeader HttpHeaders headers){
        String loggedInUserName=headers.get("UserName").get(0);
        return memory.getUserGroupRights(userName);


    }


}
