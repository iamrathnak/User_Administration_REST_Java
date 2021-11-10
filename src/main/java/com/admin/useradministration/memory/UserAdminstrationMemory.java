package com.admin.useradministration.memory;


import com.admin.useradministration.model.*;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
public class UserAdminstrationMemory {

    private static  Map<User,List<Right>> userTable = new ConcurrentHashMap<>();// userlist with rights
    private  static Map<String,List<Group>> userGroupTable = new ConcurrentHashMap<>();// user with groups
    private  static Map<Group,List<Right>> groupRightTable = new ConcurrentHashMap<>();// group right list

    private  static Map<String,String> loginCredentials = new HashMap<>();
    private static  Map<String,LocalDateTime> session = new ConcurrentHashMap<>();//manager user session

    private  static List<Group> groupData ;//pre-defined groups
    private  static List<Right> rightsData;//pre-defined rights




    UserAdminstrationMemory(){
        User user = new User();
        user.setFirstname("admin");
        user.setUserid(101);
        user.setLastname("admin");
user.setIsFamilyMember("admin");
user.setPassword("admin");
user.setUsername("admin");
        this.rightsData = UserAdminstrationMetaData.getRightData();
        userTable.put(user, rightsData);

        this.groupData = UserAdminstrationMetaData.getGroupData();
        for(Group gr:groupData){
            groupRightTable.put(gr,UserAdminstrationMetaData.getRightData());
        }

    }

    /**
     * Adding new user into the DB
     * @param loggedInUserName
     * @param user
     */
   public void addUser(String loggedInUserName,User user){
       boolean  checkSession=  checkUserSession(loggedInUserName);
if(checkSession) {
    this.rightsData = UserAdminstrationMetaData.getRightData();
    userTable.put(user, rightsData);
}
   }


    /**
     * Assign particular Group to the user
     * @param loggedInUserName
     * @param userName
     * @param group
     */

    public void assignUserToGroup(String loggedInUserName,String userName, Group group) {
        boolean checkSession = checkUserSession(loggedInUserName);
        if (checkSession) {

            if (userGroupTable.containsKey(userName)) {
                List<Group> groupList = userGroupTable.get(userName);
                if (!groupList.contains(group)) {
                    groupList.add(group);
                    userGroupTable.put(userName, groupList);

                }
            } else {
                List<Group> userIdList = new CopyOnWriteArrayList<>();
                userIdList.add(group);
                userGroupTable.put(userName, userIdList);
            }
        }else{
            System.out.println("Invalid Admin User");
        }
    }

    /**
     * Authenticate the administartor user and store daat into sessions
     *
     * @param userAuthentication
     * @return
     */
    public boolean userAuthentication(UserAuthentication userAuthentication){
        for(Map.Entry key:userTable.entrySet()){
            User user= (User) key.getKey();
            if(user.getUsername().equalsIgnoreCase(userAuthentication.getUsername())&&user.getPassword().equalsIgnoreCase(userAuthentication.getPassword())){
                session.put(user.getUsername(),LocalDateTime.now());
                return  true;
            }
        }
        return  false;
    }

    /**
     *
     * validating delete rights for the LoggedIN User to  delete other user with user id
     * @param loggedInUserName
     * @param userName
     */
    public boolean deleteUser(String loggedInUserName,String userName) {

      boolean  checkSession=  checkUserSession(loggedInUserName);
      if(checkSession && validateUserRights(loggedInUserName,"DeleteUser")){
          for(Map.Entry key:userTable.entrySet()){
              User user= (User) key.getKey();
              if(user.getUsername().equalsIgnoreCase(userName)){
                  userTable.remove(user);
                  return true;
              }
          }
      }else{

          System.out.println("Invalid Admin User");
          return false;
      }

        return false;
    }

    /**
     * Delete  particular  group for the user using groupID
     * @param loggedInUserName
     * @param userName
     * @param groupId
     */
    public void deleteUserGroup(String loggedInUserName, String userName, int groupId) {
        boolean  checkSession=  checkUserSession(loggedInUserName);
        if(checkSession){
        
        
            for(Map.Entry<String,List<Group>> key:userGroupTable.entrySet()) {
                if (key.getKey().equals(userName)) {
                    List<Group> groupList = key.getValue();
                    for (Group gr : groupList) {
                        if (gr.getGroupid() == groupId) {
                            groupList.remove(gr);

                        }
                    }

                    userGroupTable.put(userName, groupList);

                }
            }}else{

        }

    }

