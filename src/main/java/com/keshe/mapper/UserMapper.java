package com.keshe.mapper;

import com.keshe.pojo.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface UserMapper {

    @Select("select * from user where username = #{username}")
    public User findByUsername(String username);
    /**
     * 注册
     * @param username
     * @param password
     */
    @Insert("insert into user(username,password,created_at,updated_at) values(#{username},#{password},now(),now())")
    void register(@Param("username") String username, @Param("password") String password);
    /**
     * 更新
     * @param user
     */
    @Update("update user set email=#{email},phone=#{phone} ,updated_at = #{updatedAt} where user_id = #{userId}")
    void update(User user);

    /**
     * 修改密码
     * @param
     * @param
     */
     @Update("update user set password= #{newPwd}, updated_at = now() where user_id= #{id}")
    void updatePwd(@Param("newPwd") String newPwd, @Param("id") Integer id);


}