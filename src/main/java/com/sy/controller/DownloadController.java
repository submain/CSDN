package com.sy.controller;

import com.sy.expection.CsdnExpection;
import com.sy.model.Download;
import com.sy.model.Upload;
import com.sy.model.User;
import com.sy.model.resp.BaseResp;
import com.sy.service.DownloadService;
import com.sy.service.UploadService;
import com.sy.service.UserServic;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.List;

@RestController
public class DownloadController {
    @Autowired
    private UploadService service;
    @Autowired
    private UserServic servic;
    @Autowired
    private DownloadService downloadService;


    @RequestMapping(value = "downloadResource", method = RequestMethod.GET)
    public void download(Integer id, HttpServletRequest request, HttpServletResponse response) throws IOException {

//        先验证用户登录
        User user = (User) request.getSession().getAttribute("user");
        System.out.println(user+"111");
        if (user == null) {
            return ;
        }
        System.out.println(user.getUsername());
        //        实时更新用户信息
        User user1 = new User();
        try {
            Integer userId = user.getUserId();
            user1 = (User) servic.findUserByUserId(userId).getData();
        } catch (Exception e) {
            e.printStackTrace();
        }

//    再查看资源是否存在
        String path = null;
        Upload upload = new Upload();
        try {
            upload = service.findById(id);
            if (upload == null) {
                return;
            }
        } catch (CsdnExpection csdnExpection) {
            csdnExpection.printStackTrace();
            return;
        }
        System.out.println(" 再查看资源"+upload);
//判断积分是否充足
        Integer uploadUsrid = upload.getUserid();
        if (user1.getDownloadmoney() < upload.getPrice()) {
            return;
        }
        System.out.println(" 判断积分是否充足"+user1.getDownloadmoney());
//        积分操作
        Integer count = 0;
        try {
            count = servic.downloadMoney(upload.getPrice(), user1.getUserId(), uploadUsrid,id);
        } catch (CsdnExpection e) {
            e.printStackTrace();
            return;
        }
        System.out.println("积分操作"+count);
        //        开始下载
      if (count>0) {
          path = upload.getSrc();
          String filename = upload.getTitle() + "." + path.substring(path.lastIndexOf(".") + 1);
          System.out.println("资源存在" + filename);
          System.out.println(path);
          //获取输入流
          InputStream bis = new BufferedInputStream(new FileInputStream(new File(path)));
          //转码，免得文件名中文乱码
          filename = URLEncoder.encode(filename, "UTF-8");
          //设置文件下载头
          response.addHeader("Content-Disposition", "attachment;filename=" + filename);
          //1.设置文件ContentType类型，这样设置，会自动判断下载文件类型
          response.setContentType("multipart/form-data");

          FileUtils.copyFile(new File(path), response.getOutputStream());
          System.out.println("下载完毕");
      }


    }
    @RequestMapping(value = "downloadResourceMixi",method =RequestMethod.GET)
    public BaseResp downloadResourceMixi(Integer userid,Integer page,Integer pageSize,HttpServletResponse res){
        BaseResp resp=new BaseResp();
        List<Download> lists=downloadService.findByUserid(userid,(page-1)*pageSize,pageSize);
        Integer count=downloadService.findAllCount(userid);
        if (lists!=null){
            resp.setSuccess(200);
            res.setStatus(200);
            resp.setData(lists);
            resp.setCount(count);
            return resp;
        }else {
            res.setStatus(400);
            resp.setSuccess(400);
            resp.setErrorMsg("资源为找到");
            return resp;
        }
    }
}
