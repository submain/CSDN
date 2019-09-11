package com.sy.service.impl;

import com.fasterxml.jackson.databind.ser.Serializers;
import com.sy.mapper.BlogMapper;
import com.sy.mapper.BlogReplayMapper;
import com.sy.mapper.UserMapper;
import com.sy.model.Blog;
import com.sy.model.Blog_Replay;
import com.sy.model.User;
import com.sy.model.resp.BaseResp;
import com.sy.service.BlogReplayService;
import jdk.nashorn.internal.runtime.linker.LinkerCallSite;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.ws.soap.Addressing;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class BlogReplayServiceImpl implements BlogReplayService {
    @Autowired
    private BlogReplayMapper blogReplayMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private BlogMapper blogMapper;
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public BaseResp addReplay(Integer blog_id, String comment, int commentuserid, Date time) {
        BaseResp baseResp = new BaseResp();
        int result = blogReplayMapper.addReplay(blog_id,comment,commentuserid,time);
        if (result!=0){
            baseResp.setSuccess(1);
        }else {
            baseResp.setSuccess(0);
        }
        return baseResp;
    }

    @Override
    public BaseResp queryByBlogId(int blog_id) {
        BaseResp baseResp = new BaseResp();
        List<Blog_Replay> blog_replayList =  blogReplayMapper.queryByBlogId(blog_id);

        if (blog_replayList.size()!=0){
            for (Blog_Replay blog_replay:blog_replayList){
                int replayUserId = blog_replay.getCommentuserid();
                User user=userMapper.selectUserByUserId(replayUserId);
                blog_replay.setUser(user);
            }
            baseResp.setSuccess(1);
            baseResp.setData(blog_replayList);
        }else {
            baseResp.setSuccess(0);
            baseResp.setErrorMsg("没有回复");
        }

        return baseResp;
    }

    @Override
    public BaseResp queryByUserId(int userId) {
        BaseResp baseResp = new BaseResp();
        List<Blog> blogList = blogMapper.queryByUserId(userId);
        List<List<String>> lists = new ArrayList<>();
        if (blogList.size()!=0){
            for (Blog blog :blogList){
                int blogId = blog.getId();
                //通过blogId查找评论表
                List<Blog_Replay> blog_replays = blogReplayMapper.queryByBlogId(blogId);
                if (blog_replays.size()!=0){
                    for (Blog_Replay blog_replay: blog_replays){
                        int commentUserId = blog_replay.getCommentuserid();
                        User user = userMapper.selectUserByUserId(commentUserId);
                        String name = user.getNickname();
                        List<String> list = new ArrayList<>();
                        list.add(name);
                        list.add(blog.getTitle());
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日");
                        list.add(simpleDateFormat.format(blog_replay.getTime()));
                        lists.add(list);
                    }
                }
            }
            baseResp.setSuccess(1);
            baseResp.setData(lists);
        }else {
            baseResp.setSuccess(0);
            baseResp.setErrorMsg("没有博文啊，兄弟");
        }
        return baseResp;
    }
}
