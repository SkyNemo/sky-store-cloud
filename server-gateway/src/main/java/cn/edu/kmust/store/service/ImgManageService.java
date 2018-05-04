package cn.edu.kmust.store.service;


import cn.edu.kmust.store.client.ProductImageFeignClient;
import cn.edu.kmust.store.entity.ImgManage;
import cn.edu.kmust.store.entity.ProductImage;
import cn.edu.kmust.store.repository.ImgManageRepository;
import cn.edu.kmust.store.utils.SSHUtil;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDateTime;

@Component
public class ImgManageService {


    private static final String TYPE_DETAIL = "type_detail";
    private static final String TYPE_SINGLE = "type_single";


    private static final String USER = "root";
    private static final String PASSWORD = "springcloud123";
    private static final String HOST = "118.24.180.215";


    @Resource
    private ProductImageFeignClient productImageFeignClient;


    @Resource
    private ImgManageRepository imgManageRepository;

    @Scheduled(cron = "0/10 * * * * ?")
    public void monitor() throws FileNotFoundException {

        Long count = imgManageRepository.count();

        if (count != 0) {
            ImgManage imgManage = imgManageRepository.findAll().get(0);

            this.imgManage(imgManage);
        }

        System.out.println(LocalDateTime.now() + "\t:\t" + count);


    }


    public void imgManage(ImgManage imgManage) throws FileNotFoundException {

        // 判断操作类型 1为增加  0为删除
        if (imgManage.getType() == 1) {
            Integer imgId = imgManage.getImageId();
            File img = null;

            ProductImage productImage = productImageFeignClient.findProductImageById(imgId);

            if (productImage != null) {
                String imgType = productImage.getType();

                if (imgType.equals(TYPE_DETAIL)) {

                    try {

                        img = ResourceUtils.getFile("classpath:static/img/productDetail/" + imgId + ".jpg");

                    } catch (FileNotFoundException e) {

                        //构建服务端路径
                        String romotePath = buildRomotePathDetail(imgId);
                        //构建本地路径
                        String localPath = buildLocalPathDetail();
                        //下载图片
                        String result = downloadImage(romotePath, localPath);

                        if (SSHUtil.SUCCESS.equals(result)) {
                            imgManageRepository.delete(imgManage);
                        }
                    }

                } else if (imgType.equals(TYPE_SINGLE)) {

                    String resultSingle = downloadImage(buildRomotePathSingle(imgId), buildLocalPathSingle());
                    String resultSingleMiddle = downloadImage(buildRomotePathSingleMiddle(imgId), buildLocalPathSingleMiddle());
                    String resultSingleSmall = downloadImage(buildRomotePathSingleSmall(imgId), buildLocalPathSingleSmall());

                    if (resultSingle.equals(SSHUtil.SUCCESS) && resultSingleMiddle.equals(SSHUtil.SUCCESS) && resultSingleSmall.equals(SSHUtil.SUCCESS)) {
                        imgManageRepository.delete(imgManage);
                    }
                }


            }


        } else if(imgManage.getType() == 0){

            System.out.println("104------------------------");

            try {
                System.out.println("107------------------------");
                File img = ResourceUtils.getFile(buildLocalPathDetail() + "/" + imgManage.getImageId() + ".jpg");
                System.out.println("109-------" + img.getPath() + " : " + img.getName());
                img.delete();
                imgManageRepository.delete(imgManage);
            }catch (FileNotFoundException e){
                System.out.println("112------------------------");
            }

            try {
                File img = ResourceUtils.getFile(buildLocalPathSingle() + imgManage.getImageId() + ".jpg");
                img.delete();
                imgManageRepository.delete(imgManage);
            }catch (FileNotFoundException e){

            }

            try {
                File img = ResourceUtils.getFile(buildLocalPathSingleMiddle() + imgManage.getImageId() + ".jpg");
                img.delete();
                imgManageRepository.delete(imgManage);
            }catch (FileNotFoundException e){

            }
            try {
                File img = ResourceUtils.getFile(buildLocalPathSingleSmall() + imgManage.getImageId() + ".jpg");
                img.delete();
                imgManageRepository.delete(imgManage);
            }catch (FileNotFoundException e){

            }

        }

    }


    private String downloadImage(String romotePath, String localPath) {
        String result = SSHUtil.getFileFromRemote(HOST, USER, PASSWORD, romotePath, localPath);
        return result;
    }


    private String buildRomotePathDetail(Integer id) {

        return "/img/productDetail/" + id + ".jpg";

    }

    private String buildRomotePathSingle(Integer id) {

        return "/img/productSingle/" + id + ".jpg";

    }

    private String buildRomotePathSingleMiddle(Integer id) {

        return "/img/productSingle_middle/" + id + ".jpg";

    }

    private String buildRomotePathSingleSmall(Integer id) {

        return "/img/productSingle_small/" + id + ".jpg";

    }


    private String buildLocalPathDetail() throws FileNotFoundException {

        String pathStr = "classpath:static/img/productDetail/";

        String path = ResourceUtils.getFile(pathStr).getPath();

        return path;

    }

    private String buildLocalPathSingle() throws FileNotFoundException {

        String pathStr = "classpath:static/img/productSingle/";

        String path = ResourceUtils.getFile(pathStr).getPath();

        return path;

    }

    private String buildLocalPathSingleMiddle() throws FileNotFoundException {

        String pathStr = "classpath:static/img/productSingle_middle/";

        String path = ResourceUtils.getFile(pathStr).getPath();

        return path;

    }

    private String buildLocalPathSingleSmall() throws FileNotFoundException {

        String pathStr = "classpath:static/img/productSingle_small/";

        String path = ResourceUtils.getFile(pathStr).getPath();

        return path;

    }

}
