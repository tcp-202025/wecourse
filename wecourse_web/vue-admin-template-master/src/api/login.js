import request from '@/utils/request'

export function login(username, password) {
  return request({
    url: '/eduservice/admin/login',
    method: 'post',
    data: {
      username,
      password
    }
  })
}

export function getInfo(token) {
  return request({
    url: '/eduservice/admin/info',
    method: 'get',
    params: { token }
  })
}

export function logout() {
  return request({
    url: '/eduservice/admin/logout',
    method: 'post'
  })
}
