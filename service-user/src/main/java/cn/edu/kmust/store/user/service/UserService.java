package cn.edu.kmust.store.user.service;


import cn.edu.kmust.store.user.entity.User;

public interface UserService {


    boolean selectUserByName(String name);

    User selectUserByNameAndPassword(String name, String password);

    boolean saveUser(User user);

}
