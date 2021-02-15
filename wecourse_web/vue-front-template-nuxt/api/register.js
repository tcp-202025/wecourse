import request from '@/utils/request'

export default {
  //根据手机号码发送短信
  sendMsg(mobile) {
    return request({
      url: `/smsservice/sms/send/${mobile}`,
      method: 'get'
    })
  },

  //用户注册
  submitRegister(formItem) {
    return request({
      url: `/ucenterservice/member/register`,
      method: 'post',
      data: formItem
    })
  }
}