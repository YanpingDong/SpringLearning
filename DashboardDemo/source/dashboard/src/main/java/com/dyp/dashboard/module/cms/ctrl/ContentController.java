package com.dyp.dashboard.module.cms.ctrl;

import com.dyp.dashboard.module.cms.entity.CmsArticleData;
import com.dyp.dashboard.module.cms.repository.CmsArticleDataMapper;
import com.dyp.dashboard.util.IDGenerator;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Controller
@RequestMapping("/cms")
public class ContentController {
    @Autowired
    CmsArticleDataMapper cmsArticleDataDao;

    @ApiOperation(value = "获取指定工程基本信息", httpMethod = "GET")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success, type is String \n, example : vx.x.x-rcxx ", response = CmsArticleData.class),
            @ApiResponse(code = 400, message = "Parameter error", response = Error.class),
            @ApiResponse(code = 500, message = "Service error", response = Error.class)})
    @GetMapping("/{articalId}")
    @ResponseBody
    public CmsArticleData getProject(
            @ApiParam(value = "articalId", required = true, example = "1")
            @RequestParam(value = "articalId") String articalId) {
        CmsArticleData cmsArticleData = cmsArticleDataDao.selectById(articalId);
        return null == cmsArticleData ? new CmsArticleData() : cmsArticleData;
    }

    @ApiOperation(value = "创建game信息，并返还Id", httpMethod = "POST")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Success, type is String \n, example : vx.x.x-rcxx "),
            @ApiResponse(code = 400, message = "input parameter error", response = Error.class),
            @ApiResponse(code = 500, message = "Service function error", response = Error.class)})
    @PostMapping()
    @ResponseBody
    public ModelMap creatGame(HttpServletResponse response,
                              @RequestBody CmsArticleData data) {
        String id = IDGenerator.generatorID();
        data.setId(id);
        cmsArticleDataDao.insert(data);
        ModelMap modelMap = new ModelMap();
        modelMap.addAttribute("gameId", id);
        return modelMap;
    }

    @RequestMapping(value = "/editor", method = RequestMethod.GET)
    public String toEditor(Map<String, Object> map, HttpServletRequest request) {
        return "/cms/editor";
    }

    @RequestMapping({"/detail"})
    public String detail(HttpServletRequest request, Model model) {
        CmsArticleData cmsArticleData = cmsArticleDataDao.selectById("758447645058990080");
        model.addAttribute("info", cmsArticleData.getContent());
        return "/cms/detail";
    }

    @RequestMapping({"/detail/{id}"})
    @ResponseBody
    public String detaila(HttpServletRequest request,@PathVariable("id")String id, Model model) {


        CmsArticleData cmsArticleData = cmsArticleDataDao.selectById(id);
        model.addAttribute("info", cmsArticleData.getContent());
//        return "/cms/detail";
        return cmsArticleData.getContent();
    }
}
