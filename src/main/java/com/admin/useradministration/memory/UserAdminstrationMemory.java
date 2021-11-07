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
    private  static Map<String,String> loginCredentials = new HashMap<>();
    private static  Map<String,LocalDateTime> session = new ConcurrentHashMap<>();//manager user session

    private  static List<Group> groupData = UserAdminstrationMetaData.getGroupData();//pre-defined groups
    private  static List<Right> rightsData = UserAdminstrationMetaData.getRightData();//pre-defined rights




    UserAdminstrationMemory(){


    }

    /**
     * Adding new user into the DB
     * @param loggedInUserName
     * @param user
     */
   public void addUser(String loggedInUserName,User user){
       boolean  checkSession=  checkUserSession(loggedInUserName);
if(checkSession) {

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
                List<Group> userIdList = new ArrayList<>();
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
            if(userAuthentication.getUsername().equalsIgnoreCase(userAuthentication.getUsername())){
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
    public void deleteUser(String loggedInUserName,String userName) {

      boolean  checkSession=  checkUserSession(loggedInUserName);
      if(checkSession && validateUserRights(loggedInUserName,"DeleteUser")){
          for(Map.Entry key:userTable.entrySet()){
              User user= (User) key.getKey();
              if(user.getUsername().equalsIgnoreCase(userName)){
                  userTable.remove(user);
              }
          }
      }else{
          System.out.println("Invalid Admin User");

      }

    }

    /**
     * Delete  particular  group for the user using groupID
     * @param loggedInUserName
     * @param userName
     * @param groupId
     */
    public void deleteUserGroup(String loggedInUserName, String userName, int groupId) {
        boolean  checkSession=  checkUserSession(loggedInUserName);
        if(checkSession && validateUserRights(loggedInUserName,"DeleteGroup")){
            for(Map.Entry<String,List<Group>> key:userGroupTable.entrySet()){
                if(key.equals(userName)){
                   List<Group> groupList = key.getValue();
                    for(Group gr:groupList){
                        if(gr.getGroupid()==groupId){
                            groupList.remove(gr);
                        }
                    }

                    userGroupTable.put(userName,groupList);

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

        LocalDateTime currenDateTime = dateTime.plus(Duration.of(30, ChronoUnit.MINUTES));
        if(currenDateTime.equals(dateTime)){
            return  true;
        }
        return  false;
    }


}
