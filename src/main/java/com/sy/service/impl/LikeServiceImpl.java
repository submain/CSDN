package com.sy.service.impl;

import com.sy.mapper.BlogMapper;
import com.sy.mapper.LikeMapper;
import com.sy.mapper.UserMapper;
import com.sy.model.Blog;
import com.sy.model.Like;
import com.sy.model.User;
import com.sy.model.resp.BaseResp;
import com.sy.service.LikeService;
import jdk.nashorn.internal.runtime.linker.LinkerCallSite;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional(readOnly = true)
public class LikeServiceImpl implements LikeService {
    @Autowired
    private BlogMapper blogMapper;
    @Autowired
    private LikeMapper likeMapper;
    @Autowired
    private UserMapper userMapper;
    @Override
    public BaseResp query(Integer blog_id, Integer userid) {
        BaseResp baseResp = new BaseResp();
        baseResp = new BaseResp();
        List<Like> likeList = likeMapper.queryByBlog_idAndUser_Id(blog_id,userid);
        if (likeList.size()!=0){

            baseResp.setSuccess(1);


        }else {
            baseResp.setSuccess(0);
            baseResp.setErrorMsg("该用户没有点赞该博客");
        }
        return baseResp;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public BaseResp add(Integer blog_id, Integer userid) {

        BaseResp baseResp = new BaseResp();
        int i = likeMapper.addLike(blog_id,userid);
        if (i!=0){
            baseResp.setSuccess(1);
        }else {
            baseResp.setSuccess(0);
        }
        return baseResp;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public BaseResp delete(Integer blog_id, Integer userid) {
        BaseResp baseResp = new BaseResp();
        int i = likeMapper.deleteLike(blog_id,userid);
        if (i!=0){
            baseResp.setSuccess(1);
        }else {
            baseResp.setSuccess(0);
        }
        return baseResp;
    }

    @Override
    public BaseResp queryLikeInformation(int userId) {
        BaseResp baseResp = new BaseResp();
        //存储姓名
        //存储文章标题
        List<List<String>> lists = new ArrayList<>();
        //首先通过userID查找所有的文章
        List<Blog> blogList = blogMapper.queryByUserId(userId);
        if (blogList.size()!=0){
            for (Blog blog: blogList){
                int blogId = blog.getId();
                //通过blogId查找点赞表
                List<Like> likeList = likeMapper.queryByBlogId(blogId);
                for (Like like :likeList){
                    //得到点赞的用户
                    int likeUserId = like.getUserid();
                    User user = userMapper.selectUserByUserId(likeUserId);
                    if (user!=null){
                        String userName = user.getNickname();
                        List<String> list=new ArrayList<>();
                        list.add(blog.getTitle());
                        list.add(userName);
                        lists.add(list);
                    }

                }
            }
            baseResp.setSuccess(1);
            baseResp.setData(lists);
        }else {
            baseResp.setSuccess(0);
            baseResp.setErrorMsg("您没有博文");
        }
        return baseResp;
    }
}
