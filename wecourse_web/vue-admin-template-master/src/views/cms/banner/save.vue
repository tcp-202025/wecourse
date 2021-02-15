<template>
    <div class="app-container">
        <el-form label-width="120px">

            <el-form-item label="图片标题">
                <el-input v-model="crmBanner.title"/>
            </el-form-item>

            <el-form-item label="排序">
                <el-input-number v-model="crmBanner.sort" controls-position="right" min="0"/>
            </el-form-item>

            <el-form-item label="轮播图">

                <el-upload
                    :action="BASE_API+'/eduoss/fileoss/uploadFile'"
                    :before-upload="beforeUpload"
                    :on-remove="handleRemove"
                    :on-success="handleSuccess"
                    :file-list="fileList"
                    class="upload-demo"
                    list-type="picture">
                    <el-button size="small" type="primary">点击上传</el-button>
                    <div slot="tip" class="el-upload__tip">只能上传jpg/png文件，且不超过3M</div>
                </el-upload>

            </el-form-item>

            <el-form-item>
                <el-button :disabled="saveBtnDisabled" type="primary" @click="save()">保存</el-button>
            </el-form-item>

        </el-form>
    </div>
</template>

<script>
import banner from '@/api/cms/banner'
import ImageCropper from '@/components/ImageCropper'
import PanThumb from '@/components/PanThumb'

export default {
    components: { ImageCropper, PanThumb },
    data() {
        return {
            crmBanner:{},
            fileList:[],
            saveBtnDisabled:false, //表示按钮是否禁用
            BASE_API:process.env.BASE_API //获取dev.env.js中的地址
        }
    },

    created() { //页面渲染之前执行
        
    },
    

    methods: {

        //添加
        save(){
            banner.addBanner(this.crmBanner)
                .then(response => { //添加成功
                    //提示信息
                    this.$message({
                        type: 'success',
                        message: '添加成功！'
                    })

                    //回到list页面(通过路由跳转)
                    this.$router.push({path:'/banner/table'})
                })
                .catch(error => {
                    this.$message({
                        type: 'error',
                         message: '添加失败'
                    })
                })
        },

        //每次点击上传图片时，都需要将上一次url值清空
        beforeUpload(file) {
            this.crmBanner.imageUrl=''
        },

        //点击取消后，也需要清空url值
        handleRemove(file, fileList) {
            this.crmBanner.imageUrl=''
        },

        //图片上传成功后
        handleSuccess(response, file, fileList){
            this.crmBanner.imageUrl=response.data.url
        }
    }
}
</script>

