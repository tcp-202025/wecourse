package com.wecourse.cmsservice.controller;


import com.wecourse.cmsservice.pojo.CrmBanner;
import com.wecourse.cmsservice.service.CrmBannerService;
import com.wecourse.commonutils.Result;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author tcp
 * @since 2021-02-06
 */
@Api("前端轮播图展示") //swagger Api文档的中文显示
@CrossOrigin
@RestController
@RequestMapping("/cmsservice/bannerfront")
public class BannerFrontController {

    @Autowired
    private CrmBannerService bannerService;

    //查询轮播图
    @GetMapping("/findAll")
    public Result findAll(){
        List<CrmBanner> crmBannerList = bannerService.selectAllBanner();
        return Result.ok().data("crmBannerList",crmBannerList);
    }

}

