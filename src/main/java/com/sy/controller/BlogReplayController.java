package com.sy.controller;

import com.sy.model.User;
import com.sy.model.resp.BaseResp;
import com.sy.service.BlogReplayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@RestController
public class BlogReplayController {
    @Autowired
    private BlogReplayService blogReplayService;
    @RequestMapping(value = "/addBlogReplay",method = RequestMethod.GET)
    public BaseResp addBlogReplay(String replay_value,Integer userid,Integer blogid){
        BaseResp baseResp = blogReplayService.addReplay(blogid,replay_value,userid,new Date());
        return baseResp;
    }
    @RequestMapping(value = "/queryReplayByBlogId",method = RequestMethod.GET)
    public BaseResp queryByBlogId(Integer blogid){
        BaseResp baseResp = blogReplayService.queryByBlogId(blogid);
        return baseResp;
    }
    //查找评论用户的信息
    @RequestMapping(value = "/queryReplayInformation",method = RequestMethod.GET)
    public BaseResp queryReplayInformation(HttpServletRequest request){
        BaseResp baseResp = new BaseResp();
        User user = (User) request.getSession().getAttribute("user");
        if (user!=null){
            baseResp = blogReplayService.queryByUserId(user.getUserId());
        }else {
            baseResp.setSuccess(0);
            baseResp.setErrorMsg("没有登录");
        }
        return baseResp;
    }






}
