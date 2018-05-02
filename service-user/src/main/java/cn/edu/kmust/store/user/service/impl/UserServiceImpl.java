package cn.edu.kmust.store.user.service.impl;


import cn.edu.kmust.store.user.entity.User;
import cn.edu.kmust.store.user.param.UserDto;
import cn.edu.kmust.store.user.repository.UserRepository;
import cn.edu.kmust.store.user.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Resource
    private UserRepository userRepository;


    @Override
    public boolean selectUserByName(String name) {

        User findOne = userRepository.findUserByName(name);

        if (findOne == null) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public User selectUserByNameAndPassword(String name, String password) {
        User findOne = userRepository.findUserByNameAndPassword(name, password);
        return findOne;
    }

    @Override
    public boolean saveUser(User user) {

        User saveUser = userRepository.save(user);

        if (saveUser == null) {
            return false;
        }

        return true;

    }

    @Override
    public UserDto getUserByUserId(Integer userId) {

        User user = userRepository.findOne(userId);

        UserDto userDto = null;

        if (user != null) {
            userDto = new UserDto();

            BeanUtils.copyProperties(user, userDto);
        }

        return userDto;
    }
}
