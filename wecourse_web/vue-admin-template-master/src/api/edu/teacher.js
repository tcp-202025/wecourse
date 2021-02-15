import request from '@/utils/request'

const api_name = '/eduservice/teacher'

export default {

    //查询所有讲师
    getListTeacher() {
        return request({
            url: '/eduservice/teacher/findAllTeacher',
            method: 'get',
          }) 
    },

    //通过id查询
    getTeacherInfo(id) {
        return request({
            url: `${api_name}/findOneTeacher/${id}`,
            method: 'get',
        })
    },

    //分页条件查询讲师列表
    getTeacherListPage(page, limit, searchMap) {
        return request({
            url: `${api_name}/pageTeacherCondition/${page}/${limit}`,
            method: 'post',
            //后端使用@RequestBody来获取数据，所以data就表示把对象转换为json进行传递到接口中
            data: searchMap 
        })
    },

    //删除讲师
    deleteTeacherById(id) {
        return request({
            url: `${api_name}/deleteTeacher/${id}`,
            method: 'delete', 
        })
    },

    //添加讲师
    addTeacher(teacher) {
        return request({
            url: `${api_name}/addTeacher`,
            method: 'post', 
            data:teacher
        })
    },

    //修改讲师
    updateTeacher(teacher) {
        return request({
            url: `${api_name}/updateTeacher`,
            method: 'put', 
            data:teacher
        })
    }
}