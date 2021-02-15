<template>
  <div class="app-container">
     <!--查询表单-->
    <el-form :inline="true" class="demo-form-inline">
        <el-form-item>
            <el-input v-model="searchMap.title" placeholder="课程名"/>
        </el-form-item>

        <el-form-item>
            <el-select v-model="searchMap.status" clearable placeholder="课程状态">
                <el-option :value="1" label="已发布"/>
                <el-option :value="0" label="未发布"/>
            </el-select>
        </el-form-item>

        <el-form-item label="价格区间">
            <el-input placeholder="" v-model="searchMap.beginPrice"></el-input>
        </el-form-item>
        ~
        <el-form-item>
            <el-input placeholder="" v-model="searchMap.endPrice"></el-input>
      </el-form-item>

      <el-button type="primary" icon="el-icon-search" @click="getList()">查询</el-button>
      <el-button type="default" @click="resetData()">清空</el-button>
    </el-form>

    <!-- 课程列表的表格 -->
    <el-table
        v-loading="listLoading"
        :data="list"
        element-loading-text="数据加载中"
        border
        fit
        highlight-current-row>

        <el-table-column
            label="序号"
            width="60"
            align="center">
            <template slot-scope="scope">
                {{ (page - 1) * limit + scope.$index + 1 }}
            </template>
        </el-table-column>
        <el-table-column prop="title" label="课程名称"/>
        <el-table-column label="是否发布" width="100">
            <template slot-scope="scope">
                {{ scope.row.status==='Normal'?'已发布':'未发布' }}
            </template>
        </el-table-column>
        <el-table-column prop="lessonNum" label="课时数" width="80" />
        <el-table-column prop="price" label="课程价格" width="100" />
        <el-table-column prop="gmtCreate" label="添加时间" width="160"/>
        <el-table-column label="操作" width="400" align="center">
            <template slot-scope="scope">
                <router-link :to="'/course/info/'+scope.row.id"> <!-- 通过路由跳转 -->
                    <el-button type="primary" size="mini" icon="el-icon-edit">编辑课程基本信息</el-button>
                </router-link>
                <router-link :to="'/course/chapter/'+scope.row.id"> <!-- 通过路由跳转 -->
                    <el-button type="primary" size="mini" icon="el-icon-edit">编辑课程大纲</el-button>
                </router-link>
                <el-button type="danger" size="mini" icon="el-icon-delete" @click="removeDataById(scope.row.id)">删除</el-button>
            </template>
        </el-table-column>
    </el-table>

    <!-- 分页 -->
    <el-pagination
        :current-page="page"
        :page-size="limit"
        :total="total"
        style="padding: 30px 0; text-align: center;"
        layout="total, prev, pager, next, jumper"
        @current-change="getList"
    />
  </div>
</template>

<script>
import course from '@/api/edu/course'

export default {
    data() {//定义数据模型
        return {
            total:0, //总记录数
            page:1, //当前页
            limit:10, //每页显示的条数
            searchMap:{}, //条件封装对象
            list:null //接口查询之后返回的集合值
        }
    },

    created() { //钩子函数，页面渲染之前执行
        this.getList()
    },

    //调用api中的方法
    methods: {
        //1.分页条件查询课程列表
        getList(page) {
            if(page==null){
                page=1;
            }
            this.page=page
            course.getCourseListPage(this.page,this.limit,this.searchMap)
                .then(response => {
                    //请求成功后返回的数据
                    this.total=response.data.total
                    this.list=response.data.rows
                })
                .catch(error => { 
                    //请求失败
                    this.$message({
                        type: 'error',
                        message: '请求失败'
                    })
                })
        },

        //2.清空查询条件的方法
        resetData() {
            //清空查询条件后，在调用查询方法查询
            this.searchMap={}
            this.getList()
        },
    
        //3.删除课程
        removeDataById(courseId) {
            this.$confirm('此操作将永久删除该记录, 是否继续?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => { //点击确定，执行then方法
                course.deleteCourseById(courseId)
                    .then(() => {
                        this.getList()

                        this.$message({
                            type: 'success',
                            message: '删除成功!'
                        })
                    })
                    .catch((response) => { // 失败
                        this.$message({
                            type: 'error',
                            message: '删除失败'
                        })
                    })
                })
        }
       
    }
}
</script>