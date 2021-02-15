<template>
  <div class="app-container">

    <!-- banner列表的表格 -->
    <el-table
        v-loading="listLoading"
        :data="list"
        element-loading-text="数据加载中"
        border
        fit
        highlight-current-row>

        <el-table-column
            label="序号"
            width="70"
            align="center">
            <template slot-scope="scope">
                {{ (page - 1) * limit + scope.$index + 1 }}
            </template>
        </el-table-column>
        <el-table-column prop="title" label="图片标题" width="150"/>
        <el-table-column prop="imageUrl" label="图片地址"/>
        <el-table-column prop="gmtCreate" label="添加时间" width="160"/>
        <el-table-column prop="sort" label="排序" width="70" />
        <el-table-column label="操作" align="center" width="200">
            <template slot-scope="scope">
                <el-button type="primary" size="mini" icon="el-icon-edit">修改</el-button>
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
import banner from '@/api/cms/banner'

export default {
    data() {//定义数据模型
        return {
            total:0, //总记录数
            page:1, //当前页
            limit:10, //每页显示的条数
            list:null //接口查询之后返回的集合值
        }
    },

    created() { //钩子函数，页面渲染之前执行
        this.getList()
    },

    //调用api中的方法
    methods: {
        //1.分页查询
        getList(page) {
            if(page==null){
                page=1;
            }
            this.page=page
            banner.getBannerListPage(this.page,this.limit)
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

    
        //2.删除
        removeDataById(id) {
            this.$confirm('此操作将永久删除该记录, 是否继续?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => { //点击确定，执行then方法
                banner.deleteBanner(id)
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