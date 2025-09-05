package com.keshe.server.Impl;

import com.keshe.mapper.UserMapper;
import com.keshe.pojo.User;
import com.keshe.server.UserService;
import com.keshe.utils.ThreadLocalUtil;
import org.apache.ibatis.annotations.Insert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    /**
     * 根据用户名查询用户
     * @param username
     * @return
     */
    public User findByUsername(String username) {
       User u=userMapper.findByUsername(username);
       return u;
    }

    /**
     * 注册用户
     * @param username
     * @param password
     */
    public void register(String username, String password) {
            password= DigestUtils.md5DigestAsHex(password.getBytes());

            userMapper.register(username,password);
    }

    /**
     * 更新用户信息
     * @param user
     */
    public void update(User user) {
        user.setUpdatedAt(LocalDateTime.now());
        System.out.println(user);
        userMapper.update(user);
    }

    /**
     * 更新用户密码
     * @param newPwd
     * @param userId
     */
    public void updatePwd(String newPwd, Integer userId) {
        userMapper.updatePwd(DigestUtils.md5DigestAsHex(newPwd.getBytes()), userId);
    }


}
