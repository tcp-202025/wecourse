<template>

  <div class="app-container">

    <h2 style="text-align: center;">发布新课程</h2>

    <el-steps :active="1" process-status="wait" align-center style="margin-bottom: 40px;">
      <el-step title="填写课程基本信息"/>
      <el-step title="创建课程大纲"/>
      <el-step title="最终发布"/>
    </el-steps>

    <el-form label-width="120px">

        <el-form-item label="课程标题">
            <el-input v-model="courseInfo.title" placeholder=" 示例：Python机器学习应用：从基础到进阶"/>
        </el-form-item>

        <!-- 所属分类-->
        <el-form-item label="课程分类">
            <el-select
                v-model="courseInfo.subjectParentId"
                placeholder="一级分类" @change="subjectLevelOneChanged">

                <el-option
                    v-for="subject in subjectOneList"
                    :key="subject.id"
                    :label="subject.title"
                    :value="subject.id"/>

            </el-select>

            <!-- 二级分类 -->
            <el-select v-model="courseInfo.subjectId" placeholder="二级分类">
                <el-option
                    v-for="subject in subjectTwoList"
                    :key="subject.id"
                    :label="subject.title"
                    :value="subject.id"/>
            </el-select>
        </el-form-item>

        <!-- 课程讲师 -->
        <el-form-item label="课程讲师">
        <el-select
            v-model="courseInfo.teacherId"
            placeholder="请选择">

            <el-option
                v-for="teacher in teacherList"
                :key="teacher.id"
                :label="teacher.name"
                :value="teacher.id"/>

        </el-select>
        </el-form-item>

        <el-form-item label="总课时">
            <el-input-number :min="0" v-model="courseInfo.lessonNum" controls-position="right" placeholder="请填写课程的总课时数"/>
        </el-form-item>

        <!-- 课程简介-->
        <el-form-item label="课程简介">
            <tinymce :height="300" v-model="courseInfo.description" ref="content"/>
        </el-form-item>

        <!-- 课程封面-->
        <el-form-item label="课程封面">

            <el-upload
                :show-file-list="false"
                :on-success="handleAvatarSuccess"
                :before-upload="beforeAvatarUpload"
                :action="BASE_API+'/eduoss/fileoss/uploadFile'"
                class="avatar-uploader">
                <img :src="courseInfo.cover">
            </el-upload>

        </el-form-item>

        <el-form-item label="课程价格">
            <el-input-number :min="0" v-model="courseInfo.price" controls-position="right" placeholder="免费课程请设置为0元"/> 元
        </el-form-item>

        <el-form-item>
            <el-button :disabled="saveBtnDisabled" type="primary" @click="saveOrUpdate">保存并下一步</el-button>
        </el-form-item>
        </el-form>
  </div>
</template>
<script>
import course from '@/api/edu/course'
import teacher from '@/api/edu/teacher'
import subject from '@/api/edu/subject'
import Tinymce from '@/components/Tinymce'

