package cn.edu.kmust.store.user.service;


import cn.edu.kmust.store.user.entity.User;
import cn.edu.kmust.store.user.param.UserDto;

public interface UserService {


    boolean selectUserByName(String name);

    User selectUserByNameAndPassword(String name, String password);

    boolean saveUser(User user);

    UserDto getUserByUserId(Integer userId);

}
