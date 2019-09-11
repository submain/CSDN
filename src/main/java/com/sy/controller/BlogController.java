package com.sy.controller;

import com.sy.model.resp.BaseResp;
import com.sy.service.BlogService;
import com.sy.service.UserServic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/blog")
public class BlogController {
    @Autowired
    BlogService blogService;
    @Autowired
    private UserServic userServic;
    @RequestMapping(value = "addBlog",method = RequestMethod.POST)
    public BaseResp addBlog(String title,String content,String publishForm,String category,Integer userID){
        BaseResp baseResp=new BaseResp();
        try {
            baseResp=blogService.addBlog(title,content,publishForm,category,userID);
        } catch (Exception e) {
            e.printStackTrace();
        baseResp.setSuccess(0);
        baseResp.setErrorMsg("服务器异常");
    }
        return baseResp;
    }

    //下方杨
    //首页加载所有
    @RequestMapping(value = "/requestAll",method = RequestMethod.GET)
    public BaseResp queryAll(String page){

        BaseResp  baseResp = new BaseResp();
        baseResp = blogService.queryAll(page);
        return baseResp;
    }
    //通过阅读量排序查询
    @RequestMapping(value = "/requestOrderByReadCount",method = RequestMethod.GET)
    public BaseResp requestOrderByReadCount(String page){

        BaseResp  baseResp = new BaseResp();
        baseResp = blogService.queryOrderByReadCount(page);
        return baseResp;
    }
    //查找分类
    @RequestMapping(value = "/requestByCategory",method = RequestMethod.GET)
    public BaseResp requestByCategory(String page,String category){
//        System.out.println("控制层执行...");
        BaseResp  baseResp = new BaseResp();

        baseResp = blogService.queryByCategoryResult(page,category);
        return baseResp;
    }
    //查找推荐
    @RequestMapping(value = "/requestByRecommend",method = RequestMethod.GET)
    public BaseResp requestByRecommend(){
        BaseResp  baseResp = new BaseResp();
        baseResp = blogService.queryByRecommend();
        return baseResp;
    }
    //增加阅读数量
    @RequestMapping(value = "/addReadCount")
    public BaseResp addReadCount(Integer id){
        BaseResp  baseResp = new BaseResp();
        baseResp = blogService.addReadCount(id);
        return baseResp;
    }
    //通过博文ID查找博主id
    @RequestMapping(value = "/queryUserIdById",method = RequestMethod.GET)
    public BaseResp queryUserIdById(Integer blogId){
        BaseResp  baseResp = new BaseResp();
        baseResp = blogService.queryUserIdById(blogId);
        return baseResp;
    }
    //查找博主的信息
    @RequestMapping(value = "/blog/{blogUserId}",method = RequestMethod.GET)
    public BaseResp queryInformationByUserId(@PathVariable("blogUserId") Integer blogUserId){
        BaseResp  baseResp = new BaseResp();
        try {
            baseResp = userServic.findUserByUserId(blogUserId);
        } catch (Exception e) {
            baseResp.setSuccess(0);
        }
        return baseResp;
    }

    //获取博文正文，标题，博主的信息等
    @RequestMapping(value = "/{blogId}",method = RequestMethod.GET)
    public BaseResp queryBlogByBlogId(@PathVariable("blogId") Integer blogId){
//        System.out.println(blogId);
        BaseResp baseResp = new BaseResp();
        baseResp = blogService.queryById(blogId);
        return baseResp;
    }

    //通过userId查找博文
    @RequestMapping(value = "/queryByUserId",method = RequestMethod.GET)
    public BaseResp queryBlogByUserId(Integer userId){
        BaseResp baseResp = new BaseResp();
        baseResp = blogService.queryByUserId(userId);
        return baseResp;
    }


}
