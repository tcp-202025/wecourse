import request from '@/utils/request'

export default {
    //分页条件查询所有课程
    getCourseListPage(page, limit, searchMap) {
        return request({
            url: `/eduservice/course/pageCourseCondition/${page}/${limit}`,
            method: 'post',
            //后端使用@RequestBody来获取数据，所以data就表示把对象转换为json进行传递到接口中
            data: searchMap 
        })
    },

    //添加课程信息
    addCourseInfo(courseInfo) {
        return request({
            url: '/eduservice/course/addCourse',
            method: 'post',
            data: courseInfo
          })
    },

    //根据课程id查询课程基本信息
    getCourseInfo(courseId) {
        return request({
            url: `/eduservice/course/getCourseInfo/${courseId}`,
            method: 'get'
          })
    },

    //修改课程信息
    updateCourseInfo(courseInfo) {
        return request({
            url: `/eduservice/course/updateCourseInfo`,
            method: 'put',
            data: courseInfo
          })
    },

    //课程确认信息的显示
    getPublishCourseInfo(courseId) {
        return request({
            url: `/eduservice/course/getPublishCourseInfo/${courseId}`,
            method: 'get'
          })
    },

    //课程发布
    publishCourse(courseId) {
        return request({
            url: `/eduservice/course/publishCourse/${courseId}`,
            method: 'post'
          })
    },

    //删除课程
    deleteCourseById(id) {
        return request({
            url: `/eduservice/course/deleteCourse/${id}`,
            method: 'delete', 
        })
    },
}
