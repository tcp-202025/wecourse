module.exports = {
  /*
  ** Headers of the page
  */
  head: {
    title: '小邮微课 - Java视频|HTML5视频|前端视频|Python视频|大数据视频-学与思的在线视频课程，为你专属推荐！',
    meta: [
      { charset: 'utf-8' },
      { name: 'viewport', content: 'width=device-width, initial-scale=1' },
      { hid: 'description', name: 'description', content: '小邮微课平台是重庆邮电大学csa工作室成员合作开发的在线视频学习平台，集学习、授课、讨论等多个功能于一体，为高校学生提供更多的学习资源，使他们通过自主学习能够获取更多的知识'}
    ],
    link: [
      { rel: 'icon', type: 'image/x-icon', href: '/favicon.ico' }
    ]
  },
  /*
  ** Customize the progress bar color
  */
  loading: { color: '#3B8070' },
  /*
  ** Build configuration
  */
  build: {
    /*
    ** Run ESLint on save
    */
    extend (config, { isDev, isClient }) {
      if (isDev && isClient) {
        config.module.rules.push({
          enforce: 'pre',
          test: /\.(js|vue)$/,
          loader: 'eslint-loader',
          exclude: /(node_modules)/
        })
      }
    }
  },

  plugins: [
      { src: '~/plugins/nuxt-swiper-plugin.js', ssr: false }
    ],
    css: [
      'swiper/dist/css/swiper.css'
    ]
}

