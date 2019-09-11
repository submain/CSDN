package com.sy.service;


import com.sy.model.resp.BaseResp;


import java.util.Date;


public interface BlogReplayService {
    //添加评论
    BaseResp addReplay(Integer blog_id, String comment, int commentuserid, Date time);
    //根据博文id查找评论
    BaseResp queryByBlogId(int blog_id);
    //查找评论用户信息
    BaseResp queryByUserId(int userId);
}
