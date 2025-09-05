package com.keshe.controller;

import ch.qos.logback.core.util.StringUtil;
import com.keshe.pojo.Result;
import com.keshe.pojo.User;
import com.keshe.server.UserService;
import com.keshe.utils.JwtUtil;
import com.keshe.utils.ThreadLocalUtil;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.URL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/users")
@Validated
@Slf4j
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public Result register(@RequestParam("username") String username,@RequestParam("password") String password) {
        //查询是否存在用户
        User user = userService.findByUsername(username);
        if(user == null){
            userService.register(username, password);
            return Result.success();
        }else{
            return Result.error("用户已存在");
        }
    }
    @PostMapping("/login")
    public Result login(@RequestParam("username") String username, @RequestParam("password") String password) {
        User user = userService.findByUsername(username);
        if(user == null){
            return Result.error("用户不存在");
        }
        if(user.getPassword().equals(DigestUtils.md5DigestAsHex(password.getBytes()))){
            Map<String, Object> claims=new HashMap<>();
            claims.put("id",user.getUserId());
            claims.put("username",user.getUsername());
            String token= JwtUtil.genToken(claims);

            return Result.success(token);
        }
        return Result.error("密码错误");
    }
     @GetMapping("/userInfo")
    public Result<User> userInfo(@RequestHeader(name="Authorization") String token){
        Map<String, Object> claims = JwtUtil.parseToken(token);
        String username =(String) claims.get("username");
        User user = userService.findByUsername(username);
        return Result.success(user);
    }
    @PutMapping("/update")
    public Result update(@RequestBody User user){

        userService.update(user);
        return Result.success();
    }
     @PatchMapping("/updatePwd")
    public Result updatePwd(@RequestBody Map<String, String> params, @RequestHeader(name="Authorization") String token){
        String oldPwd = params.get("old_pwd");
        String newPwd = params.get("new_pwd");
        String rePwd = params.get("re_pwd");

        if(!StringUtils.hasLength(oldPwd)||!StringUtils.hasLength(newPwd)||!StringUtils.hasLength(rePwd)){
            return Result.error("缺少参数");
        }
        
        // 手动解析token获取用户信息
        Map<String, Object> claims;
        try {
            claims = JwtUtil.parseToken(token);
        } catch (Exception e) {
            return Result.error("用户未登录或登录已过期");
        }
        
        String username =(String) claims.get("username");
        Integer userId = (Integer) claims.get("id");
        User user = userService.findByUsername(username);
        if(!user.getPassword().equals(DigestUtils.md5DigestAsHex(oldPwd.getBytes()))){
            return Result.error("旧密码错误");
        }
        if(!newPwd.equals(rePwd)){
            return Result.error("新密码不一致");
        }

        userService.updatePwd(newPwd, userId);
        return Result.success();
    }
}
