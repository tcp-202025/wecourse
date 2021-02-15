import request from '@/utils/request'

export default {

  getPageList(courseId, page, limit) {
    return request({
      url: `/eduservice/comment/pageListComment/${courseId}/${page}/${limit}`,
      method: 'get',
    })
  },

  addComment(comment) {
    return request({
      url: `/eduservice/comment/addComment`,
      method: 'post',
      data: comment
    })
  }
}