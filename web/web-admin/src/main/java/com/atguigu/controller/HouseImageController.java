package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.entity.HouseImage;
import com.atguigu.result.Result;
import com.atguigu.service.HouseImageService;
import com.atguigu.util.QiniuUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * @author 刘翰林
 * @create 2023-06-07 21:59
 */
@Controller
@RequestMapping("/houseImage")
public class HouseImageController extends BaseController{

    @Reference
    private HouseImageService houseImageService;

    /**
     * 去上传界面
     * @date
     * @author
     * @return
     * @throws
     */
    @RequestMapping("/uploadShow/{houseId}/{type}")
    public ModelAndView toUploadPage(@PathVariable("houseId") Long houseId, @PathVariable("type") Integer type){

        ModelAndView mav = new ModelAndView();

        mav.setViewName("house/upload");

        mav.addObject("houseId",houseId);
        mav.addObject("type",type);

        return mav;
    }

    /**
     * 上传房源或房产图片
     * @date
     * @author
     * @return
     * @throws
     */
    @ResponseBody
    @RequestMapping("/upload/{houseId}/{type}")
    public Result uploadHouseImage(@PathVariable("houseId") Long houseId, @PathVariable("type") Integer type,
                                   @RequestParam("file") MultipartFile[] files){    //此处的MultipartFile是个接口，里面定义了一系列操作文件的方法，注意这里传过来的是实现了这个接口的文件类（个人猜测）

        //1、上传到七牛云
        try {
            if(files != null && files.length > 0){
                for(MultipartFile file : files){

                    byte[] bytes = file.getBytes(); //文件是以二进制流传递到后端的，也就是文件的传输是二进制流传输的，所以我们上传其实上传的也是二进制流，
                    //MultipartFile中getBytes方法写好了将文件转换成二进制流，所以这也是为啥使用MultipartFile的原因
                    //得到文件的名字
                    String originalFilename = file.getOriginalFilename();

                    //利用uuid随机生成一个名字
                    String newFileName = UUID.randomUUID().toString();

                    //通过工具类qiniu上传到七牛云
                    QiniuUtils.upload2Qiniu(bytes,newFileName);

                    //2、在数据库表中插入图片url
                    HouseImage houseImage = new HouseImage();
                    houseImage.setHouseId(houseId);
                    houseImage.setType(type);
                    houseImage.setImageName(originalFilename);
                    houseImage.setImageUrl("http://rvvm97mge.hn-bkt.clouddn.com/" + newFileName);
                    houseImageService.insert(houseImage);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }


        return Result.ok();

    }

    /**
     * 删除图片
     * @date
     * @author
     * @return
     * @throws
     */
    @RequestMapping("/delete/{houseId}/{id}")
    public String delete(@PathVariable("houseId") Long houseId,@PathVariable Long id){

        houseImageService.delete(id);

        return "redirect:/house/" + houseId;

    }


}
