package com.keshe.server;

import com.keshe.pojo.User;
import org.hibernate.validator.constraints.URL;

public interface UserService {

    /**
     * 根据用户名查询用户
     * @param username
     * @return
     */
    User findByUsername(String username);

    /**
     * 注册用户
     * @param username
     * @param password
     */
    void register(String username, String password);

    /**
     * 更新用户信息
     * @param user
     */
    void update(User user);

    /**
     * 修改密码
     * @param newPwd
     * @param userId
     */
    void updatePwd(String newPwd, Integer userId);
}