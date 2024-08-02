package com.itheima.stock.service.impl;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import com.itheima.stock.constant.StockConstant;
import com.itheima.stock.pojo.entity.SysUser;
import com.itheima.stock.mapper.SysUserMapper;
import com.itheima.stock.service.UserService;
import com.itheima.stock.utils.IdWorker;
import com.itheima.stock.vo.LoginReqVo;
import com.itheima.stock.vo.LoginRespVo;
import com.itheima.stock.vo.R;
import com.itheima.stock.vo.ResponseCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private IdWorker idWorker;

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public SysUser findByUsername(String userName) {
        return sysUserMapper.findUserInfoByUsername(userName);
    }

    @Override
    public R<LoginRespVo> login(LoginReqVo vo) {
//        1、判断参数是否合法
        if (vo == null || StringUtils.isBlank(vo.getUsername()) || StringUtils.isBlank(vo.getPassword())) {
            return R.error(ResponseCode.DATA_ERROR);
        }
        if (StringUtils.isBlank(vo.getCode())||StringUtils.isBlank(vo.getSessionId())) {
            return R.error(ResponseCode.CHECK_CODE_ERROR);
        }
        String redisCode = (String) redisTemplate.opsForValue().get(StockConstant.CHECK_PREFIX+vo.getSessionId());
        if(StringUtils.isBlank(redisCode)){
            return R.error(ResponseCode.CHECK_CODE_TIMEOUT);
        }
        if (!redisCode.equalsIgnoreCase(vo.getCode())) {
            return R.error(ResponseCode.CHECK_CODE_ERROR);
        }
//        2、查询数据库
        SysUser sysUser = sysUserMapper.findUserInfoByUsername(vo.getUsername());
        if (sysUser == null) {
            return R.error(ResponseCode.ACCOUNT_NOT_EXISTS);
        }
//        3、密码匹配
        if (!passwordEncoder.matches(vo.getPassword(), sysUser.getPassword())) {
            return R.error(ResponseCode.USERNAME_OR_PASSWORD_ERROR);
        }

        LoginRespVo loginRespVo = new LoginRespVo();
        BeanUtils.copyProperties(sysUser, loginRespVo);
        return R.ok(loginRespVo);
    }

    @Override
    public R<Map> getCaptchaCode() {
        //参数分别是宽、高、验证码长度、干扰线数量
        LineCaptcha captcha = CaptchaUtil.createLineCaptcha(250, 40, 4, 5);
        captcha.setFont(new Font("宋体", Font.BOLD, 30));
        captcha.setBackground(Color.LIGHT_GRAY);
        String checkCode = captcha.getCode();
        String imageData = captcha.getImageBase64();
        String sessionId = String.valueOf(idWorker.nextId());
        //将验证码存入redis
        redisTemplate.opsForValue().set(StockConstant.CHECK_PREFIX+sessionId, checkCode, 1, TimeUnit.MINUTES);
        Map<String,String> data=new HashMap<>();
        data.put("imageData",imageData);
        data.put("sessionId",sessionId);
        return R.ok(data);
    }
}
