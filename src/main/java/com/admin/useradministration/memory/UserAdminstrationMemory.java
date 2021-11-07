package com.admin.useradministration.memory;


import com.admin.useradministration.model.Group;
import com.admin.useradministration.model.Right;
import com.admin.useradministration.model.User;
import com.admin.useradministration.model.UserAuthentication;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
public class UserAdminstrationMemory {

    private static  Map<User,Right> userTable = new ConcurrentHashMap<>();
    private  static Map<Group,List<String>> userGroupTable = new ConcurrentHashMap<>();
    private  static Map<String,String> loginCredentials = new HashMap<>();
    private static  Map<String,LocalDateTime> session = new ConcurrentHashMap<>();


    private Right right;
    UserAdminstrationMemory(){
        this. right = new Right();
       this. right.setName("default");
      this.  right.setRightid(1);
    }
    //DU - Delete User
    //DUG - Delete User  Group
    //DUGR - Delete  User  Grou[ Right

   public void storeUser(User user){
       userTable.put(user,this.right);

   }

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

    private boolean checkUserSession(String userName){
        LocalDateTime dateTime = session.get(userName);

        LocalDateTime currenDateTime = dateTime.plus(Duration.of(30, ChronoUnit.MINUTES));
        if(currenDateTime.equals(dateTime)){
            return  true;
        }
       return  false;
    }

    public void assignUserToGroup(Group userGroup,String userId){

       if(userGroupTable.containsKey(userGroup)){
           List<String> userIdList = userGroupTable.get(userGroup);
           if(!userIdList.contains(userId)){
               userIdList.add(userId);
               userGroupTable.put(userGroup,userIdList);

           }
       }else{
           List<String> userIdList = new ArrayList<>();
           userIdList.add(userId);
           userGroupTable.put(userGroup,userIdList);
       }
    }


    public void deleteUser(int userId) {
        for(Map.Entry key:userGroupTable.entrySet()){
            User user= (User) key.getKey();
                if(user.getUserid()==(userId)){
                    userTable.remove(user);
                }
            }
    }

    public void deleteGroup(int userId,int groupId) {
        checkUserSession(userId);
        for(Map.Entry key:userGroupTable.entrySet()){
            Group group= (Group) key.getKey();
            if(group.getGroupid()==groupId){
                userGroupTable.remove(group);
            }
        }
    }

    public void deleteUserRight(String userName,int userId, int groupId) {

      boolean seession=  checkUserSession(userName);

    }
    public void  getUserRights(String userName){
        for(Map.Entry key:userTable.entrySet()){
            User user= (User) key.getKey();
            if(user.getUsername().equalsIgnoreCase(userName)){
               Right right=(Right)user.getValues();

            }
        }
    }

}
