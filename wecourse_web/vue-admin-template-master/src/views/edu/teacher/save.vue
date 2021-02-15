<template>
    <div class="app-container">
        <el-form label-width="120px">

            <el-form-item label="讲师名称">
                <el-input v-model="teacher.name"/>
            </el-form-item>

            <el-form-item label="讲师排序">
                <el-input-number v-model="teacher.sort" controls-position="right" min="0"/>
            </el-form-item>

            <el-form-item label="讲师头衔">
                <el-select v-model="teacher.level" clearable placeholder="请选择">
                    <!--
                        数据类型一定要和取出的json中的一致，否则没法回填
                        因此，这里value使用动态绑定的值，保证其数据类型是number
                    -->
                    <el-option :value="1" label="高级讲师"/>
                    <el-option :value="2" label="首席讲师"/>
                </el-select>
            </el-form-item>

            <el-form-item label="讲师资历">
                <el-input v-model="teacher.career"/>
            </el-form-item>

            <el-form-item label="讲师简介">
                <el-input v-model="teacher.intro" :rows="10" type="textarea"/>
            </el-form-item>

    
            <el-form-item label="讲师头像">
                <!-- 头衔缩略图 -->
                <pan-thumb :image="teacher.avatar"/>

                <!-- 文件上传按钮 -->
                <el-button type="primary" icon="el-icon-upload" @click="imagecropperShow=true">更换头像
                    </el-button>
                        <!--
                            v-show：是否显示上传组件
                            :key：类似于id，如果一个页面多个图片上传控件，可以做区分
                            :url：后台上传的url地址
                            @close：关闭上传组件
                            @crop-upload-success：上传成功后的回调 -->
                        <image-cropper
                            v-show="imagecropperShow"
                            :width="300"
                            :height="300"
                            :key="imagecropperKey"
                            :url="BASE_API+'/eduoss/fileoss/uploadFile'"
                            field="file"
                            @close="close"
                            @crop-upload-success="cropSuccess"/>
            </el-form-item>

            <el-form-item>
                <el-button :disabled="saveBtnDisabled" type="primary" @click="saveOrUpdate()">保存</el-button>
            </el-form-item>

        </el-form>
    </div>
</template>

<script>
import teacherApi from '@/api/edu/teacher'
import ImageCropper from '@/components/ImageCropper'
import PanThumb from '@/components/PanThumb'

export default {
    components: { ImageCropper, PanThumb },
    data() {
        return {
            teacher:{},
            saveBtnDisabled:false, //表示按钮是否禁用
            imagecropperShow:false, //上传图片的组件是否显示
            imagecropperKey:0, //上传图片的这个组件的KEY值
            BASE_API:process.env.BASE_API //获取dev.env.js中的地址
        }
    },

    created() { //页面渲染之前执行
        this.init()
    },
    watch: { 
        $route(to,from){ //监听：每次当路由变化时，都去调用这个初始化方法（因为钩子函数有生命周期，不能重复调用多次，所以用监听来完成）
            this.init()
        }
    },

    methods: {
        init() {
            //在路由的路径中判断是否有id值
            //如果有id值，那么是修改方法，需要回显；如果没有id值，则是添加方法，不需要回显
            if(this.$route.params && this.$route.params.id){
                //获取id值
                const id=this.$route.params.id
                //调用方法回显数据
                this.getInfo(id)
            }else {
                //路径中没有id值，则是添加操作,需要清空表单
                this.teacher={}
            }
        },

        saveOrUpdate(){
            if(!this.teacher.id) { //没有id值则是添加操作
                this.save()
            }else { //有id值就是修改
                this.update()
            }
        },

        //添加讲师
        save(){
            if(this.teacher.name==null || this.teacher.name==''){
                this.$alert("请填写讲师名称")
                return;
            }
            if(this.teacher.level==null || this.teacher.name==''){
                this.$alert("请选择讲师头衔")
                return;
            }
            teacherApi.addTeacher(this.teacher)
                .then(response => { //添加成功
                    //提示信息
                    this.$message({
                        type: 'success',
                        message: '添加成功！'
                    })

                    //回到list页面(通过路由跳转)
                    this.$router.push({path:'/teacher/table'})
                })
                .catch(error => {
                    this.$message({
                        type: 'error',
                         message: '添加失败'
                    })
                })
        },

        //根据id查询讲师的信息
        getInfo(id) {
            teacherApi.getTeacherInfo(id)
                .then(response => {
                    this.teacher=response.data.teacher
                })
        },

        //修改讲师的方法
        update() {
            teacherApi.updateTeacher(this.teacher)
                .then(response => {
                    //提示信息
                    this.$message({
                        type: 'success',
                        message: '修改成功！'
                    })
                    //回到list页面(通过路由跳转)
                    this.$router.push({path:'/teacher/table'})
                })
                .catch(error => {
                    this.$message({
                        type: 'error',
                         message: '修改失败'
                    })
                })
        },

        //关闭上传图片弹框
        close() {
            this.imagecropperShow = false
            // 上传失败后，重新打开上传组件时初始化组件，否则显示上一次的上传结果
            this.imagecropperKey = this.imagecropperKey + 1
        },

        //上传成功的方法
        cropSuccess(data) {
            if(data.url==null){
                this.$message({
                        type: 'error',
                        message: '上传失败！'
                    })
            }

            //上传成功后关闭上传图片的弹框，返回图片的URL
            this.imagecropperShow = false
            this.teacher.avatar = data.url
            //上传成功后，重新打开上传组件时初始化组件，否则显示上一次的上传结果
            this.imagecropperKey = this.imagecropperKey + 1
        }

    }
}
</script>

