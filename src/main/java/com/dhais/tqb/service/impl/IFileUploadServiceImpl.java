package com.dhais.tqb.service.impl;

import com.dhais.tqb.common.exception.ServiceException;
import com.dhais.tqb.common.utils.PropertiesUtil;
import com.dhais.tqb.service.IFileUploadService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * All rights Reserved, Designed By Fan Jun
 *
 * @author Fan Jun
 * @version 1.0
 * @since 2022/8/9 17:23
 */
@Service
public class IFileUploadServiceImpl implements IFileUploadService {

    private static final String WIN_IMG_UPLOAD_URL = "windows.img.uploadUrl";

    private static final String LINUX_IMG_UPLOAD_URL = "linux.img.uploadUrl";

    private static final String MAC_IMG_UPLOAD_URL = "mac.img.uploadUrl";

    private static final String IMG_DOMAIN = "img.domain";

    private static final String VIDEO_DOMAIN = "video.domain";

    private static final String WIN_SIGN = "Windows";

    private static final String LINUX_SIGN = "Linux";

    private static final String MAC_SIGN = "Mac OS X";

    /**
     * 文件上传接口
     * @param file
     * @param fileType 0 图片 1 视频
     * @return
     */
    @Override
    public String fileUpload(MultipartFile file,int fileType) {
        if (file.isEmpty()) {
            throw new ServiceException("文件为空！");
        }
        String fileUrl = "";
        String fileName ="";
        try {
            String savePath="";
            String osName = System.getProperty("os.name");
            if (osName.startsWith(WIN_SIGN)) {
                savePath= PropertiesUtil.getString(WIN_IMG_UPLOAD_URL);
            }else if(osName.startsWith(LINUX_SIGN)){
                savePath= PropertiesUtil.getString(LINUX_IMG_UPLOAD_URL);
            }else if(osName.startsWith(MAC_SIGN)) {
                savePath= PropertiesUtil.getString(MAC_IMG_UPLOAD_URL);
            }
            assert savePath != null;
            File uploadDir = new File(savePath);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }
            //获取原文件名
            String originalFilename = file.getOriginalFilename();
            //获取文件后缀
            String suffixName = originalFilename.substring(originalFilename.lastIndexOf("."));
            fileName = UUID.randomUUID() + suffixName;

            if (osName.startsWith(WIN_SIGN)) {
                fileUrl= savePath+"/"+fileName;
            }else if(osName.startsWith(LINUX_SIGN)||osName.startsWith(MAC_SIGN)){
                fileUrl= savePath+"/"+fileName;
            }
            File localFile = new File(fileUrl);
            file.transferTo(localFile);
        } catch (IOException e) {
            e.printStackTrace();
            throw new ServiceException("上传失败！");
        }
        if (fileType==0) {
            return PropertiesUtil.getString(IMG_DOMAIN)+fileName;
        }else{
            return PropertiesUtil.getString(VIDEO_DOMAIN)+fileName;
        }
    }
}
