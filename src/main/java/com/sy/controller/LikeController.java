package com.sy.controller;

import com.sy.model.User;
import com.sy.model.resp.BaseResp;
import com.sy.service.FansService;
import com.sy.service.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class LikeController {
    @Autowired
    private LikeService likeService;
    @RequestMapping(value = "/queryIsLike",method = RequestMethod.GET)
    public BaseResp queryIsLike(Integer blogId, HttpServletRequest request){

        BaseResp baseResp = new BaseResp();
        User user=(User) request.getSession().getAttribute("user");
        if (user !=null){
            baseResp = likeService.query(blogId,user.getUserId());
        }else {
            baseResp.setSuccess(0);
        }

//        System.out.println("13131");
//        System.out.println(baseResp);
        return baseResp;
    }

    @RequestMapping("/addLike")
    public BaseResp addLike(Integer userId,Integer blogId){
        BaseResp baseResp = null;
        baseResp = likeService.add(blogId,userId);
        return baseResp;
    }
    @RequestMapping("/deleteLike")
    public BaseResp deleteLike(Integer userId,Integer blogId){
        BaseResp baseResp = null;
        baseResp = likeService.delete(blogId,userId);
        return baseResp;
    }

    //查找所有给我点赞的人
    @RequestMapping(value = "/queryLikeId",method = RequestMethod.GET)
    public BaseResp queryLikeId(HttpServletRequest request){
        BaseResp baseResp = new BaseResp();
        User user = (User) request.getSession().getAttribute("user");
        if(user!=null){
            baseResp = likeService.queryLikeInformation(user.getUserId());
            baseResp.setSuccess(1);
        }else {
            baseResp.setSuccess(0);
        }
        return baseResp;
    }









}
