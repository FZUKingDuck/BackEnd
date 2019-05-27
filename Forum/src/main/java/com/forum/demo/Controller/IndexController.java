package com.forum.demo.Controller;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.forum.demo.DAO.CustomDao;
import com.forum.demo.DAO.ImageInfoDao;
import com.forum.demo.DAO.PostsDao;
import com.forum.demo.Entity.CustomEntity;
import com.forum.demo.Entity.ImageInfoEntity;
import com.forum.demo.Entity.PostsEntity;
import com.forum.demo.Model.RankModel;
import com.forum.demo.ResponseResult.Result;
import com.forum.demo.UtilTool.DateUtil;
import com.forum.demo.UtilTool.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/index")
public class IndexController {
    @Autowired
    PostsDao postsDao;

    @Autowired
    ImageInfoDao  imageInfoDao;

    @Autowired
    CustomDao customDao;


    //获取热门帖子
    @GetMapping(value = "/getPostsList")
    public Result getPostsList(){
        Result result = new Result();

        try {
            PageRequest page = PageRequest.of(0,10,Sort.Direction.DESC,"remark");
            List<PostsEntity> list = postsDao.findAll(page).getContent();
            result.setOK("获取成功",list);

        }catch (Exception e){
            e.printStackTrace();
            result.setSysFalse();
            return result;
        }

        return result;
    }

    //测试使用的添加帖子数据的接口
    @GetMapping(value = "/add")
    public void add(){
        PostsEntity postsEntity = new PostsEntity();
        postsEntity.setId(DateUtil.getIdFromDate());
        postsEntity.setUserid("15586219479869");
        postsEntity.setAuthority("1");
        postsEntity.setTitle("测试1");
        postsEntity.setInfo("这是一个测试的数据");
        postsEntity.setCreattime(DateUtil.getTime());
        postsEntity.setUpdatetime(DateUtil.getTime());
        postsEntity.setOperator("15586219479869");
        postsEntity.setType("html");
        postsDao.save(postsEntity);
    }


    //获取固定的一个图片url
    @GetMapping(value = "/getImageUrl")
    public Result getImageUrl(){
        Result result = new Result();

        List<String > list = new ArrayList<>();
        list.add("/page1.jpg");
        list.add("/page2.jpg");
        result.setOK("",list);
        return result;

    }

    //获取指定id的图片
    @GetMapping(value = "/getImage" , produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] getImage(@PathParam("id")String id){
            if(!StringUtils.checkKey(id)) {
                return null;
            }
            String pathHead = "F:\\Image\\";
            try {
                //获取数据库中的数据
                Optional<ImageInfoEntity> result = imageInfoDao.findById(id);
                if(result==null||!result.isPresent()) {
                    return null;
                }

                ImageInfoEntity imageInfoEntity = result.get();
                String url =pathHead + imageInfoEntity.getUrl();

                File image = new File(url);
                FileInputStream inputStream = new FileInputStream(image);

                byte[] bytes = new byte[inputStream.available()];
                inputStream.read(bytes, 0, inputStream.available());
                return bytes;
            }
            catch (Exception e){
                e.printStackTrace();
                return null;
            }

    }

    //获取排行榜数据
    @GetMapping(value = "/getRank")
    public Result getRank(){
        Result result = new Result();

        PageRequest page = PageRequest.of(0,10,Sort.Direction.DESC,"remark");
        List<CustomEntity> customEntity =customDao.findAll(page).getContent();

        List<RankModel> list  =new ArrayList<>();
        for (CustomEntity custom:customEntity) {
            RankModel item = new RankModel();
            item.setId(custom.getId());
            item.setName(custom.getName());
            item.setCount(custom.getRemark());
            list.add(item);
        }


        result.setOK("成功",list);
        return result;

    }

}
