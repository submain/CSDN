package com.sy.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sy.mapper.BlogMapper;
import com.sy.model.Blog;
import com.sy.model.resp.BaseResp;
import com.sy.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class BlogServiceImpl implements BlogService {
    @Autowired
    private BlogMapper blogMapper;
    //新增博客
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public BaseResp addBlog(String title,String content,String publishForm,String category,Integer userID) throws Exception {
        BaseResp baseResp=new BaseResp();
        Blog blog=new Blog();
        blog.setTitle(title);
        blog.setContent(content);
        blog.setPublishForm(publishForm);
        blog.setCategory(category);
        blog.setUserid(userID);

        int result=blogMapper.insertNewBlog(blog);
        System.out.println(blog.getId());
        if(result>0){
            baseResp.setData(blog.getId());
            baseResp.setSuccess(1);
            baseResp.setErrorMsg("发布成功");
        }else {
            baseResp.setSuccess(0);
            baseResp.setErrorMsg("发布失败");
        }
        return baseResp;
    }


    //下方杨

    public BaseResp queryBykEYResult(String key) {
        BaseResp baseResp = new BaseResp();
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("%");
        stringBuffer.append(key);
        stringBuffer.append("%");
        List<Blog> blogList = blogMapper.queryByKey(stringBuffer.toString());
        if (blogList.size()!=0){
            baseResp.setSuccess(1);
            baseResp.setData(blogList);
        }

        return  baseResp;
    }
    //查找所有
    public BaseResp queryAll(String pageNumStr){
        BaseResp baseResp = new BaseResp();

        Integer pageNum = 1;
        Integer pageSize = 12;
        pageNum = Integer.parseInt(pageNumStr);
        PageHelper.startPage(pageNum,pageSize);
        List<Blog> blogList = blogMapper.queryAll();
        Page<Blog> blogPage = (Page<Blog>)blogList;
//        System.out.println(blogPage+"hga1");
//        for (Blog blog : blogList){
//            System.out.println(blog);
//        }

//        List<Blog> blogList = blogMapper.queryAll();
        if (blogList.size()!=0){
            baseResp.setSuccess(1);
            baseResp.setData(blogList);
            baseResp.setCount(blogPage.getPageSize());
            return baseResp;
        }
        return null;
    }

    @Override
    public BaseResp queryByCategoryResult(String pageNumStr,String category) {
        BaseResp baseResp = new BaseResp();
//        System.out.println("service曾执行");
        Integer pageNum = 1;
        Integer pageSize = 12;
        List<Blog> blogList1 = blogMapper.queryByCategory(category);
//        System.out.println(blogList1);

        pageNum = Integer.parseInt(pageNumStr);
        PageHelper.startPage(pageNum,pageSize);


        List<Blog> blogList = blogMapper.queryByCategory(category);
        //System.out.println(blogList);
////
////        Page<Blog> blogPage = (Page<Blog>)blogList;
////        System.out.println(blogPage+"313131");
//        for (Blog blog : blogList){
//            System.out.println(blog);
//        }

//        List<Blog> blogList = blogMapper.queryAll();
        if (blogList.size()!=0){
            baseResp.setSuccess(1);
            baseResp.setData(blogList);
            baseResp.setCount(blogList1.size());
            return baseResp;
        }
        return null;
    }

    @Override
    public BaseResp queryOrderByReadCount(String pageNumStr) {
        BaseResp baseResp = new BaseResp();

        Integer pageNum = 1;
        Integer pageSize = 12;
        pageNum = Integer.parseInt(pageNumStr);
        PageHelper.startPage(pageNum,pageSize);
        List<Blog> blogList = blogMapper.queryOrderByReadCount();
        Page<Blog> blogPage = (Page<Blog>)blogList;
        if (blogList.size()!=0){
            baseResp.setSuccess(1);
            baseResp.setData(blogList);
            baseResp.setCount(blogPage.getPageSize());
            return baseResp;
        }
        return null;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public BaseResp addReadCount(Integer id) {
        BaseResp baseResp = new BaseResp();
        Integer i = blogMapper.addReadCount(id);
        if (i !=0){

            baseResp.setSuccess(1);

        }else {
            baseResp.setSuccess(0);
        }
        return baseResp;
    }

    @Override
    public BaseResp queryUserIdById(int id) {
        BaseResp baseResp = new BaseResp();
        Integer userId = blogMapper.queryUserIdById(id);
        if (userId!=0){
            baseResp.setSuccess(1);
            baseResp.setData(userId);
        }else {
            baseResp.setSuccess(0);
        }
        return baseResp;
    }

    @Override
    public BaseResp queryById(int id) {
        BaseResp baseResp = new BaseResp();
        List<Blog> blogList = blogMapper.queryBlogByBlogId(id);
        if (blogList.size()!=0){
            baseResp.setSuccess(200);
            baseResp.setData(blogList);

        }else {
            baseResp.setSuccess(404);
            baseResp.setErrorMsg("没有找到该博文");
        }
        return baseResp;
    }

    @Override
    public BaseResp queryByUserId(int userId) {
        BaseResp baseResp = new BaseResp();
        List<Blog> blogList = blogMapper.queryByUserId(userId);
        if (blogList.size()!=0){
            if (blogList.size()>5){
                List<Blog> blogList1 = blogList.subList(0,4);


                baseResp.setData(blogList1);
            }else {
                baseResp.setData(blogList);
            }

            baseResp.setSuccess(200);

        }else {
            baseResp.setSuccess(404);
        }

        return baseResp;
    }


    public BaseResp queryByRecommend() {
        BaseResp baseResp = new BaseResp();
        List<Blog> blogList = blogMapper.queryByRecomment();
        if (blogList.size()!=0){
            baseResp.setSuccess(1);
            baseResp.setData(blogList);
            return baseResp;
        }
        return null;

    }

}
