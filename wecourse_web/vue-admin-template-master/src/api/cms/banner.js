import request from '@/utils/request'

export default {
    //分页查询
    getBannerListPage(page, limit) {
        return request({
            url: `/cmsservice/banneradmin/pageListBanner/${page}/${limit}`,
            method: 'get'
        })
    },

    //添加
    addBanner(CrmBanner) {
        return request({
            url: '/cmsservice/banneradmin/addBanner',
            method: 'post',
            data: CrmBanner     
          })
    },

    //根据id查询息
    getBannerInfo(id) {
        return request({
            url: `/cmsservice/banneradmin/getBannerInfo/${id}`,
            method: 'get'
          })
    },

    //修改
    updateBanner(CrmBanner) {
        return request({
            url: `/cmsservice/banneradmin/updateBanner`,
            method: 'put',
            data: CrmBanner
          })
    },

    //删除
    deleteBanner(id) {
        return request({
            url: `/cmsservice/banneradmin/deleteBanner/${id}`,
            method: 'delete', 
        })
    },
}
