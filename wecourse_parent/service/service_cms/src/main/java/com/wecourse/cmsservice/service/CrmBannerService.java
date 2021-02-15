package com.wecourse.cmsservice.service;

import com.wecourse.cmsservice.pojo.CrmBanner;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.cache.annotation.CacheEvict;

import java.util.List;

/**
 * @author tcp
 * @since 2021-02-06
 */
public interface CrmBannerService extends IService<CrmBanner> {

    //查询轮播图
    public List<CrmBanner> selectAllBanner();

    //新增轮播图
    public void saveBanner(CrmBanner banner);

    //修改轮播图
    public void updateBannerById(CrmBanner banner);

    //删除轮播图
    public void removeBannerById(String id);
}
