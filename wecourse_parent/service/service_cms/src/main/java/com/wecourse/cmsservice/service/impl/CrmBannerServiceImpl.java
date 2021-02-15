package com.wecourse.cmsservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wecourse.cmsservice.pojo.CrmBanner;
import com.wecourse.cmsservice.mapper.CrmBannerMapper;
import com.wecourse.cmsservice.service.CrmBannerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author tcp
 * @since 2021-02-06
 */
@Service
public class CrmBannerServiceImpl extends ServiceImpl<CrmBannerMapper, CrmBanner> implements CrmBannerService {

    //查询轮播图
    @Cacheable(value = "banner", key = "'selectIndexList'")  //添加缓存
    @Override
    public List<CrmBanner> selectAllBanner() {

        //查询数据库：返回前3条轮播图记录
        QueryWrapper<CrmBanner> wrapper=new QueryWrapper<>();
        wrapper.orderByDesc("gmt_modified");//按修改时间排序
        wrapper.last("limit 3");//拼接sql语句

        return baseMapper.selectList(wrapper);
    }

    @CacheEvict(value = "banner", allEntries=true) //增、删、改的时候都重新清空缓存
    @Override
    public void saveBanner(CrmBanner banner) {
        baseMapper.insert(banner);
    }

    @CacheEvict(value = "banner", allEntries=true)
    @Override
    public void updateBannerById(CrmBanner banner) {
        baseMapper.updateById(banner);
    }

    @CacheEvict(value = "banner", allEntries=true)
    @Override
    public void removeBannerById(String id) {
        baseMapper.deleteById(id);
    }
}
