package com.sy.service;

import com.sy.model.resp.BaseResp;
import org.apache.ibatis.annotations.Param;

public interface FansService {
    //通过粉丝id查询
    BaseResp queryByFansIdResult(Integer fansId);
    //查询是否关注
    BaseResp queryIsFocus(int fansedid, int fansid);

    //增加
    BaseResp addFocus(int fansedid, int fansid);
    //删除
    BaseResp deleteFocus(int fansedid, int fansid);
    //查找用户的所有粉丝
    BaseResp queryAllFans(int userId);

    //个人详情页增加取消关注合并
    BaseResp addAndremoveFans(int viweUserId,int userId,String type) throws Exception;

}
