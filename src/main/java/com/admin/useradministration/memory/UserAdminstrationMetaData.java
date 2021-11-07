package com.admin.useradministration.memory;


import com.admin.useradministration.model.*;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
public class UserAdminstrationMetaData {

    private  static List<Group> groupData = new CopyOnWriteArrayList<>();
    private  static List<Right> rightsData = new CopyOnWriteArrayList<>();


    /**
     * Pre-defined Rights
     * @return
     */
    public static List getRightData() {
        Right right1 = new Right();
        right1.setRightid(1);
        right1.setName("deleteUser");
        right1.setType(RightsType.deleteUser);
        Right right2 = new Right();
        right2.setRightid(2);
        right2.setName("addGroup");
        right2.setType(RightsType.addGroup);
        Right right3 = new Right();
        right3.setRightid(3);
        right3.setName("addUser");
        right3.setType(RightsType.addUser);
        Right right4 = new Right();
        right4.setRightid(4);
        right4.setName("defineRight");
        right4.setType(RightsType.defineRight);
        Right right5 = new Right();
        right5.setRightid(5);
        right5.setName("deleteRight");
        right5.setType(RightsType.deleteRight);
        Right right6 = new Right();
        right6.setRightid(6);
        right6.setName("deleteGroup");
        right6.setType(RightsType.deleteGroup);
        Right right7 = new Right();
        right7.setRightid(7);
        right7.setName("removerUserFromGroup");
        right7.setType(RightsType.removerUserFromGroup);


        rightsData.add(right1);
        rightsData.add(right2);
        rightsData.add(right3);
        rightsData.add(right4);
        rightsData.add(right5);
        rightsData.add(right6);
        rightsData.add(right7);


        return  rightsData;


    }

    /**
     * Pre-defined Groups
     * @return
     */
    public static  List getGroupData(){
            Group group1 = new Group();
            group1.setGroupid(1);
            group1.setName("HouseAdmins");
            group1.setType(GroupType.HouseAdmins);
    Group group2 = new Group();
    group2.setGroupid(2);
    group2.setName("FamilyMembers");
    group2.setType(GroupType.FamilyMembers);
    Group group3 = new Group();
    group3.setGroupid(3);
    group3.setName("Guests");
    group3.setType(GroupType.Guests);
    Group group4 = new Group();
    group4.setGroupid(4);
    group4.setName("SystemAdmins");
    group4.setType(GroupType.SystemAdmins);
    Group group5 = new Group();
    group5.setGroupid(5);
    group5.setName("Owners");
    group5.setType(GroupType.Owners);

            groupData.add(group1);
    groupData.add(group2);
    groupData.add(group3);
    groupData.add(group4);
    groupData.add(group5);

    return  groupData;
        }


    }



