package com.tra.controller;

import com.tra.entity.PersonRole;
import com.tra.entity.PhotoEntity;
import com.tra.repository.IPersonRole;
import com.tra.repository.IPhoto;
import com.tra.web.model.StudentForm;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.sql.Date;
import java.util.List;

//https://blog.csdn.net/qq_34874784/article/details/83380144 分段上传
//https://blog.csdn.net/A1032453509/article/details/78045957 断点续传
@Api(value = "api to test connectivity with related services", produces = "application/json")
@Controller
@RequestMapping("/photo")
public class UploadController {

    private static Logger logger = LoggerFactory.getLogger(UploadController.class);
    @Autowired
    private IPhoto iPhoto;



    @ApiOperation(value = "get version info", notes = "get version info, type is String \n, example : vx.x.x-rcxx", httpMethod = "POST")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success, type is String \n, example : vx.x.x-rcxx ", response = String.class),
            @ApiResponse(code = 404, message = "Not Found", response = Error.class) })
    @PostMapping("/upload/actionf")
    @ResponseBody
    public String actionfile(MultipartFile studentForm,
                         HttpServletRequest request)  {
        System.out.println("hahaha");

        try {
            InputStream is = studentForm.getInputStream();
            byte[] studentPhotoData = new byte[(int) studentForm.getSize()];
            int length = is.read(studentPhotoData);
            String fileName = studentForm.getOriginalFilename();
            String destDirName = "/home/learlee/Downloads/TmpJar";
            File dir = new File(destDirName);
            if (!destDirName.endsWith(File.separator)) {
                destDirName = destDirName + File.separator;
            }
            File file = new File(destDirName+ fileName + ".txt");
            file.createNewFile();
            FileOutputStream fos = new FileOutputStream(destDirName+ fileName + ".txt");
            fos.write(studentPhotoData,0,length);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        return "success";
    }

    @ApiOperation(value = "get version info", notes = "get version info, type is String \n, example : vx.x.x-rcxx", httpMethod = "POST")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success, type is String \n, example : vx.x.x-rcxx ", response = String.class),
            @ApiResponse(code = 404, message = "Not Found", response = Error.class) })
    @PostMapping("/upload/actiond")
    @ResponseBody
    public String actionfileDouble(MultipartFile studentPhoto,MultipartFile teatcherPhoto,
                             HttpServletRequest request) throws IOException {
        System.out.println("hahaha");

        InputStream is = studentPhoto.getInputStream();
        byte[] studentPhotoData = new byte[(int) studentPhoto.getSize()];
        int length = is.read(studentPhotoData);
        String fileName = studentPhoto.getOriginalFilename();
        String destDirName = "/home/learlee/Downloads/TmpJar";
        File dir = new File(destDirName);
        if (!destDirName.endsWith(File.separator)) {
            destDirName = destDirName + File.separator;
        }
        File file = new File(destDirName+ fileName + ".txt");
        file.createNewFile();
        FileOutputStream fos = new FileOutputStream(destDirName+ fileName + ".txt");
        fos.write(studentPhotoData,0,length);
        return "success";
    }
    //下载
    @ApiOperation(value = "get downPhotoById info", notes = "get version info, type is String \n, example : vx.x.x-rcxx", httpMethod = "GET")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success, type is String \n, example : vx.x.x-rcxx ", response = void.class),
            @ApiResponse(code = 404, message = "Not Found", response = Error.class) })
    @RequestMapping(value = "/downPhotoByIdf")
    public void downPhotoByStudentIdf(
            @ApiParam(value = "The user/account to update subscriptions for", required = true, defaultValue = "dyptest")
            @RequestParam(value = "id") String id,
            final HttpServletResponse response) throws IOException {
        String destDirName = "/home/learlee/Downloads/test.jpg";
        File file = new File(destDirName);
        FileInputStream fileInputStream=new FileInputStream(file);
        ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
        byte[] buff = new byte[100];
        int rc = 0;
        while ((rc = fileInputStream.read(buff, 0, 100)) > 0) {
                         swapStream.write(buff, 0, rc);
                     }

        byte[] data = swapStream.toByteArray();
        String fileName = "test.jpg" ;
        fileName = URLEncoder.encode(fileName, "UTF-8");
        response.reset();
//        response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
        response.addHeader("Content-Length", "" + data.length);  //""+data.length help to convert as String
        response.setContentType("image/jpeg;charset=UTF-8");
        OutputStream outputStream = new BufferedOutputStream(response.getOutputStream());
        outputStream.write(data);
        outputStream.flush();
        outputStream.close();
    }

    @ApiOperation(value = "get version info", notes = "get version info, type is String \n, example : vx.x.x-rcxx", httpMethod = "POST")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Success, type is String \n, example : vx.x.x-rcxx ", response = String.class),
        @ApiResponse(code = 404, message = "Not Found", response = Error.class) })
    @PostMapping("/upload/action")
    @ResponseBody
    public String action(StudentForm studentForm, MultipartFile studentPhoto,
                         HttpServletRequest request) throws IOException {
        System.out.println("hahaha");

        InputStream is = studentPhoto.getInputStream();
        byte[] studentPhotoData = new byte[(int) studentPhoto.getSize()];
        is.read(studentPhotoData);
        String fileName = studentPhoto.getOriginalFilename();
        PhotoEntity photoEntity = new PhotoEntity();
        photoEntity.setPhotoData(studentPhotoData);
        photoEntity.setFileName(studentForm.getStudentName());
        photoEntity.setPhotoId("dyptest" );
//        this.iPhoto.createPhoto(photoEntity);
        return "success";
    }


    @GetMapping("/upload")
    public String index(){
        return "upload"; //当浏览器输入/upload，会返回 /templates/upload.html页面
    }


    //下载
    @ApiOperation(value = "get downPhotoById info", notes = "get version info, type is String \n, example : vx.x.x-rcxx", httpMethod = "GET")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success, type is String \n, example : vx.x.x-rcxx ", response = void.class),
            @ApiResponse(code = 404, message = "Not Found", response = Error.class) })
    @RequestMapping(value = "/downPhotoById")
    public void downPhotoByStudentId(
            @ApiParam(value = "The user/account to update subscriptions for", required = true, defaultValue = "dyptest")
            @RequestParam(value = "id") String id,
            final HttpServletResponse response) throws IOException {
        PhotoEntity entity = this.iPhoto.getPhotoEntityByPhotoId(id);
        byte[] data = entity.getPhotoData();
        String fileName = entity.getFileName()== null ? "照片.png" : entity.getFileName();
        fileName = URLEncoder.encode(fileName, "UTF-8");
        response.reset();
        response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
        response.addHeader("Content-Length", "" + data.length);
        response.setContentType("application/octet-stream;charset=UTF-8");
        OutputStream outputStream = new BufferedOutputStream(response.getOutputStream());
        outputStream.write(data);
        outputStream.flush();
        outputStream.close();
    }


    //直接在页面显示
    @ApiOperation(value = "get getPhotoById info", notes = "get version info, type is String \n, example : vx.x.x-rcxx", httpMethod = "GET")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success, type is String \n, example : vx.x.x-rcxx ", response = void.class),
            @ApiResponse(code = 404, message = "Not Found", response = Error.class) })
    @RequestMapping(value = "getPhotoById")
    public void getPhotoById (
            @ApiParam(value = "The user/account to update subscriptions for", required = true, defaultValue = "dyptest")
            @RequestParam(value = "id")
                    String id,
            final HttpServletResponse response) throws IOException {
        PhotoEntity entity = this.iPhoto.getPhotoEntityByPhotoId(id);
        byte[] data = entity.getPhotoData();
        response.setContentType("image/png");
        response.setCharacterEncoding("UTF-8");
        OutputStream outputSream = response.getOutputStream();
        InputStream in = new ByteArrayInputStream(data);
        int len = 0;
        byte[] buf = new byte[1024];
        while ((len = in.read(buf, 0, 1024)) != -1) {
            outputSream.write(buf, 0, len);
        }
        outputSream.close();
    }

    /**
     * 支持断点续传
     *
     * 请求资源的部分内容（不包括响应头的大小），单位是byte，即字节，从0开始.
     * 如果服务器能够正常响应的话，服务器会返回 206 Partial Content 的状态码及说明.
     * 如果不能处理这种Range的话，就会返回整个资源以及响应状态码为 200 OK .（这个要注意，要分段下载时，要先判断这个）
     * 参考：https://www.cnblogs.com/1995hxt/p/5692050.html 了解断点下载原理
     * 参考:https://gitee.com/chemphern/fileupload-demo/tree/master/src/main/java/com/demo/fileupload/controller 看代码编写
     * Request
     * GET /bg-upper.png HTTP/1.1
     * User-Agent: curl/7.35.0
     * Host: 127.0.0.1:8180
     * Range:bytes=0-10
     *
     * Response
     * HTTP/1.1 206 Partial Content
     * Server: Apache-Coyote/1.1
     * Accept-Ranges: bytes
     * Content-Range: bytes 0-10/3103
     * Content-Type: image/png
     * Content-Length: 11
     * Date: Tue, 29 Dec 2015 09:18:36 GMT
     * @param name
     * @param request
     * @param response
     * @throws FileNotFoundException
     */
    @RequestMapping("/download/{name}")
    public void getDownload(@PathVariable String name, HttpServletRequest request, HttpServletResponse response) throws FileNotFoundException {
        // Get your file stream from wherever.
        logger.info("name="+name);
        String fullPath = "/home/learlee/Downloads/test.jpg";
        logger.info("下载路径:"+fullPath);
        File downloadFile = new File(fullPath);

        ServletContext context = request.getServletContext();
        // get MIME type of the file
        String mimeType = context.getMimeType(fullPath);
        if (mimeType == null) {
            // set to binary type if MIME mapping not found
            mimeType = "application/octet-stream";
        }

        // set content attributes for the response
        response.setContentType(mimeType);
        // response.setContentLength((int) downloadFile.length());
        // 告诉客户端允许断点续传多线程连接下载,响应的格式是:Accept-Ranges: bytes
        response.setHeader("Accept-Ranges", "bytes");
        // set headers for the response
        String headerKey = "Content-Disposition";
        String headerValue = String.format("attachment; filename=\"%s\"", downloadFile.getName());
        response.setHeader(headerKey, headerValue);
        // 解析断点续传相关信息

        long downloadSize = downloadFile.length();
        long fromPos = 0, toPos = 0;
        if (request.getHeader("Range") == null) {
            response.setHeader("Content-Length", downloadSize + "");
        } else {
            // 若客户端传来Range，说明之前下载了一部分，设置206状态(SC_PARTIAL_CONTENT)
            response.setStatus(HttpServletResponse.SC_PARTIAL_CONTENT);

            String range = request.getHeader("Range");
            String bytes = range.replaceAll("bytes=", "");
            String[] ary = bytes.split("-");
            fromPos = Long.parseLong(ary[0]);
            if (ary.length == 2) {
                toPos = Long.parseLong(ary[1]);
            }
            int size;
            if (toPos > fromPos) {
                size = (int) (toPos - fromPos);
            } else {
                size = (int) (downloadSize - fromPos);
            }
            response.setHeader("Content-Length", size + "");
            downloadSize = size;
        }
        // Copy the stream to the response's output stream.
        RandomAccessFile in = null;
        OutputStream out = null;
        try {
            in = new RandomAccessFile(downloadFile, "rw");
            // 设置下载起始位置
            if (fromPos > 0) {
                in.seek(fromPos);
            }
            // 缓冲区大小
            int bufLen = (int) (downloadSize < 2048 ? downloadSize : 2048);
            byte[] buffer = new byte[bufLen];
            int num;
            int count = 0; // 当前写到客户端的大小
            out = response.getOutputStream();
            while ((num = in.read(buffer)) != -1) {
                out.write(buffer, 0, num);
                count += num;
                //处理最后一段，计算不满缓冲区的大小
                if (downloadSize - count < bufLen) {
                    bufLen = (int) (downloadSize-count);
                    if(bufLen==0){
                        break;
                    }
                    buffer = new byte[bufLen];
                }
            }
            response.flushBuffer();
        } catch (IOException e) {
            logger.info("数据被暂停或中断。");
//            e.printStackTrace();
        } finally {
            if (null != out) {
                try {
                    out.close();
                } catch (IOException e) {
                    logger.info("数据被暂停或中断。");
//                    e.printStackTrace();
                }
            }
            if (null != in) {
                try {
                    in.close();
                } catch (IOException e) {
                    logger.info("数据被暂停或中断。");
//                    e.printStackTrace();
                }
            }
        }
    }
}
