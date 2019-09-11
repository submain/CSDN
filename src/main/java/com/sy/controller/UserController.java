package com.sy.controller;

import com.sy.model.User;
import com.sy.model.resp.BaseResp;
import com.sy.service.UserServic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
public class UserController {
    @Autowired
    UserServic servic;
    BaseResp baseResp = new BaseResp();

    //登录接口
    @RequestMapping(value = "loginVerification", method = RequestMethod.POST)
    public BaseResp loginVerification(String username, String userpassword, HttpServletRequest request) {
        try {
            baseResp = servic.loginVerification(username, userpassword);
            if (baseResp.getSuccess() == 1) {
                request.getSession().setAttribute("user", baseResp.getData());
            }
            return baseResp;
        } catch (Exception e) {
            e.printStackTrace();
            baseResp.setSuccess(0);
            return baseResp;
        }
    }

    ;

    //注册接口
    @RequestMapping(value = "registerServlet", method = RequestMethod.POST)
    public BaseResp registerServlet(String username, String userpassword, HttpServletRequest request) {
        try {
            baseResp = servic.addUser(username, userpassword);
            System.out.println(request.getSession().getAttribute("user"));
            return baseResp;
        } catch (Exception e) {
            e.printStackTrace();
            baseResp.setSuccess(0);
            return baseResp;
        }
    }

    //判断是否登入的接口
    @RequestMapping(value = "isEnter")
    public BaseResp isEnter(HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            baseResp.setSuccess(0);
            baseResp.setErrorMsg("未登入");
            return baseResp;
        } else {
            baseResp.setSuccess(1);
            baseResp.setErrorMsg("已登入");
            try {
                Integer userId = user.getUserId();
                baseResp.setData(servic.findUserByUserId(userId).getData());
            } catch (Exception e) {
                e.printStackTrace();
                baseResp.setData(user);
            }
            return baseResp;
        }
    }

    //注销登入
    @RequestMapping(value = "logout")
    public BaseResp logout(HttpServletRequest request) {
        request.getSession().invalidate();
        baseResp.setSuccess(1);
        baseResp.setErrorMsg("注销成功");
        return baseResp;
    }

    //修改用户头像
    @RequestMapping(value = "modifyHeadImg", method = RequestMethod.POST)
    public BaseResp modifyHeadImg(Integer userId, String headImg) {
        try {
            baseResp = servic.modifyHeadImgByUserid(userId, headImg);
        } catch (Exception e) {
            e.printStackTrace();
            baseResp.setSuccess(0);
            baseResp.setErrorMsg("服务器异常");
        }
        return baseResp;
    }

    //修改用户信息
    @RequestMapping(value = "modifyUserInfor", method = RequestMethod.POST)
    public BaseResp modifyUserInfor(User user) {
        try {
            baseResp = servic.modifyUserInfor(user);
        } catch (Exception e) {
            e.printStackTrace();
            baseResp.setSuccess(0);
            baseResp.setErrorMsg("服务器异常");
        }
        return baseResp;
    }

    //个人资料渲染接口
    @RequestMapping(value = "personalData", method = RequestMethod.GET)
    public BaseResp personalData(Integer userId) {
        try {
            baseResp = servic.findUserInforIncludeMsg(userId);
        } catch (Exception e) {
            e.printStackTrace();
            baseResp.setSuccess(0);
            baseResp.setErrorMsg("服务器异常");
        }
        return baseResp;
    }
    //根据userId查询粉丝
    @RequestMapping(value = "myFans")
    public BaseResp findAllFansByUserid(Integer userId) {
        try {
            baseResp = servic.findAllFansByUserid(userId);
        } catch (Exception e) {
            e.printStackTrace();
            baseResp.setSuccess(0);
            baseResp.setErrorMsg("服务器异常");
        }
        return baseResp;
    }
    //根据userId查询关注的人
    @RequestMapping(value = "myInterest")
    public BaseResp findAllreFansByUserId(Integer userId) {
        try {
            baseResp = servic.findAllreFansByUserId(userId);
        } catch (Exception e) {
            e.printStackTrace();
            baseResp.setSuccess(0);
            baseResp.setErrorMsg("服务器异常");
        }
        return baseResp;
    }
    //个人主页渲染数据
    @RequestMapping(value = "perInfordata")
    public BaseResp perInfordata(Integer viewUserId,Integer userId) {
        try {
            baseResp = servic.perInfordata(viewUserId,userId);
        } catch (Exception e) {
            e.printStackTrace();
            baseResp.setSuccess(0);
            baseResp.setErrorMsg("服务器异常");
        }
        return baseResp;
    }
    //个人主页细节数据
    @RequestMapping(value = "perInforDetailData")
    public BaseResp perInforDetailData(Integer userId, String type) {
        try {
            baseResp = servic.perInforDetailData(userId,type);
        } catch (Exception e) {
            e.printStackTrace();
            baseResp.setSuccess(0);
            baseResp.setErrorMsg("服务器异常");
        }
        return baseResp;
    }

    //修改账户
    @RequestMapping(value = "midifyUser",method = RequestMethod.POST)
    public BaseResp midifyUser(Integer userId,String userpassword, String username) {
        try {
            baseResp = servic.midifyUserByUserId(userId,userpassword,username);
        } catch (Exception e) {
            e.printStackTrace();
            baseResp.setSuccess(0);
            baseResp.setErrorMsg("服务器异常");
        }
        return baseResp;
    }





}
