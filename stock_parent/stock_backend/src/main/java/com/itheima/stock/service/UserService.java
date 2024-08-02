package com.itheima.stock.service;

import com.itheima.stock.pojo.entity.SysUser;
import com.itheima.stock.vo.LoginReqVo;
import com.itheima.stock.vo.LoginRespVo;
import com.itheima.stock.vo.R;

import java.util.Map;

public interface UserService {
    SysUser findByUsername(String userName);

    R<LoginRespVo> login(LoginReqVo vo);

    R<Map> getCaptchaCode();
}
