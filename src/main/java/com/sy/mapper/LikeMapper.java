package com.sy.mapper;

import com.sy.model.Blog;
import com.sy.model.Like;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface LikeMapper {
    //通过用户id和博文id查询
   List<Like> queryByBlog_idAndUser_Id(@Param("blogId") int blog_id, @Param("userId") int user_Id);
    //新增点赞列表
    Integer addLike(@Param("blogId") int blog_id, @Param("userId") int user_Id);
    //删除点赞
    Integer deleteLike(@Param("blogId") int blog_id, @Param("userId") int user_Id);
    //通过博文Id查找
    List<Like> queryByBlogId(int blogId);


}
