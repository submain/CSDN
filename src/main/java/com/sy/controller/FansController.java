package com.sy.controller;

import com.sy.model.User;
import com.sy.model.resp.BaseResp;
import com.sy.service.BlogService;
import com.sy.service.FansService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class FansController {
    @Autowired
    private FansService fansService;
//    @RequestMapping("/requestByKey")
//    public BaseResp queryByKey(){
//        BaseResp baseResp = null;
//        baseResp = blogService.queryBykEYResult("博士");
//        return baseResp;
//    }
    @RequestMapping(value = "/queryBlogByFansId",method = RequestMethod.GET)
    public BaseResp queryFansedBlogByFansId(Integer userId){
//        System.out.println(userId);
        BaseResp baseResp = null;
        baseResp = fansService.queryByFansIdResult(userId);
//        System.out.println(baseResp);
        return baseResp;
    }
    @RequestMapping(value = "/queryIsFocus",method = RequestMethod.GET)
    public BaseResp queryIsFocus(int bloguserid, HttpServletRequest request){
        BaseResp baseResp = new BaseResp();
        User user=(User) request.getSession().getAttribute("user");
        if (user!=null){
            if (bloguserid==user.getUserId()){
                baseResp.setSuccess(400);
                baseResp.setData("此文章的博主是自己");
            }else {
                baseResp = fansService.queryIsFocus(bloguserid,user.getUserId());
            }

        }else {
            baseResp.setSuccess(0);
            baseResp.setErrorMsg("没有登录");
        }

        return baseResp;
    }
    @RequestMapping("/addFocus")
    public BaseResp addFocus(int blogUserId,HttpServletRequest request){
        BaseResp baseResp = new BaseResp();
        User user=(User) request.getSession().getAttribute("user");
        if (user!=null){
            baseResp = fansService.addFocus(blogUserId,user.getUserId());
        }else {
            baseResp.setSuccess(0);
            baseResp.setErrorMsg("没有登录");
        }

        return baseResp;
    }
    @RequestMapping("/deleteFocus")
    public BaseResp deleteFocus(int blogUserId,HttpServletRequest request){
        BaseResp baseResp = new BaseResp();
        User user=(User) request.getSession().getAttribute("user");
        if (user!=null){
            baseResp = fansService.deleteFocus(blogUserId,user.getUserId());

        }else {
            baseResp.setErrorMsg("没有登录");
            baseResp.setSuccess(0);
        }
        return baseResp;
    }

    //查询所有的粉丝
    @RequestMapping(value = "/queryAllFans",method = RequestMethod.GET)
    public BaseResp queryAllFans(HttpServletRequest request){
        BaseResp baseResp = new BaseResp();
        User user=(User) request.getSession().getAttribute("user");
        if (user!=null){
            baseResp = fansService.queryAllFans(user.getUserId());
        }else {
            baseResp.setErrorMsg("没有登录");
            baseResp.setSuccess(0);
        }
        return baseResp;
    }
    //查询所有的粉丝
    @RequestMapping(value = "addAndremoveFans")
    public BaseResp addAndremoveFans(int viweUserId, int userId, String type){
        BaseResp baseResp= new BaseResp();
        try {
            baseResp = fansService.addAndremoveFans(viweUserId,userId,type);
        } catch (Exception e) {
            e.printStackTrace();
            baseResp.setSuccess(0);
            baseResp.setErrorMsg("服务器异常");
        }
        return baseResp;
    }




}
