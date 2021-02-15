import request from '@/utils/request'

export default {
  
  //查询前三条banner数据
  getBannerList() {
    return request({
      url: '/cmsservice/bannerfront/findAll',
      method: 'get'
    })
  }
}