import request from '@/utils/request'
export default {

    //添加小节
    addVideo(video) {
        return request({
            url: `/eduservice/video/addVideo`,
            method: 'post',
            data: video
          })
    },
    
    //删除小节
    deleteVideo(id) {
        return request({
            url: `/eduservice/video/deleteVideo/${id}`,
            method: 'delete'
          })
    },

    //根据id查询信息
    getVideoInfo(id) {
        return request({
            url: `/eduservice/video/getVideoInfo/${id}`,
            method: 'get'
          })
    },

    //修改小节
    updateVideo(video) {
        return request({
            url: `/eduservice/video/updateVideo`,
            method: 'put',
            data: video
          })
    },

    //删除上传到阿里云中的视频
    deleteVideoAliyun(id) {
        return request({
            url: `/eduvod/video/removeVideoAliyun/${id}`,
            method: 'delete'
          })
    },
}