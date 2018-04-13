package cn.edu.kmust.store.user.user.service;


import cn.edu.kmust.store.user.user.entity.User;

public interface UserService {


    boolean selectUserByName(String name);

    User selectUserByNameAndPassword(String name, String password);

    boolean saveUser(User user);

}
