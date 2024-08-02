package com.itheima.stock.controller;

import com.itheima.stock.pojo.entity.SysUser;
import com.itheima.stock.service.UserService;
import com.itheima.stock.vo.LoginReqVo;
import com.itheima.stock.vo.LoginRespVo;
import com.itheima.stock.vo.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/user/{userName}")
    public SysUser getUserByUserName(@PathVariable("userName") String userName) {
        return userService.findByUsername(userName);
    }

    @PostMapping("/login")
    public R<LoginRespVo> login(@RequestBody LoginReqVo vo) {
        return userService.login(vo);
    }

    @GetMapping("/captcha")
    public R<Map> getCaptcha() {
        return userService.getCaptchaCode();
    }
}