    /**
     * Delete  particular  right for the user using rightId
     * @param loggedInUserName
     * @param userName
     * @param rightId
     */
    public void deleteUserRight(String loggedInUserName, String userName, int rightId) {
        boolean  checkSession=  checkUserSession(loggedInUserName);
        if(checkSession && validateUserRights(loggedInUserName,"DeleteRight")){
            for(Map.Entry<User,List<Right>> key:userTable.entrySet()){
                if(key.getKey().getUsername().equals(userName)){
                    List<Right> rightList =  key.getValue();
                    for(Right right:rightList){
                        if(right.getRightid()==rightId){
                            rightList.remove(right);
                            userTable.put(key.getKey(),rightList);

                        }
                    }


                }
            }
        }else{
            System.out.println("Invalid Admin User");

        }

    }
    /**
     * Delete user  group right
     * @param loggedInUserName
     * @param rightId
     * @param groupId
     */
    //Need to check the logic with client
    public void deleteUserGroupRight(String loggedInUserName,  int rightId, int groupId) {
        boolean  checkSession=  checkUserSession(loggedInUserName);
        if(checkSession ){
            for(Map.Entry<Group,List<Right>> key:groupRightTable.entrySet()){
                if(key.getKey().getGroupid()==(groupId)){
                    List<Right> rightList = key.getValue();
                    for(Right gr:rightList){
                        if(gr.getRightid()==rightId){
                            rightList.remove(gr);
                        }
                    }
                    groupRightTable.put(key.getKey(),rightList);

                }
            }
        }else{
            System.out.println("Invalid Admin User");

        }

    }



    /**
     * util method to Validate user rights
     * @param userName
     * @param rights
     * @return
     */
    public boolean validateUserRights(String userName,String rights){
        for(Map.Entry key:userTable.entrySet()){
            User user= (User) key.getKey();
            if(user.getUsername().equalsIgnoreCase(userName)){
               List<Right> right=(List<Right>)key.getValue();
              for(Right rig:right){
               if(rig.getName().equalsIgnoreCase(rights)){
                   return true;
               }}

            }
        }
        return false;
    }

    /**
     * Util method to   check the loggedin user session
     * @param userName
     * @return
     */
    private boolean checkUserSession(String userName){
        LocalDateTime dateTime = session.get(userName);

        LocalDateTime interValTime = dateTime.plus(Duration.of(30, ChronoUnit.MINUTES));
        if(interValTime.equals(LocalDateTime.now())){
            return  false;
        }
        return  true;
    }

    public Map<User, List<Right>> getUserList() {
        return userTable;
    }

    public Map<String, List<Group>> getUserGroups(String userName) {
        return userGroupTable;
    }

    public Map<Group, List<Right>> getGroupRights(Integer groupId) {
        return groupRightTable;
    }

    public boolean userAuthentication(String userName,Right rights) {
        for(Map.Entry key:userTable.entrySet()){
            User user= (User) key.getKey();
            if(user.getUsername().equalsIgnoreCase(userName)){
                List<Right> right=(List<Right>)key.getValue();
                for(Right rig:right){
                    if(rig.getName().equalsIgnoreCase(rights.getName())){
                        return false;
                    }else{
                        right.add(rights);
                        break;
                    }
                }
                userTable.put(user,right);
            }
        }
   return  true;
    }

    public List getUserGroupRights(String userName) {

      Map<Group, List<Right>> groupRightTableMap=  groupRightTable;
        Map<User, List<Right>> userTableMap=        userTable;
       Map<String, List<Group>> userGroupTableMap= userGroupTable;
        List<Right> right = null;
        List<Group> groups = null;

        //Get User rights
        for(Map.Entry<User, List<Right>> key: userTable.entrySet()){
            User user= (User) key.getKey();
            if(user.getUsername().equalsIgnoreCase(userName)) {
                right = (List<Right>) key.getValue();
            }

        }
//Get User Group
        for(Map.Entry<String, List<Group>> key: userGroupTable.entrySet()){
            if(key.getKey().equalsIgnoreCase(userName)) {
                groups =  key.getValue();
            }

        }
        List ggRightList = new ArrayList();
     if(groups!=null){


        //Get group rights

        for(Group gg: groups) {
            List<Right> ggRight = groupRightTable.get(gg);
            Map map = new HashMap();
            map.put("GroupName", gg.getName());
            map.put("GroupId", gg.getGroupid());
            map.put("GroupRights", ggRight);
            ggRightList.add(map);

        }        }

            List finalList = new ArrayList();
            Map map = new HashMap();
            map.put("UserName",userName);
            map.put("userRight",right);
            map.put("UserGroupRight",ggRightList);
        finalList.add(map);


return  finalList;
    }
}
