package com.dhais.tqb.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * All rights Reserved, Designed By Fan Jun
 *
 * @author Fan Jun
 * @version 1.0
 * @since 2022/8/9 17:23
 */
public interface IFileUploadService {
    String fileUpload(MultipartFile multipartFile,int fileType);
}
