package com.sy.mapper;

import com.sy.model.Blog_Replay;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface BlogReplayMapper {
    //添加评论
    Integer addReplay(@Param("blog_id") Integer blog_id, @Param("comment") String comment, @Param("commentuserid") int commentuserid, @Param("time") Date time);
    //根据博文id查找评论
    List<Blog_Replay> queryByBlogId(int blog_id);
}
