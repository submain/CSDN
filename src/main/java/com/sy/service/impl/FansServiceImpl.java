package com.sy.service.impl;

import com.sy.mapper.BlogMapper;
import com.sy.mapper.FansMapper;
import com.sy.mapper.UserMapper;
import com.sy.model.Blog;
import com.sy.model.Fans;
import com.sy.model.resp.BaseResp;
import com.sy.service.FansService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

@Service
@Transactional(readOnly = true)
public class FansServiceImpl implements FansService {
    @Autowired
    private BlogMapper blogMapper;
    @Autowired
    private FansMapper fansMapper;
    @Autowired
    private UserMapper userMapper;
    @Override
    public BaseResp queryByFansIdResult(Integer fansId) {
        BaseResp baseResp = new BaseResp();
        List<String>  list = fansMapper.queryByfansid(fansId);
        Map<Integer,List<Blog>> map = new HashMap<>();

        if (list!=null){
            for(String string:list){
                int userId = Integer.parseInt(string);
//                System.out.println(userId);
                List<Blog> blogList = blogMapper.queryByUserId(userId);
//                System.out.println(blogList);
                map.put(userId,blogList);
            }
            baseResp.setSuccess(1);
            baseResp.setData(map);
            return baseResp;
        }else {
            baseResp.setSuccess(0);
            baseResp.setErrorMsg("用户没有关注任何人");
        }
        return null;
    }
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public BaseResp queryIsFocus(int fansedid, int fansid) {
        BaseResp baseResp = new BaseResp();
        List<Fans>  fansList = fansMapper.queryIsFocus(fansedid,fansid);
        if (fansList.size()!=0){
            baseResp.setSuccess(1);
            baseResp.setErrorMsg("关注");
        }else {
            baseResp.setSuccess(0);
            baseResp.setErrorMsg("未关注");
        }
         return baseResp;
    }
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public BaseResp addFocus(int fansedid, int fansid) {
        BaseResp baseResp = new BaseResp();
        int result = fansMapper.addFocus(fansedid,fansid);
        if (result!=0){
            baseResp.setSuccess(1);
            baseResp.setErrorMsg("关注成功");
        }else {
            baseResp.setSuccess(0);
        }
        return baseResp;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public BaseResp deleteFocus(int fansedid, int fansid) {
        BaseResp baseResp = new BaseResp();
        int result = fansMapper.deleteFocus(fansedid,fansid);
        if (result!=0){
            baseResp.setSuccess(1);
            baseResp.setErrorMsg("取消关注成功");
        }else {
            baseResp.setSuccess(0);
        }
        return baseResp;
    }

    @Override
    public BaseResp queryAllFans(int userId) {
        BaseResp baseResp = new BaseResp();
        List<Fans> fansList = fansMapper.queryAllFans(userId);
        List<String> nameList = new ArrayList<>();
        if (fansList.size()!=0){
            for (Fans fans:fansList){
                int fansId = fans.getFansid();
                String fansName = userMapper.selectUserByUserId(fansId).getNickname();
                nameList.add(fansName);
            }
            baseResp.setData(nameList);
            baseResp.setSuccess(1);
        }else {
            baseResp.setSuccess(0);
            baseResp.setErrorMsg("你是没有粉丝的，小兄弟！");
        }
        return baseResp;
    }
    //个人详情页增加取消关注合并
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public BaseResp addAndremoveFans(int viweUserId, int userId, String type) throws Exception {
       BaseResp baseResp=new BaseResp();
       int result=0;
       if("attention".equals(type)){
            result=fansMapper.addFocus(viweUserId,userId);
       }else {
            result=fansMapper.deleteFocus(viweUserId,userId);
       }
       if (result>0){
           baseResp.setErrorMsg("取消关注成功");
           baseResp.setSuccess(1);
       }else {
           baseResp.setErrorMsg("取消关注失败");
           baseResp.setSuccess(0);
       }
        return baseResp;
    }
}
