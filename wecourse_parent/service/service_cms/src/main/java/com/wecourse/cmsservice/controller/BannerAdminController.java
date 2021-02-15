package com.wecourse.cmsservice.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wecourse.cmsservice.pojo.CrmBanner;
import com.wecourse.cmsservice.service.CrmBannerService;
import com.wecourse.commonutils.Result;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author tcp
 * @since 2021-02-06
 */
@Api("前端轮播图管理") //swagger Api文档的中文显示
@CrossOrigin
@RestController
@RequestMapping("/cmsservice/banneradmin")
public class BannerAdminController {

    @Autowired
    private CrmBannerService bannerService;

    //分页查询banner
    @GetMapping("/pageListBanner/{page}/{limit}")
    public Result pageListBanner(@PathVariable long page,@PathVariable long limit){
        Page<CrmBanner> pageBanner=new Page<>(page,limit);
        bannerService.page(pageBanner,null);
        return Result.ok().data("total",pageBanner.getTotal()).data("rows",pageBanner.getRecords());
    }


    //查询一个
    @GetMapping("/getBannerInfo/{id}")
    public Result getBannerInfo(@PathVariable String id) {
        CrmBanner banner = bannerService.getById(id);
        return Result.ok().data("banner", banner);
    }

    //添加banner
    @PostMapping("/addBanner")
    public Result addBanner(@RequestBody CrmBanner crmBanner){
        bannerService.saveBanner(crmBanner);
        return Result.ok();
    }

    //修改banner
    @PutMapping("/updateBanner")
    public Result updateBanner(@RequestBody CrmBanner banner) {
        bannerService.updateBannerById(banner);
        return Result.ok();
    }

    //删除banner
    @DeleteMapping("/deleteBanner/{id}")
    public Result deleteBanner(@PathVariable String id) {
        bannerService.removeBannerById(id);
        return Result.ok();
    }
}

