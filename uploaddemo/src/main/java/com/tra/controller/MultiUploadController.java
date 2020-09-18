package com.tra.controller;


import java.io.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tra.model.JsonResult;
import com.tra.utility.FileMd5Util;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;


@Controller
@Api(value = "api to test connectivity with related services", produces = "application/json")
public class MultiUploadController{

    @Value("${uploadFolder}")
    private String filePath;

    @PostMapping("/multipart/check")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success, type is String \n, example : vx.x.x-rcxx "),
            @ApiResponse(code = 404, message = "Not Found", response = Error.class) })
    public void check(HttpServletRequest request, HttpServletResponse response, String guid, Integer chunk, String md5, MultipartFile file, Integer chunks){
        System.out.println("check finished");
    }
    /**
     * 上传文件
     * @param request
     * @param response
     * @param guid
     * @param chunk
     * @param file
     * @param chunks
     */
    @PostMapping("/multipart/upload")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success, type is String \n, example : vx.x.x-rcxx "),
            @ApiResponse(code = 404, message = "Not Found", response = Error.class) })
    public void bigFile(HttpServletRequest request, HttpServletResponse response, String guid, Integer chunk, String md5,
                        String md5value, MultipartFile file, Integer chunks){
        try {
            boolean isMultipart = ServletFileUpload.isMultipartContent(request);
            if (isMultipart) {
                // 临时目录用来存放所有分片文件
                String tempFileDir = filePath + guid;
                File parentFileDir = new File(tempFileDir);
                if (!parentFileDir.exists()) {
                    parentFileDir.mkdirs();
                }
                File tempPartFile = new File(parentFileDir, md5value + "_" + chunk + ".part");
                // 分片处理时，前台会多次调用上传接口，每次都会上传文件的一部分到后台
                if (null != md5value && "" != md5value)
                {
                    if(tempPartFile.exists() && md5value.equalsIgnoreCase(FileMd5Util.getFileMD5(tempPartFile)))
                    {
                        //do nothing
                    }
                    else if(tempPartFile.exists())
                    {
                        tempPartFile.delete();
                        FileUtils.copyInputStreamToFile(file.getInputStream(), tempPartFile);
                    }
                    else
                    {
                        FileUtils.copyInputStreamToFile(file.getInputStream(), tempPartFile);
                    }
                }
                else
                {
                    if(tempPartFile.exists())
                    {
                        tempPartFile.delete();
                        FileUtils.copyInputStreamToFile(file.getInputStream(), tempPartFile);
                    }
                    else
                    {
                        FileUtils.copyInputStreamToFile(file.getInputStream(), tempPartFile);
                    }
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 合并文件
     * @param guid
     * @param fileName
     * @throws Exception
     */
    @RequestMapping( "/multipart/merge")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success, type is String \n, example : vx.x.x-rcxx "),
            @ApiResponse(code = 404, message = "Not Found", response = Error.class) })
    @ResponseBody
    public JsonResult mergeFile(String guid, String fileName){
        // 得到 destTempFile 就是最终的文件
        try {
            File parentFileDir = new File(filePath + guid);
            if(parentFileDir.isDirectory()){
                File destTempFile = new File(filePath + "/merge", fileName);
                if(!destTempFile.exists()){
                    //先得到文件的上级目录，并创建上级目录，在创建文件,
                    destTempFile.getParentFile().mkdir();
                    try {
                        //创建文件
                        destTempFile.createNewFile(); //上级目录没有创建，这里会报错
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println(parentFileDir.listFiles().length);
                for (int i = 0; i < parentFileDir.listFiles().length; i++) {
                    File partFile = new File(parentFileDir, guid + "_" + i + ".part");
                    FileOutputStream destTempfos = new FileOutputStream(destTempFile, true);
                    //遍历"所有分片文件"到"最终文件"中
                    FileUtils.copyFile(partFile, destTempfos);
                    destTempfos.close();
                }
                // 删除临时目录中的分片文件
                FileUtils.deleteDirectory(parentFileDir);
                return JsonResult.success();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return JsonResult.fail();
        }
        return null;
    }

    @GetMapping("/multipart/upload")
    public String index(){
        return "multiUpload3"; //当浏览器输入/upload，会返回 /templates/upload.html页面
    }

}
