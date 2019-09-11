package com.sy.mapper;

import com.sy.model.Blog;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BlogMapper {

    //新增博客
    int insertNewBlog(Blog blog);

//下方杨


    //通过关键词查询
    List<Blog> queryByKey(String key);
    //查找所有
    List<Blog> queryAll();
    //查找推荐
    List<Blog> queryByRecomment();

    List<Blog> queryByCategory(String category);

    //原生分页查找类别

    List<Blog> queryByCategoryByPage(@Param("category") String category, @Param("initNum") int initNum, @Param("pageSize") int pageSize);
    //通过userId去查找博客
    List<Blog> queryByUserId(int userId);
    //通过阅读量查找数据
    List<Blog> queryOrderByReadCount();
    //阅读量点击增加
    Integer addReadCount(int id);
    //查找专家，通过userId查找博客数量最多的用户，即为专家
    Integer queryMaxCountByUserId();

    //通过博客Id查找博主id
    Integer queryUserIdById(int id);
    //通过博客ID查找博客
    List<Blog> queryBlogByBlogId(int id);
}
