package com.sy.service.impl;

import com.sy.mapper.DownloadMapper;
import com.sy.model.Download;
import com.sy.service.DownloadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
public class DownloadServiceImpl implements DownloadService {
    @Autowired
    private DownloadMapper mapper;
    @Override
    @Transactional(isolation = Isolation.DEFAULT,propagation = Propagation.REQUIRED)
    public Integer save(Download download) {
        return mapper.insert(download);
    }

    @Override
    public Integer remove(Integer userid, Integer id) {
        return null;
    }

    @Override
    public List<Download> findAll(Integer page, Integer pageSize) {
        return null;
    }

    @Override
    public List<Download> findByUserid(Integer userid, Integer page, Integer pageSize) {
        return mapper.selectByUserid(userid,page,pageSize);
    }

    @Override
    public Integer findAllCount(Integer userid) {
        return mapper.selectAllCount(userid);
    }
}
