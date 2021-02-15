import request from '@/utils/request'

export default {
    //根据课程id获取章节列表
    getAllChapterByCourseId(courseId) {
        return request({
            url: `/eduservice/chapter/getChapter/${courseId}`,
            method: 'get'
          })
    },

    //添加章节
    addChapter(chapter) {
        return request({
            url: `/eduservice/chapter/addChapter`,
            method: 'post',
            data: chapter
          })
    },

    //根据章节id查询章节信息
    getChapterInfo(chapterId) {
        return request({
            url: `/eduservice/chapter/getChapterInfo/${chapterId}`,
            method: 'get'
          })
    },

    //修改章节
    updateChapter(chapter) {
        return request({
            url: `/eduservice/chapter/updateChapter`,
            method: 'put',
            data: chapter
          })
    },

    //根据课程id获取章节列表
    deleteChapter(chapterId) {
        return request({
            url: `/eduservice/chapter/deleteChapter/${chapterId}`,
            method: 'delete'
          })
    },

}