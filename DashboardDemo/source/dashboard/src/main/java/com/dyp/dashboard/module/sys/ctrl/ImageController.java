package com.dyp.dashboard.module.sys.ctrl;

import com.dyp.dashboard.module.sys.entity.ImageData;
import com.dyp.dashboard.module.sys.repository.ImageDataMapper;
import com.dyp.dashboard.util.IDGenerator;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;

@RestController
@RequestMapping("/image")
public class ImageController {

    @Autowired
    ImageDataMapper imageDataDao;

    @ApiOperation(value = "upload image and return id", httpMethod = "GET")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success, type is String \n, example : vx.x.x-rcxx ", response = void.class),
            @ApiResponse(code = 404, message = "Not Found", response = Error.class) })
    @PostMapping("")
    @ResponseBody
    public ModelMap uploadImage(MultipartFile image,
                                HttpServletRequest request)  {
        String imageId = IDGenerator.generatorID();
        try {
            InputStream is = image.getInputStream();
            byte[] studentPhotoData = new byte[(int) image.getSize()];
            int length = is.read(studentPhotoData);
            String name = image.getOriginalFilename();
            String type = image.getContentType();
            ImageData imageData = new ImageData();
            imageData.setCreateDate(new Date(System.currentTimeMillis()));
            imageData.setId(imageId);
            imageData.setName(name);
            imageData.setData(studentPhotoData);
            imageData.setType(type);

            imageDataDao.insert(imageData);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        ModelMap modelMap = new ModelMap();
        modelMap.addAttribute("id", imageId);
        return modelMap;
    }

    @ApiOperation(value = "downlaod image by id", httpMethod = "GET")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success, type is String \n, example : vx.x.x-rcxx ", response = void.class),
            @ApiResponse(code = 404, message = "Not Found", response = Error.class) })
    @GetMapping(value = "/{imageId}")
    public void downPhotoByStudentId(
            @ApiParam(value = "The user/account to update subscriptions for", required = true, defaultValue = "dyptest")
            @PathVariable(value = "imageId") String imageId,
            final HttpServletResponse response) throws IOException {
        ImageData entity = this.imageDataDao.selectById(imageId);
        byte[] data = entity.getData();
        String name = entity.getName();
        response.reset();
//        response.setHeader("Content-Disposition", "attachment; filename=\"" + name + "\"");
//        response.setContentType("application/octet-stream;charset=UTF-8");
        response.addHeader("Content-Length", "" + data.length);
        response.addHeader("Content-Type", "" +entity.getType());

        response.setContentType(entity.getType());
        OutputStream outputStream = new BufferedOutputStream(response.getOutputStream());
        outputStream.write(data);
        outputStream.flush();
        outputStream.close();
    }
}
