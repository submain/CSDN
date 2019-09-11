package com.sy.service;

import com.sy.model.Download;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DownloadService {
    Integer save(Download download);
    Integer remove(Integer userid, Integer id);
    List<Download> findAll(Integer page, Integer pageSize);
    List<Download>  findByUserid(Integer userid, Integer page, Integer pageSize);
    Integer findAllCount(Integer userid);
}
