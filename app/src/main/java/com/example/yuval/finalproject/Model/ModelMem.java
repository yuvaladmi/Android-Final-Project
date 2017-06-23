package com.example.yuval.finalproject.Model;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Yuval on 14/06/2017.
 */

class ModelMem {
    private List<BusinessUser> data = new LinkedList<BusinessUser>();

    public ModelMem() {
        for(int i=0; i<15; i++){
            BusinessUser user = new BusinessUser();
            user.setUserId(""+i*17);
            user.setfirstName("kuku" + i);
            user.setAddress("1111");
            user.setEmail("kuku"+i+"@gmail.com");
            user.setImages("");
            user.setTreatments("1");

            data.add(user);
        }
    }

    public boolean addNewBusinessUser(BusinessUser newUser){
        if(getOneUser(newUser.getUserId()) == null) {
            data.add(newUser);
            return true;
        }
        else return false;
    }

    public BusinessUser getOneUser(String uid) {
        for(BusinessUser oneUser : data){
            if(oneUser.getUserId().equals(uid)) return oneUser;
        }
        return null;
    }

    public List<BusinessUser> getAllUsers(){return data;}



    public void deleteStudent(BusinessUser user) {
        data.remove(user);
    }
}
