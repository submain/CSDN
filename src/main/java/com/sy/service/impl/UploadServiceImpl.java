package com.sy.service.impl;

import com.sy.expection.CsdnExpection;
import com.sy.mapper.DownloadreplyMapper;
import com.sy.mapper.UploadMapper;
import com.sy.mapper.UserMapper;
import com.sy.model.Download;
import com.sy.model.Downloadreply;
import com.sy.model.Upload;
import com.sy.model.User;
import com.sy.service.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
public class UploadServiceImpl implements UploadService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UploadMapper mapper;
    @Autowired
    private DownloadreplyMapper downloadreplyMapper;
    @Transactional(isolation = Isolation.DEFAULT,propagation = Propagation.REQUIRED)
    @Override
    public Integer save(Upload download) throws CsdnExpection{
        if (download.getTitle() == null || "".equals(download.getTitle().trim())) {
            throw new CsdnExpection("标题不能为空");
        }
        if (download.getSize() == null) {
            throw new CsdnExpection("大小异常");
        }
        if (download.getPrice() == null ) {
            throw new CsdnExpection("用户名不能为空");
        }
        if (download.getCategoryid() == null ) {
            throw new CsdnExpection("类别不能为空");
        }
        if (download.getCategoryid2() == null ) {
            throw new CsdnExpection("类别不能为空");
        }
        if (download.getLeixin() == null ) {
            throw new CsdnExpection("类型不能为空");
        }
//        更新用户上传量
        User user=userMapper.selectUserByUserId(download.getUserid());
        Integer resourceCount=user.getResourceCount();
        userMapper.resourceCount((resourceCount+1),download.getUserid());
        return mapper.insert(download);
    }

    @Override
    public Integer remove(Integer userid, Integer id) throws CsdnExpection{
        return null;
    }

    @Override
    public List<Upload> findByUserid(Integer userid) throws CsdnExpection{
        return null;
    }

    @Override
    public List<Upload> findByLike(String keyword) throws CsdnExpection{
        return null;
    }

    @Override
    public Upload findById(Integer id) throws CsdnExpection{
        return mapper.selectById(id);
    }

    @Override
    public List<Upload> findAll(Upload upload) throws CsdnExpection{
//更新数据评分

        return mapper.selectAll(upload);
    }

    @Override
    public Integer findAllCount(Upload download) throws CsdnExpection{
        return mapper.selectAllCount(download);
    }

    @Override
    public Integer modifReplyCount( Integer id,Integer replyCount) {
        Integer appraise=null;
        return mapper.updataReplyCount(id,replyCount,appraise);
    }
}