export default {
    components: { Tinymce }, //对引入的富文本组件进行声明

    data() {
        return {
            saveBtnDisabled:false,  //按钮是否禁用
            courseInfo:{
                title: '',
                subjectId: '',//二级分类id
                subjectParentId:'',//一级分类id
                teacherId: '',
                lessonNum: 0,
                description: '',
                cover: '/static/template.jpg',
                price: 0
            },
            BASE_API: process.env.BASE_API, // dev接口的API地址
            courseId:'', //添加的课程的id
            teacherList:[],//讲师列表
            subjectOneList:[],//所有一级分类
            subjectTwoList:[]//所有的二级分类
        }   
    },
    created() {
        this.init()
    },
    watch: { 
        $route(to,from){ //监听：每次当路由变化时，都去调用这个初始化方法（因为钩子函数有生命周期，不能重复调用多次，所以用监听来完成）
            this.init()
        }
    },
    methods:{
        init(){
            //如果是从其它页面点击的上一步，则需要获取路由中的课程id值，回显信息
            if(this.$route.params && this.$route.params.id) {
                this.courseId = this.$route.params.id
                this.getCourseInfo()
                this.getListTeacher()
            }else{
                //路由中没有id值，则是新增
            
                //初始化所有讲师
                this.getListTeacher()
                //初始化一级分类
                this.getOneSubject()
                //新增的话需要清空表单
                this.courseInfo.title=''
                this.courseInfo.subjectId=''
                this.courseInfo.subjectParentId='',//一级分类id
                this.courseInfo.teacherId=''
                this.courseInfo.lessonNum=0
                this.courseInfo.description=''
                this.courseInfo.cover='/static/template.jpg'
                this.courseInfo.price=0
                this.$refs.content.setContent("");
            }   
        },

        //回显课程信息
        getCourseInfo(){
            course.getCourseInfo(this.courseId)
                .then(response=>{
                    this.courseInfo=response.data.courseInfo

                    //返回的课程信息中只含有分类的id值
                    //为了能够在下拉列表中显示出分类的name，所以我们需要把所有分类查询出来，然后当id值匹配时就会显示name
                    subject.getSubjectList()
                        .then(response=>{
                            //获取所有的一级分类
                            this.subjectOneList = response.data.list;
                            
                            //获取回显的一级分类下的二级分类
                            for(var i=0;i<this.subjectOneList.length;i++){
                                var oneSubject=this.subjectOneList[i]
                                if(this.courseInfo.subjectParentId == oneSubject.id){
                                    this.subjectTwoList = oneSubject.children
                                }
                            }
                        })
                })
        },    

        //上传封面成功调用的方法
        handleAvatarSuccess(res, file) {
            this.courseInfo.cover = res.data.url
        },
        //上传之前调用的方法
        beforeAvatarUpload(file) {
            const isLt3M = file.size / 1024 / 1024 < 3
            //约束上传文件的大小
            if (!isLt3M) {
                this.$message.error('上传头像图片大小不能超过 3MB!')
            }
            return isLt3M
        },

        //点击某个一级分类，触发change，显示对应二级分类
        subjectLevelOneChanged(value) {
            //value就是一级分类id值
            //遍历所有的分类，包含一级和二级
            for(var i=0;i<this.subjectOneList.length;i++) {
                //每个一级分类
                var oneSubject = this.subjectOneList[i]
                //判断：所有一级分类id 和 点击一级分类id是否一样
                if(value === oneSubject.id) {
                    //从一级分类获取里面所有的二级分类
                    this.subjectTwoList = oneSubject.children
                    //点击新的一级分类的时候，需要把二级分类id值清空
                    this.courseInfo.subjectId = ''
                }
            }
        },
        //查询所有的一级分类
        getOneSubject() {
            subject.getSubjectList()
                .then(response => {
                    this.subjectOneList = response.data.list
                })
        },
        //查询所有的讲师
        getListTeacher() {
            teacher.getListTeacher()
                .then(response => {
                    this.teacherList = response.data.items
                })
        },

        //添加课程
        addCourse() {
            course.addCourseInfo(this.courseInfo)
                .then(response => {
                    //提示
                    this.$message({
                        type: 'success',
                        message: '添加课程信息成功!'
                    });
                    //跳转到第二步
                    this.$router.push({path:'/course/chapter/'+response.data.courseId})
                })
        },
        //修改课程
        updateCourse() {
            course.updateCourseInfo(this.courseInfo)
                .then(response=>{
                    //提示
                    this.$message({
                        type: 'success',
                        message: '修改课程信息成功!'
                    });
                    //跳转到第二步
                    this.$router.push({path:'/course/chapter/'+this.courseId})
                
                })
        },

        saveOrUpdate() {
            //判断是添加还是修改
            if(!this.courseInfo.id){
                this.addCourse()
            }else{
                this.updateCourse()
            }
        }
    }
}
</script>

<!-- 富文本编辑器tinymce的CSS样式部分-->
<style scoped>
.tinymce-container {
    line-height: 29px;
}
</style>